package com.code.demo.common.lock.impl;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Scope
@Aspect
@Order(1)
public class LockImpl{
    /**
     * 思考：为什么不用synchronized
     * service 默认是单例的，并发下lock只有一个实例
     */
    private static Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁

    //Service层切点     用于记录错误日志
    @Pointcut("@annotation(com.code.demo.common.lock.Locks)")
    public void LookImpl() {

    }

    @Around("LookImpl()")
    public  Object around(ProceedingJoinPoint joinPoint) throws Exception {
        lock.lock();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        catch (Throwable e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
        return obj;
    }
}
