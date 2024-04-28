package com.base.core.aspect;

import com.base.core.annotation.DistributeLock;
import com.base.core.exception.DistributeLockException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁切面
 * 在目标方法执行前获取分布式锁，在{@link #LOCK_TIMEOUT} 超时时间内未获取到锁则抛出异常。
 * 获取到锁之后执行目标方法
 * 接着释放分布式锁，返回目标方法的返回值。
 * 
 * @author xuhang
 * @version v0.0.1
 */
@Aspect
@Component
public class DistributeLockAspect {
    private static Logger logger = LoggerFactory.getLogger(DistributeLockAspect.class);
    private static final Long LOCK_TIMEOUT = 5*1000L;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 分布式锁切入点
     * 只有含{@link com.base.core.annotation.DistributeLock}注解的方法才会自动获取和释放分布式锁
     */
    @Pointcut("@annotation(com.base.core.annotation.DistributeLock)")
    public void distributeLockAspect(){}

    /**
     * 环绕通知
     * @param joinPoint
     * @return 目标方法执行结果
     * @throws DistributeLockException 获取锁和释放锁失败抛出此异常
     */
    @Around("distributeLockAspect()")
    public Object around(JoinPoint joinPoint) throws DistributeLockException {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        String signName = joinPoint.getTarget().getClass().getName() + "." + method.getName() + "(" + joinPoint.getArgs() + ")";
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);
        
        if (distributeLock == null) {
            // 没有注解
            throw new DistributeLockException("方法["+signName+"]上未获取到@DistributeLock注解");
        }
        
        String lockKey = distributeLock.lockKey();
        String requestId = distributeLock.requestId();
        
        // 获取锁
        logger.info("=========["+signName+"]开始获取分布式锁=========");
        Boolean lock = lock(lockKey, requestId);
        Long start = System.currentTimeMillis();
        while (!lock) {
            if (System.currentTimeMillis() - start > LOCK_TIMEOUT) {
                throw new DistributeLockException("方法["+signName+"]获取分布式锁超时");
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock = lock(lockKey, requestId);
        }
        logger.info("[" + signName + "]获取分布式锁成功√√√√√√√√√");

        // 执行目标方法
        logger.info("=========["+signName+"]执行目标方法=========");
        Object proceed = null;
        try {
            proceed = ((ProceedingJoinPoint) joinPoint).proceed();
        } catch (Throwable throwable) {
            logger.error("目标方法执行异常", throwable);
        }
        
        //释放锁
        logger.info("=========["+signName+"]开始释放分布式锁=========");
        Boolean unlock = unlock(lockKey, requestId);
        if (unlock) {
            logger.info("[" + signName + "]释放分布式锁成功√√√√√√√√√");
        } else {
            logger.error("[" + signName + "]释放分布式锁失败×××××××××");
//            throw new DistributeLockException("[" + signName + "]释放分布式锁失败");
        }
        
        return proceed;
    }

    /**
     * 获取锁
     * setIfAbsent KEY为空时才能设置值，并且设置超时时间
     * @param lockKey KEY
     * @param requestId VALUE
     * @return 是否成功获取锁
     */
    public synchronized Boolean lock(String lockKey, String requestId) {
        Boolean flag = false;
        try {
            flag = redisTemplate.opsForValue()
                    .setIfAbsent(lockKey, requestId, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        } finally {
            return flag != null && flag;
        }
    }
    
    public synchronized Boolean unlock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long result = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(lockKey), requestId);
        return result != null && result == 1L;
    }

}
