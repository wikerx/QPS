package com.scott.wiker.test;

import com.scott.wiker.quartz.SapmleAloneJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :Sample1
 * @description : 常规配置
 * @data :2020/11/27 0027 下午 12:01
 * @status : 编写
 **/
@Slf4j
public class Sample1 {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Date startDate = new Date();
        Date endDate = new Date();
//        设置任务的开始时间和结束时间
        startDate.setTime(startDate.getTime() + 5000);
        endDate.setTime(startDate.getTime() + 5000);


        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(SapmleAloneJob.class)
                .withIdentity("job1", "group1").build();
        // 3、构建Trigger实例,每隔1s执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .startNow()//立即生效
//                .startAt(startDate)
//                .endAt(endDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        每隔5s执行一次
                        .withIntervalInSeconds(5)
//                        一直执行
                        .repeatForever()).build();
        //4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        log.info("--------定时任务调度开始了！------------");
        scheduler.start();

        //睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        log.info("--------定时任务调度结束了 ! ------------");
    }


}
