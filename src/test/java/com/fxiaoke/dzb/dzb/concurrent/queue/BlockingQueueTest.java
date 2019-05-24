package com.fxiaoke.dzb.dzb.concurrent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: dongzhb
 * @date: 2019/5/24
 * @Description:
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingQueue(5);
        try {
            queue.add("1");
            queue.add("2");
            queue.add("3");
            queue.add("4");
            queue.add("5");
            System.out.println("queue  full size=" + queue.size());
            queue.take();
            System.out.println("queue take size=" + queue.size());
            queue.add("6");
            System.out.println("queue clean after size=" + queue.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
