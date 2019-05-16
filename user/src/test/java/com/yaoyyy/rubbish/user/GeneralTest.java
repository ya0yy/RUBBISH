package com.yaoyyy.rubbish.user;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 　　　　　　　 ┏┓　 ┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　┃
 * 　　　　　　　┃　　　━　　 ┃ ++ + + +
 * 　　　　　　 ████━████  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　 ┃　　　┃
 * 　　　　　　　　 ┃　　　┃ + + + +
 * 　　　　　　　　 ┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　 ┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　 ┃　　　┃
 * 　　　　　　　　 ┃　　　┃　　+
 * 　　　　　　　　 ┃　 　 ┗━━━┓ + +
 * 　　　　　　　　 ┃ 　　　　   ┣┓
 * 　　　　　　　　 ┃ 　　　　　 ┏┛
 * 　　　　　　　　 ┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　  ┃┫┫ ┃┫┫
 * 　　　　　　　　  ┗┻┛ ┗┻┛+ + + +
 * <p>
 * rubbish-parent
 * 2019-02-28 19:38
 *
 * @author yaoyang
 */
public class GeneralTest {

    static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        new Thread() {
            @Override
            public void run() {
                sleep1();
            }
        }.start();

        sleep1();

    }

    private static void sleep1() {

        boolean b = lock.tryLock();
        System.err.println(Thread.currentThread().getName() + "上锁" + b);
        if (!b) {
            throw new RuntimeException("茅坑被别人占了");
        }

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.unlock();
        System.err.println(Thread.currentThread().getName() + "解锁");
    }

    @Test
    public void test() {
        String s = "abcd";
        System.out.println(s.replace("a", ""));
        System.out.println(s);
    }

    @Test
    public void testLongToInt() {
        long l = -21474836600L;
        int i = (int) l;
        System.out.println(i);
    }
}
