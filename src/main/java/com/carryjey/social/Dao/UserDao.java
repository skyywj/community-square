package com.carryjey.social.Dao;

import com.carryjey.social.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Repository
@CacheConfig(cacheNames = "user_profile")
public class UserDao {

    private static final BeanPropertyRowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @CacheEvict(key = "'user_id_' + #user.getUserId()")
    public void updateAvater(User user) {
        String sql = "update user set avatar = :avatar where id = :id";
        int rows = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
        assert rows == 1;
    }

    @CachePut(key = "'user_id_' + #userId")
    public User getByUserId(long userId) {
        String sql = "select * from user where user_id = :userId";
        List<User> user = jdbcTemplate.query(sql, new MapSqlParameterSource("userId", userId), rowMapper);
        if (user.size() == 0) {
            return null;
        }
        return user.get(0);
    }
}
