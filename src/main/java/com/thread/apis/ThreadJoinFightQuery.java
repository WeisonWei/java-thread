package com.thread.apis;


import com.thread.impl.FightQueryTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ThreadJoinFightQuery {
    private static List<String> fightCompany = Arrays.asList("海南航空", "东方航空", "西北航空");

    public static void main(String[] args) throws InterruptedException {


    }

    private static List<String> search(String original, String dest) {
        final List<String> result = new ArrayList<>();
        //2 创建查询航班的线程列表
        List<FightQueryTask> tasks = fightCompany.stream().
                map(f -> createSearchTask(f, original, dest)).collect(toList());


        //3 分别启动这几个线程
        tasks.forEach(Thread::start);

        //4 分别调用每个线程的join方法，阻塞当前线程
        tasks.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        });
        //5 当前线程会阻塞，获取每个线程的查询结果，并加入到result中
        tasks.stream().map(FightQueryTask::get).forEach(result::addAll);
        return result;
    }

    private static FightQueryTask createSearchTask(String fight, String original, String dest) {
        return new FightQueryTask(fight, original, dest);
    }
}
