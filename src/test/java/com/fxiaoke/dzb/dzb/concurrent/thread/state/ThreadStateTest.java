package com.fxiaoke.dzb.dzb.concurrent.thread.state;

/**
 * @author: dongzhb
 * @date: 2019/5/17
 * @Description: 线程状态 java.lang.Thread.State
 *
 * 线程的状态 NEW 尚未启动的线程的线程状态。 RUNNABLE 可运行线程的线程状态，等待CPU调度。 BLOCKED 线程阻塞等待监视器锁定的线程状态。 处于synchronized同步代码块或方法中被阻塞。 WAITING 线程等待的线程状态。不带timeout参数的方式调用Object.wait、 Thread.join、 LockSupport.park TIMED_WAITING
 * 具有指定等待时间的等待线程的线程状态。下列带超时的方式：Thread.sleep、 Object.wait、 Thread.join、 LockSupport.parkNanos、 LockSupport.parkUntil TERMINATED 终止线程的线程状态。线程正常完成执行或者出现异常
 */
public class ThreadStateTest {
    public static Thread thread1;
    public static ThreadStateTest threadState;

    public static void main(String[] args) throws Exception {
        // test1();
        //test2();
        test3();
    }

    public static void test1() throws InterruptedException {
        // 第一种状态切换 - 新建 -> 运行 -> 终止
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("开始执行任务");
            }
        });
        System.out.println("new begin status=" + thread.getState().toString());
        Thread.sleep(2000L);
        thread.start();
        System.out.println("new after status=" + thread.getState().toString());
        Thread.sleep(4000L); // 等待thread1执行结束，再看状态
        System.out.println("sleep status=" + thread.getState().toString());
        //thread1.start();     //TODO 注意，线程终止之后，再进行调用，会抛出IllegalThreadStateException异常
    }

    public static void test2() throws InterruptedException {
        //新建 -> 运行 -> 等待 -> 运行 -> 终止(sleep方式)
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {// 将线程2移动到等待状态，1500后自动唤醒
                    Thread.sleep(5000);
                    System.out.println("sleep is over,thread name=" + Thread.currentThread().getName() + " status =" + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("new begin status=" + thread.getState().toString());
        thread.start();
        System.out.println("new after status=" + thread.getState().toString());
        Thread.sleep(2000L); // 等待200毫秒，再看状态
        System.out.println("wait 2s status=" + thread.getState().toString());
    }

    public static void test3() throws InterruptedException {
       //新建 -> 运行 -> 阻塞 -> 运行 -> 终止
        //创建thread3，暂不启动
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                System.out.println("thread lock  before  status=" + Thread.currentThread().getState().toString());
                //3、子线程抢锁，阻塞 等待
                synchronized (ThreadStateTest.class) {
                    System.out.println("lock execute  status=" + Thread.currentThread().getState());
                    try {
                        Thread.sleep(100000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        synchronized (ThreadStateTest.class) {
            System.out.println("thread lock ,begin is runable!");
            thread3.start();
            Thread.sleep(5000L);
            System.out.println("sleep statue=" + thread3.getState());
            Thread.sleep(5000L);
        }
        System.out.println("thread lock is release");
    }
}
