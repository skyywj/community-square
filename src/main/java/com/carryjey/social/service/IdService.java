package com.carryjey.social.service;

import org.springframework.stereotype.Service;

/**
 * @author CarryJey
 * @since 2018/12/19
 */
@Service
public class IdService {
    /**
     * id生成器，采用单例模式进行实例化，依靠静态内部类实现单例模式
     */
    private static final long WORKER_ID = 1;

    private static final long DATA_CENTER_ID = 1;

    private static class SingleTon {
        public static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(WORKER_ID, DATA_CENTER_ID);
    }

    public static SnowflakeIdWorker createUserId() {
        return SingleTon.snowflakeIdWorker;
    }
}
