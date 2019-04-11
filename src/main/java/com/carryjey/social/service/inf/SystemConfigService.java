package com.carryjey.social.service.inf;

import com.carryjey.social.model.SystemConfig;

import java.util.Map;

/**
 * Created by CarryJey on 2019-04-11.
 */
public interface SystemConfigService {

    public Map selectAllConfig();

    // 根据键取值
    public SystemConfig selectByKey(String key);

    public Map<String, Object> selectAll();

    // 在更新系统设置后，清一下selectAllConfig()的缓存
    public String update(String[] key, String[] value);

    // 判断redis是否配置了
    public boolean isRedisConfig();
}
