package com.scott.wiker.test;

import com.scott.wiker.quartz.SapmleAloneJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :Sample2
 * @description :Cron配置
 * @data :2020/11/27 0027 下午 12:10
 * @status : 编写
 **/
@Slf4j
public class Sample2 {
    private final static String CRON_TIME1 = "0 0/2 8-17 * * ?";

    public static void main(String[] args) throws InterruptedException, SchedulerException {
        new Sample2().testCron();
    }

    /**
     * cron定时测试
     * @throws SchedulerException
     * @throws InterruptedException
     */
    public void testCron() throws SchedulerException, InterruptedException{
        // 1、创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 2、创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
        JobDetail jobDetail = JobBuilder.newJob(SapmleAloneJob.class)
                .usingJobData("jobDetail1", "这个Job用来测试的")
                .withIdentity("job1", "group1").build();
        // 3、构建Trigger实例,每隔1s执行一次
        Date startDate = new Date();
        Date endDate = new Date();
        startDate.setTime(startDate.getTime() + 5000);
        endDate.setTime(startDate.getTime() + 5000);

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
                .usingJobData("trigger1", "这是jobDetail1的trigger")
                .startNow()//立即生效
//                .startAt(startDate)
//                .endAt(endDate)
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_TIME1))
                .build();

        //4、执行
        scheduler.scheduleJob(jobDetail, cronTrigger);
        log.info("--------定时任务调度开始了！------------");
        scheduler.start();
        log.info("--------定时任务调度结束了 ! ------------");
    }

}
