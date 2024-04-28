package com.base.core.annotation;

import org.springframework.stereotype.Service;

/**
 * 测试分布式锁切面的测试类
 */
@Service
public class DistributeLockTest {
    
    @DistributeLock()
    public String update(String symbol) {
        return symbol + "TEST" + symbol;
    }

    @DistributeLock()
    public Long multiThread(Long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return time;
        }
    }

    @DistributeLock()
    public int throwException(int i) {
        return 10/i;
    }
}
