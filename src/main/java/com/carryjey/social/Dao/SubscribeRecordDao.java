package com.carryjey.social.Dao;

import com.carryjey.social.model.SubscribeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/25
 */
@Repository
public class SubscribeRecordDao {

    private static final BeanPropertyRowMapper<SubscribeRecord> rowMapper =
        BeanPropertyRowMapper.newInstance(SubscribeRecord.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public SubscribeRecord getSubscribe(long userId, long fromUserid) {
        String sql = "select * from subscribe_record where user_id = :userId and from_user_id = :fromUserId";
        List<SubscribeRecord> subscribeRecords =
            jdbcTemplate.query(
                sql, new MapSqlParameterSource("userId", userId).addValue("fromUserId", fromUserid), rowMapper);
        if (subscribeRecords.size() == 0) {
            return null;
        }
        return subscribeRecords.get(0);
    }

    public void addSubscribeRecord(SubscribeRecord subscribeRecord) {
        String sql =
            "insert into subscribe_record (user_id,from_user_id,created_time,updated_time) values (:userId,:fromUserId,:createdTime,:updatedTime)";
        int rows = jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(subscribeRecord));
        assert rows == 1;
    }

    public void updateStatus(long userId, long fromUserId, int status) {
        String sql =
            "update  subscribe_record set status = :status where user_id=:userId and from_user_id = :fromUserId";
        int rows =
            jdbcTemplate.update(
                sql,
                new MapSqlParameterSource("status", status)
                    .addValue("userId", userId)
                    .addValue("fromUserId", fromUserId));
        assert rows == 1;
    }

    public List<SubscribeRecord> getSubscribeList(long userId) {
        String sql = "select * from subscribe_record where user_id = :userId";
        List<SubscribeRecord> subscribeRecords =
            jdbcTemplate.query(sql, new MapSqlParameterSource("userId", userId), rowMapper);
        if (subscribeRecords.size() == 0) {
            return null;
        }
        return subscribeRecords;
    }
}
