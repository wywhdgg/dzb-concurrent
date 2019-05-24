package com.fxiaoke.dzb.dzb.concurrent.pool;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: dongzhb
 * @date: 2019/5/24
 * @Description:
 */
@Slf4j
public class FixedThreadPoolProduce {
    /**
     * 1.任务仓库 2.集合容器 队列 3.执行任务task 4.初始化线程池 5.对外提供提交接口 6.关闭线程池
     */

    private BlockingQueue<Runnable> blockingQueue;
    private List<Thread> workers;
    private  volatile  boolean isWorking= true;

    public FixedThreadPoolProduce(int poolSize, int queueSize) {
        if(poolSize<0||queueSize<0){
            throw  new IllegalArgumentException("param is illegal");
        }
        blockingQueue = new LinkedBlockingQueue<Runnable>();
         this.workers = Collections.synchronizedList(new ArrayList<Thread>());
        for (int i = 0; i <poolSize ; i++) {
            Worker worker = new Worker(this);
            worker.start();
            workers.add(worker);
        }
    }

    private static class Worker extends Thread {
        private FixedThreadPoolProduce pool;
        public Worker(FixedThreadPoolProduce pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                try {
                    /**
                     * 如果是正常工作，阻塞等待次此操作，直至完成
                     * */
                    if(this.pool.isWorking){
                        runnable=    this.pool.blockingQueue.take();
                    }else {
                        runnable=    this.pool.blockingQueue.poll();
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

    /**
     * 执行任务 阻塞
     */
    public void execute(Runnable runnable) {
        try {
            if(isWorking){
              this.blockingQueue.put(runnable);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交任务 非阻塞
     */
    public boolean submit(Runnable runnable) {
        if(!isWorking) {
            return false;
        }
        this.blockingQueue.offer(runnable);
        return true;
    }

    /**
     * 关闭线程池
     */
    public void shutdownNow() {
          //设置工作状态:false
           isWorking= false;
            for (Thread thread:workers){
                log.info("state={}",thread.getState());
                if(thread.getState().equals(State.WAITING)||thread.getState().equals(State.BLOCKED)){
                    thread.interrupt();
                }
            }
    }

    public int getWorkerCount(){
        return  this.workers.size();
    }
}
