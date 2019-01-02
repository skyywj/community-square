package com.carryjey.social;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.time.Duration;

/**
 * @author CarryJey
 * @since 2018/12/17
 */
@SpringBootApplication
@EnableCaching
public class CommunitySquareApplication {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        SpringApplication.run(CommunitySquareApplication.class, args);
    }

    @Component
    @ConfigurationProperties(prefix = "spring.redis")
    public static class RedisConfig {
        private String prefix = "community-square";

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

        public String addPrefix(String key) {
            return prefix + ":" + key;
        }
    }

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        SingleServerConfig singleServerConfig = config.useSingleServer();
        String schema = redisProperties.isSsl() ? "rediss://" : "redis://";
        singleServerConfig.setAddress(schema + redisProperties.getHost() + ":" + redisProperties.getPort());
        singleServerConfig.setDatabase(redisProperties.getDatabase());
        if (redisProperties.getPassword() != null) {
            singleServerConfig.setPassword(redisProperties.getPassword());
        }

        // 其他配置项都先采用默认值
        return Redisson.create(config);
    }

    //使用Async实现异步任务和线程池空值
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        //设置线程池大小
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        return taskScheduler;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Configuration
    public static class CacheConfig extends CachingConfigurerSupport {
        @Autowired
        private RedisConfig redisConfig;

        @Bean
        public GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer() {

            GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

            try {
                Field mapperField = ReflectionUtils.findField(GenericJackson2JsonRedisSerializer.class, "mapper");
                mapperField.setAccessible(true);

                ObjectMapper objectMapper = (ObjectMapper) mapperField.get(serializer);
                // java8 time
                objectMapper.findAndRegisterModules();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

                mapperField.setAccessible(false);
            } catch (Exception e) {
                logger.warn("Config object mapper of GenericJackson2JsonRedisSerializer error.", e);
                throw new RuntimeException(e);
            }

            return serializer;
        }

        @Bean
        public RedisCacheConfiguration redisCacheConfiguration() {
            return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(2))
                .computePrefixWith(cacheName -> redisConfig.prefix + ":" + cacheName + ":")
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()));
        }
    }
}
