package com.carryjey.social.service;

import com.carryjey.social.Dao.ImStatDao;
import com.carryjey.social.controller.front.BaseController;
import com.carryjey.social.model.ImStat;
import com.carryjey.social.model.ObjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangjunwei
 * @since 2018-02-05
 */
@Service
@Transactional(rollbackFor = Exception.class, timeout = 5)
public class ImStatService extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ImStatService.class);

    @Autowired
    private ImStatDao imStatDao;

    public ImStat getOrInit(long userId) {
        return imStatDao.getOrInitByUserId(userId);
    }

    public long incrAvatarVersion(long userId) {
        return incrAvatarVersion(userId, 1);
    }

    public long incrAvatarVersion(long userId, int batchSize) {
        return imStatDao.incrVersion(userId, ObjectType.AVATAR, batchSize).getAvatarVersion();
    }
}
