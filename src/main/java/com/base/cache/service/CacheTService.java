package com.base.cache.service;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 使用JSON存储缓存
 * Created by YiMing on 2017-04-05.
 */
@Service
public class CacheTService<T> {


    @Resource(name = "redisTemplate")
    private RedisOperations<String, T> redisOperations;

    @Autowired
    private RedisTemplate<String, T> redisTemplate;


    public Long increment(String key, Long init) {
        return redisOperations.opsForValue().increment(key, init);
    }


    public Long increment(String key, Long init, Long seconds) {
        Long result = increment(key, init);
        expire(key, seconds);
        return result;
    }

    public Long increment(String key, Long init, Integer day){
        Long result = increment(key, init);
        redisTemplate.expire(key, day,TimeUnit.DAYS);
        return result;
    }


    public boolean expire(String key, Long seconds) {
        return expire(key, seconds, TimeUnit.SECONDS);
    }


    public boolean expire(String key, Long seconds, TimeUnit timeUnit) {
        return redisTemplate.expire(key, seconds, timeUnit);
    }


    public void add(String key, T value) {
        redisOperations.opsForValue().set(key, value);
    }


    public void add(String key, T value, Integer outDay) {
        redisOperations.opsForValue().set(key, value, outDay, TimeUnit.DAYS);
    }


    public void add(String key, T value, Long outTime) {
        redisOperations.opsForValue().set(key, value, outTime, TimeUnit.SECONDS);
    }

    /**
     * 从尾进
     * @param key
     * @param value
     */

    public void addToList(String key, T value) {
        redisOperations.opsForList().rightPush(key, value);
    }

    /**
     * 从头进
     * @param key
     * @param value
     */

    public void addToLeftList(String key, T value) {
        redisOperations.opsForList().leftPush(key, value);
    }


    public T get(String key) {
        return redisOperations.opsForValue().get(key);
    }


    public T outFromList(String key) {
        return redisOperations.opsForList().leftPop(key);
    }


    public T outFromLeftList(String key) {
        return redisOperations.opsForList().leftPop(key);
    }


    public List<T> getFromList(String key, Long start, Long end) {
        return redisOperations.opsForList().range(key, start, end);
    }


    public T getListByIndex(String key, Integer index){
        ListOperations<String, T> ops = redisOperations.opsForList();
        return ops.index(key,index);
    }

    public void removeList(String key, Object old){
        ListOperations<String, T> ops = redisOperations.opsForList();
        ops.remove(key, 1, JSON.toJSONString(old));
    }



    public void putToMap(String keyMap, String mapKey, Object mapValue) {
        redisOperations.opsForHash().put(keyMap, mapKey, JSON.toJSONString(mapValue));
    }


    public Object getFromMap(String keyMap, String mapKey) {
        return redisOperations.opsForHash().get(keyMap, mapKey);
    }


    public Long getListSize(String key) {
        return redisOperations.opsForList().size(key);
    }



    public Boolean hasKey(String key) {
        return redisOperations.hasKey(key);
    }


    public String getIncrement(String key) {
        String result = redisTemplate.boundValueOps(key).get(0, -1);
        if(StringUtils.isEmpty(result)) {
            return "0";
        }
        return result;
    }



    public void delete(String key) {
        redisOperations.delete(key);
    }


    public void pushMessage(String message) {
        redisTemplate.convertAndSend("message_mark", message);
    }


    /**
     * 获取缓存数据不加锁
     * @param key    缓存key值
     * @param cacheLoadable  回调接口
     * @return
     */

    public T getCache(String key, CacheLoadable<T> cacheLoadable) {
        T json = get(key);
        if(null == json){
            json = cacheLoadable.load();
            add(key, cacheLoadable.load());
        }
        return json;
    }

    /**
     * 获取缓存数据不加锁
     * @param key    缓存key值
     * @param expire  缓存时间
     * @param cacheLoadable  回调接口
     * @return
     */

    public T getCache(String key, long expire, CacheLoadable<T> cacheLoadable) {
        T json = get(key);
        if(null == json){
            json = cacheLoadable.load();
            add(key, cacheLoadable.load(), expire);
        }
        return json;
    }
}
