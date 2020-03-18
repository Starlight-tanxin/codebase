package com.zyb.mini.mall.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

/**
 * @author tanxin
 * @date 2019/8/13
 */
@EnableCaching
@Configuration
public class RedisConfig {

    /**
     * redis 连接池
     *
     * @return 连接池对象
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    public GenericObjectPoolConfig redisPool() {
        return new GenericObjectPoolConfig<>();
    }

    /**
     * 常规redis
     *
     * @return mainRedis配置
     */
    @Bean
    public RedisStandaloneConfiguration redisConfigMain(@Value("${spring.redis.redis-main.host}") String host,
                                                        @Value("${spring.redis.redis-main.port}") int port,
                                                        @Value("${spring.redis.redis-main.database}") int database,
                                                        @Value("${spring.redis.redis-main.password}") String password) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setDatabase(database);
        if (password != null && password.trim().length() != 0) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        }
        return redisStandaloneConfiguration;
    }

    /**
     * 对项目token缓存操作得Reid
     *
     * @return 项目tokenRedis配置
     */
    @Bean
    public RedisStandaloneConfiguration redisConfigPT(@Value("${spring.redis.redis-project-token.host}") String host,
                                                      @Value("${spring.redis.redis-project-token.port}") int port,
                                                      @Value("${spring.redis.redis-project-token.database}") int database,
                                                      @Value("${spring.redis.redis-project-token.password}") String password) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setDatabase(database);
        if (password != null && password.trim().length() != 0) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        }
        return redisStandaloneConfiguration;
    }

    /**
     * 配置第一个数据源的连接工厂
     * 这里注意：需要添加@Primary 指定bean的名称，目的是为了创建两个不同名称的LettuceConnectionFactory
     *
     * @param redisPool       连接池
     * @param redisConfigMain 主redis
     * @return factoryMain
     */
    @Bean("factoryMain")
    @Primary
    public LettuceConnectionFactory factoryMain(GenericObjectPoolConfig redisPool, RedisStandaloneConfiguration redisConfigMain) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(redisPool).build();
        return new LettuceConnectionFactory(redisConfigMain, clientConfiguration);
    }

    @Bean("factoryPt")
    public LettuceConnectionFactory factoryPt(GenericObjectPoolConfig redisPool, RedisStandaloneConfiguration redisConfigPT) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(redisPool).build();
        return new LettuceConnectionFactory(redisConfigPT, clientConfiguration);
    }

    @Bean(name = "redisTemplate")
    @Primary
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("factoryMain") LettuceConnectionFactory factoryMain) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setConnectionFactory(factoryMain);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "redisTemplatePt")
    public RedisTemplate<String, Object> redisTemplatePt(@Qualifier("factoryPt") LettuceConnectionFactory factoryPt) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setEnableTransactionSupport(false);
        redisTemplate.setConnectionFactory(factoryPt);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues();
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
        return cacheManager;
    }

}
