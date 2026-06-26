package com.eetrust.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于 ReentrantLock 的细粒度锁管理器
 * <p>
 * 按 key 分配锁，不同 key 互不阻塞，适合并发插入场景
 * 锁对象不会自动回收，key 数量受业务唯一标识限制，不会产生内存泄漏
 */
@Component
public class LockManager {

    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    /**
     * 获取 key 对应的锁（懒创建，线程安全）
     */
    public ReentrantLock getLock(String key) {
        return lockMap.computeIfAbsent(key, k -> new ReentrantLock());
    }

    /**
     * 释放并清理 key 对应的锁
     */
    public void removeLock(String key) {
        ReentrantLock lock = lockMap.remove(key);
        if (lock != null) {
            lock.unlock();
        }
    }
}
