package com.fxiaoke.dzb.dzb.concurrent.thread.corepool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: dongzhb
 * @date: 2019/5/24
 * @Description:
 */
public class CorePoolTest {

    public static void main(String[] args) throws Exception {
        CorePoolTest corePoolTest = new CorePoolTest();
        corePoolTest.test1();
    }

    /**
     * 1、无界队列，超出核心线程数量的线程存活时间：5秒
     *
     * @throws Exception
     */
    private void test1() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            3,
            5,
            5,      //超过核心线程数的线程，如果超过5s（keepAliveTime）还没有任务给他执行，这个线程就会被销毁
            TimeUnit.SECONDS,                       //keepAliveTime 的时间单位
            new LinkedBlockingQueue<Runnable>(4)     //传入无界的等待队列
        );
        testCommon(threadPoolExecutor);
        // 预计结果：线程池线程数量为：5,超出数量的任务，其他的进入队列中等待被执行
    }

    /**
     * 测试： 提交15个执行时间需要3秒的任务,看线程池的状况
     *
     * @param threadPoolExecutor 传入不同的线程池，看不同的结果
     * @throws Exception
     */
    public void testCommon(ThreadPoolExecutor threadPoolExecutor) throws Exception {
        // 测试： 提交15个执行时间需要3秒的任务，看超过大小的2个，对应的处理情况
        for (int i = 0; i < 10; i++) {
            System.out.println(">>> 线程数量：" + threadPoolExecutor.getPoolSize());
            System.out.println(">>> 队列任务数量：" + threadPoolExecutor.getQueue().size());
            final int n = i;
            threadPoolExecutor.submit(new Runnable() {
                public void run() {
                    try {
                        System.out.println("任务" + n +" 开始执行");
                        Thread.sleep(3000L);
                        System.err.println("任务" + n +" 执行结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("任务" + i + " 提交成功");
        }


//        while(true){
//            // 查看线程数量，查看队列等待数量
//            Thread.sleep(1000L);
//            System.out.println(">>> 线程数量：" + threadPoolExecutor.getPoolSize());
//            System.out.println(">>> 队列任务数量：" + threadPoolExecutor.getQueue().size());
//        }

    }
}
