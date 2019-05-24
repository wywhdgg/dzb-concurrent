package com.fxiaoke.dzb.dzb.concurrent.thread.corepool;

import com.fxiaoke.dzb.dzb.concurrent.pool.FixedThreadPoolProduce;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: dongzhb
 * @date: 2019/5/24
 * @Description:
 */
@Slf4j
public class FixedThreadPoolProduceTest {
    public static void main(String[] args) {
        FixedThreadPoolProduce poolProduce = new FixedThreadPoolProduce(3,6);
        for (int i = 0; i < 7; i++) {
            poolProduce.submit(new Runnable() {
                @Override
                public void run() {
                    log.info("开始执行任务");
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        log.info("=======休眠前线程池活跃线程数={}======" + poolProduce.getWorkerCount());
        try {
            Thread.sleep(2500);
            poolProduce.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("=======休眠后线程池活跃线程数={}======" + poolProduce.getWorkerCount());
    }
}
