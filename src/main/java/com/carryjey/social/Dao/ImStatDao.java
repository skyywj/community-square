package com.carryjey.social.Dao;

import com.carryjey.social.model.ImStat;
import com.carryjey.social.model.ObjectType;
import com.carryjey.social.util.Constants;
import com.carryjey.social.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.IntSupplier;

/**
 * @author CarryJey
 * @since 2018/12/27
 */
@Repository
@CacheConfig(cacheNames = "stats")
public class ImStatDao {
    private static final Logger logger = LoggerFactory.getLogger(ImStatDao.class);

    private static final BeanPropertyRowMapper<ImStat> rowMapper = BeanPropertyRowMapper.newInstance(ImStat.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private ImStat findByUserId(long usreId) {
        String sql = "SELECT * FROM im_stat WHERE user_id = :userId";
        List<ImStat> imStats = jdbcTemplate.query(sql, new MapSqlParameterSource("userId", usreId), rowMapper);
        if (imStats.isEmpty()) {
            return null;
        }
        return imStats.get(0);
    }

    private void save(ImStat imStat) {
        String sql =
            "INSERT INTO im_stat "
                + "(user_id,avatar_version, created_time, updated_time) "
                + "VALUES (:userId,:avatarVersion,:createdTime,:updatedTime)";

        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(imStat));
    }

    @Cacheable(key = "'user_id_'+ #userId")
    public ImStat getOrInitByUserId(long userId) {
        ImStat pillStat = findByUserId(userId);
        if (pillStat != null) {
            return pillStat;
        }

        initStat(userId);
        pillStat = findByUserId(userId);
        if (pillStat == null) {
            logger.error("Im stats still not exists after init for user id: {}, does it really happen?", userId);
            throw new RuntimeException();
        }

        return pillStat;
    }

    private void initStat(long userId) {
        ImStat pillStat =
            new ImStat()
                .setUserId(userId)
                .setAvatarVersion(0)
                .setCreatedTime(System.currentTimeMillis())
                .setUpdatedTime(System.currentTimeMillis());

        try {
            save(pillStat);
        } catch (DataIntegrityViolationException e) {
            // 不考虑并发， duplicate key 异常忽略即可
            // 但是理论上出现的几率非常低，所以打 warn 日志
            logger.warn("init stat error, userId: {},{}", userId, e);
        }
    }

    /**
     * 更新 stat
     */
    private ImStat updateStat(long userId, IntSupplier updateOperation) {
        int rows = updateOperation.getAsInt();
        if (rows == 0) {
            initStat(userId);
            rows = updateOperation.getAsInt();
        }

        assert rows == 1;

        return findByUserId(userId);
    }

    /**
     * 增加 "对象" 最大版本号，并返回增加后的值 先更新，后读取， 除非当前事务提交，否则其他事务无法修改当前事务锁定的记录
     */
    @CacheEvict(key = "'user_id_' + #userId")
    public ImStat incrVersion(long userId, ObjectType objType, int delta) {
        return updateStat(
            userId,
            () -> {
                String updateSql =
                    "UPDATE im_stat SET "
                        + objType
                        + "_version = "
                        + objType
                        + "_version + :delta, updated_time = :updatedTime WHERE user_id = :userId";
                MapSqlParameterSource params =
                    new MapSqlParameterSource("delta", delta)
                        .addValue(Constants.FIELD_NAME_USER_ID, userId)
                        .addValue(Constants.FIELD_NAME_UPDATED_TIME, DateTimeUtils.utcNow());
                return jdbcTemplate.update(updateSql, params);
            });
    }

    @CacheEvict(key = "'user_id_' + #userId")
    public void delete(long userId) {
        logger.info("Delete stat, user id: {}.", userId);
        String sql = "DELETE FROM im_stat WHERE user_id = :userId";
        int rows = jdbcTemplate.update(sql, new MapSqlParameterSource(Constants.FIELD_NAME_USER_ID, userId));

        assert rows == 1;
    }
}
