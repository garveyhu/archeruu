package com.archeruu.core.utils;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * jetcache缓存管理工具
 *
 * @author Archer
 */
@Component
public class JetCacheManager {
    @Autowired
    private CacheManager cacheManager;

    /**
     * 获取或创建缓存，带过期时间和缓存类型
     * @param key 缓存键
     * @param cacheType 缓存类型（本地、远程）
     * @param expireInSeconds 缓存过期时间，单位秒
     * @return 缓存实例
     */
    public <K, V> Cache<K, V> getOrCreateCache(String key, CacheType cacheType, int expireInSeconds) {
        Cache<K, V> cache = cacheManager.getCache(key);
        if (cache == null) {
            QuickConfig config = QuickConfig
                    .newBuilder(key)
                    .cacheType(cacheType)
                    .expire(Duration.ofSeconds(expireInSeconds))
                    .build();
            cache = cacheManager.getOrCreateCache(config);
        }
        return cache;
    }

    /**
     * 获取或创建缓存，默认缓存类型为远程，不设置过期时间
     * @param key 缓存键
     * @return 缓存实例
     */
    public <K, V> Cache<K, V> getOrCreateCache(String key) {
        return getOrCreateCache(key, CacheType.REMOTE, -1);
    }

    /**
     * 获取或创建缓存，指定过期时间，默认缓存类型为远程
     * @param key 缓存键
     * @param expireInSeconds 缓存过期时间，单位秒
     * @return 缓存实例
     */
    public <K, V> Cache<K, V> getOrCreateCache(String key, int expireInSeconds) {
        return getOrCreateCache(key, CacheType.REMOTE, expireInSeconds);
    }

    /**
     * 获取或创建缓存，默认缓存类型为远程，永久有效
     * @param key 缓存键
     * @param cacheType 缓存类型
     * @return 缓存实例
     */
    public <K, V> Cache<K, V> getOrCreateCache(String key, CacheType cacheType) {
        return getOrCreateCache(key, cacheType, -1);
    }

    /**
     * 获取缓存，如果不存在则返回null
     * @param key 缓存键
     * @return 缓存实例或null
     */
    public <K, V> Cache<K, V> getCache(String key) {
        return cacheManager.getCache(key);
    }

    /**
     * 创建缓存，带过期时间和缓存类型
     * @param key 缓存键
     * @param cacheType 缓存类型
     * @param expireInSeconds 缓存过期时间，单位秒
     * @return 新创建的缓存实例
     */
    public <K, V> Cache<K, V> createCache(String key, CacheType cacheType, int expireInSeconds) {
        QuickConfig config = QuickConfig
                .newBuilder(key)
                .cacheType(cacheType)
                .expire(Duration.ofSeconds(expireInSeconds))
                .build();
        return cacheManager.getOrCreateCache(config);
    }
}