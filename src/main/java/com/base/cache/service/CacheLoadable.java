package com.base.cache.service;

/**
 * 缓存数据库操作接口
 */
public interface CacheLoadable<T> {
    T load();
}
