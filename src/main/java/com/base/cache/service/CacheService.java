package com.base.cache.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.shiro.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 使用JSON存储缓存
 * Created by YiMing on 2017-04-05.
 */
@Service
public class CacheService {


    @Resource(name = "redisTemplate")
    private RedisOperations redisOperations;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 线程安全的+1
     * @param key
     * @return
     */
    public Long increment(String key) {
        return redisOperations.opsForValue().increment(key);
    }


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

    /**
     * 线程安全的-1
     * @param key
     * @return
     */
    public Long decrement(String key) {
        return redisOperations.opsForValue().decrement(key);
    }

    public Long decrement(String key, Long init) {
        return redisOperations.opsForValue().decrement(key, init);
    }


    public boolean expire(String key, Long seconds) {
        return expire(key, seconds, TimeUnit.SECONDS);
    }


    public boolean expire(String key, Long seconds, TimeUnit timeUnit) {
        return redisTemplate.expire(key, seconds, timeUnit);
    }


    public void add(String key, Object value) {
        redisOperations.opsForValue().set(key, JSON.toJSONString(value));
    }


    public void add(String key, Object value, Integer outDay) {
        redisOperations.opsForValue().set(key, JSON.toJSONString(value), outDay, TimeUnit.DAYS);
    }


    public void add(String key, Object value, Long outTime) {
        redisOperations.opsForValue().set(key, JSON.toJSONString(value), outTime, TimeUnit.SECONDS);
    }

    /**
     * 从尾进
     * @param key
     * @param value
     */

    public void addToList(String key, Object value) {
        redisOperations.opsForList().rightPush(key, JSON.toJSONString(value));
    }

    /**
     * 从头进
     * @param key
     * @param value
     */

    public void addToLeftList(String key, Object value) {
        redisOperations.opsForList().leftPush(key, JSON.toJSONString(value));
    }


    public Object get(String key) {
        return redisOperations.opsForValue().get(key);
    }

    /**
     * 从尾出
     * @param key
     * @param clazz
     */

    public <T> T get(String key, Class<T> clazz) {
        Object value = redisOperations.opsForValue().get(key);
        T object = JSON.parseObject(String.valueOf(value), clazz);
        return object;
    }


    public <T> T outFromList(String key, Class<T> clazz) {
        Object value = redisOperations.opsForList().rightPop(key);
        T object = JSON.parseObject(String.valueOf(value), clazz);
        return object;
    }


    public <T> T outFromLeftList(String key, Class<T> clazz) {
        Object value = redisOperations.opsForList().leftPop(key);
        T object = JSON.parseObject(String.valueOf(value), clazz);
        return object;
    }


    public List<JSONObject> getFromList(String key, Long start, Long end) {
        Object value = redisOperations.opsForList().range(key, start, end);
        List<JSONObject> object = JSON.parseObject(String.valueOf(value), ArrayList.class);
        return object;
    }


    public  Object getListByIndex(String key, Integer index){
        ListOperations ops = redisOperations.opsForList();
        return ops.index(key,index);
    }

    public void removeList(String key, Object old){
        ListOperations ops = redisOperations.opsForList();
        ops.remove(key, 1, JSON.toJSONString(old));
    }



    public void putToMap(String keyMap, String mapKey, Object mapValue) {
        redisOperations.opsForHash().put(keyMap, mapKey, JSON.toJSONString(mapValue));
    }


    public <T> T getFromMap(String keyMap, String mapKey, Class<T> clazz) {
        Object value = redisOperations.opsForHash().get(keyMap, mapKey);
        T object = JSON.parseObject(String.valueOf(value), clazz);
        return object;
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
     * @param clazz  返回类型
     * @param cacheLoadable  回调接口
     * @param <T>
     * @return
     */

    public <T> T getCache(String key, Class<T> clazz, CacheLoadable<T> cacheLoadable) {
        T json = get(key, clazz);
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
     * @param clazz  返回类型
     * @param cacheLoadable  回调接口
     * @param <T>
     * @return
     */

    public <T> T getCache(String key, long expire, Class<T> clazz, CacheLoadable<T> cacheLoadable) {
        T json = get(key, clazz);
        if(null == json){
            json = cacheLoadable.load();
            add(key, cacheLoadable.load(), expire);
        }
        return json;
    }
}
