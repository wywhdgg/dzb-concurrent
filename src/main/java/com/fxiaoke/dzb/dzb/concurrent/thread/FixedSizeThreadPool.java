package com.fxiaoke.dzb.dzb.concurrent.thread;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author: dongzhb
 * @date: 2019/5/17
 * @Description:   手写线程池
 */
public class FixedSizeThreadPool {

    /**
     * 1.创建一个线程任务仓库
     * 2.创建集合任务容器
     * 3.线程执行的任务
     * 4.初始化线程
     * 5.对外提供提交任务接口  阻塞
     * 6.非阻塞的接口
     * 7.关闭线程池
     *    禁止向队列提交
     *    等待仓库中任务执行完
     *    关闭任务  阻塞的任务立即中断  不阻塞的，没有新增任务
     * */

    private BlockingQueue<Runnable>  blockingQueue;

    private List<Thread> workers;

    private static class  Worker extends Thread{

        private FixedSizeThreadPool pool;

        public Worker(FixedSizeThreadPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            while (this.pool.blockingQueue!=null||this.pool.blockingQueue.size()>0){
                Runnable runnable = null;
                try {
                    if(this.pool.isWorking){
                            runnable =this.pool.blockingQueue.take();
                    }else {
                        runnable = this.pool.blockingQueue.poll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(runnable!=null){
                    runnable.run();
                }
            }
        }
    }


    public   FixedSizeThreadPool(int poolSize,int queueSize){
        if(poolSize<=0||queueSize<=0){
            throw  new IllegalArgumentException("param is illegal");
        }
        //任务仓库
        this.blockingQueue = new LinkedBlockingDeque<Runnable>();
        //初始化线程池
       this.workers = Collections.synchronizedList(new ArrayList<Thread>());
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

     //非阻塞
     public  boolean submit(Runnable runnable){
          if(!this.isWorking) return  false;
         return this.blockingQueue.offer(runnable);
     }

     //阻塞
     public  void  execute(Runnable runnable){
         try {
             if(this.isWorking){
                 this.blockingQueue.put(runnable);
             }
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
     private  volatile  boolean isWorking =true;
    /** java.lang.Thread.State
     *
     * 线程的状态
     * NEW 尚未启动的线程的线程状态。
     * RUNNABLE 可运行线程的线程状态，等待CPU调度。
     * BLOCKED 线程阻塞等待监视器锁定的线程状态。 处于synchronized同步代码块或方法中被阻塞。
     * WAITING 线程等待的线程状态。不带timeout参数的方式调用Object.wait、 Thread.join、 LockSupport.park
     * TIMED_WAITING 具有指定等待时间的等待线程的线程状态。下列带超时的方式：Thread.sleep、 Object.wait、 Thread.join、 LockSupport.parkNanos、 LockSupport.parkUntil
     * TERMINATED 终止线程的线程状态。线程正常完成执行或者出现异常
     * */
    public void shutdown(){
        this.isWorking=false;
           for(Thread thread:workers){
               if(thread.getState().equals(State.WAITING)||thread.getState().equals(State.BLOCKED)){
                   thread.interrupt();
               }
           }
    }

}
