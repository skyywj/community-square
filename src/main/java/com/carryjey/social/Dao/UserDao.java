package com.carryjey.social.Dao;

import com.carryjey.social.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@Repository
public class UserDao {

    private static final BeanPropertyRowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void updateAvater(User user) {
        String sql = "update user set avatar = :avatar where id = :id";
        int rows = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
        assert rows == 1;
    }
}
