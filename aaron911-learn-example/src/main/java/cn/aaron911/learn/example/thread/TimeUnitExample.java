package cn.aaron911.learn.example.thread;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author:
 * @time: 2020/11/12 17:20
 */
public class TimeUnitExample {

    public static void main(String[] args){
        try {
            TimeUnit.HOURS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
