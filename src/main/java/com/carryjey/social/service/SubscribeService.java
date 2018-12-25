package com.carryjey.social.service;

import com.carryjey.social.Dao.SubscribeRecordDao;
import com.carryjey.social.controller.front.BaseController;
import com.carryjey.social.model.SubscribeRecord;
import com.carryjey.social.model.User;
import com.carryjey.social.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CarryJey
 * @since 2018/12/25
 */
@Service
public class SubscribeService extends BaseController {

    @Autowired
    private SubscribeRecordDao subscribeRecordDao;

    /**
     * 订阅
     */
    public void subscribe(long fromUserId) {
        User user = getUser();
        SubscribeRecord subscribeRecord = getSubscribe(user.getUserId(), fromUserId);
        if (subscribeRecord != null) {
            subscribeRecord
                .setUserId(user.getUserId())
                .setFrromUserId(fromUserId)
                .setCreatedTime(System.currentTimeMillis())
                .setUpdatedTime(System.currentTimeMillis());
            subscribeRecordDao.addSubscribeRecord(subscribeRecord);
            return;
        }
        subscribeRecordDao.updateStatus(user.getUserId(), fromUserId, Constants.OPEN_SUBSCRIBE_STATUS);
    }

    /**
     * 取消订阅
     */
    public void cancelSubscribe(long fromUserId) {
        subscribeRecordDao.updateStatus(getUser().getUserId(), fromUserId, Constants.CLOSE_SUBSCRIBE_STATUS);
    }

    /**
     * 获取订阅列表
     */
    public List<SubscribeRecord> getSubscribeList() {
        return subscribeRecordDao.getSubscribeList(getUser().getUserId());
    }

    /**
     * 获取单条订阅列表
     *
     * @return
     */
    public SubscribeRecord getSubscribe(long userId, long fromUserId) {
        return subscribeRecordDao.getSubscribe(userId, fromUserId);
    }
}
