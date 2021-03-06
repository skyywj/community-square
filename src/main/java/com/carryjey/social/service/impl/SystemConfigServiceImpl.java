package com.carryjey.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.carryjey.social.mapper.SystemConfigMapper;
import com.carryjey.social.model.SystemConfig;
import com.carryjey.social.service.inf.SystemConfigService;
import com.carryjey.social.util.Constants;
import com.carryjey.social.util.JsonUtil;
import com.carryjey.social.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Service
@Transactional
public class SystemConfigServiceImpl implements SystemConfigService {

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map selectAllConfig() {
        String system_config = redisUtil.getString(Constants.REDIS_SYSTEM_CONFIG_KEY);
        if (system_config != null) {
            return JsonUtil.jsonToObject(system_config, Map.class);
        } else {
            List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
            Map<String, Object> map = new HashMap<>();
            systemConfigs
                    .stream()
                    .filter(systemConfig -> systemConfig.getPid() != 0)
                    .forEach(systemConfig -> map.put(systemConfig.getKey(), systemConfig.getValue()));
            // 将查询出来的数据放到redis里缓存下来（如果redis可用的话）
            redisUtil.setString(Constants.REDIS_SYSTEM_CONFIG_KEY, JsonUtil.objectToJson(map));
            return map;
        }
    }

    // 根据键取值
    @Override
    public SystemConfig selectByKey(String key) {
        QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SystemConfig::getKey, key);
        return systemConfigMapper.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> selectAll() {
        Map<String, Object> map = new LinkedHashMap<>();
        List<SystemConfig> systemConfigs = systemConfigMapper.selectList(null);
        // 先提取出所有父节点
        List<SystemConfig> p =
                systemConfigs.stream().filter(systemConfig -> systemConfig.getPid() == 0).collect(Collectors.toList());
        // 遍历父节点取父节点下的所有子节点
        p.forEach(
                systemConfig -> {
                    List<SystemConfig> collect =
                            systemConfigs
                                    .stream()
                                    .filter(systemConfig1 -> systemConfig1.getPid().equals(systemConfig.getId()))
                                    .collect(Collectors.toList());
                    map.put(systemConfig.getDescription(), collect);
                });
        return map;
    }

    // 在更新系统设置后，清一下selectAllConfig()的缓存
    @Override
    public String update(String[] key, String[] value) {
        for (int i = 0; i < key.length; i++) {
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setKey(key[i]);
            systemConfig.setValue(value[i]);
            QueryWrapper<SystemConfig> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SystemConfig::getKey, systemConfig.getKey());
            systemConfigMapper.update(systemConfig, wrapper);
        }
        // 判断redis配置是否去除，去除了，就将RedisUtil里的jedis属性设置为null
        if (!this.isRedisConfig()) {
            redisUtil.setJedis(null);
        }
        // 清除redis里关于 system_config 的缓存
        redisUtil.delString(Constants.REDIS_SYSTEM_CONFIG_KEY);
        return null;
    }

    // 判断redis是否配置了
    @Override
    public boolean isRedisConfig() {
        SystemConfig systemConfigHost = this.selectByKey("redis.host");
        String host = systemConfigHost.getValue();
        // port
        SystemConfig systemConfigPort = this.selectByKey("redis.port");
        String port = systemConfigPort.getValue();
        // database
        SystemConfig systemConfigDatabase = this.selectByKey("redis.database");
        String database = systemConfigDatabase.getValue();
        // timeout
        SystemConfig systemConfigTimeout = this.selectByKey("redis.timeout");
        String timeout = systemConfigTimeout.getValue();

        return !StringUtils.isEmpty(host)
                && !StringUtils.isEmpty(port)
                && !StringUtils.isEmpty(database)
                && !StringUtils.isEmpty(timeout);
    }
}
