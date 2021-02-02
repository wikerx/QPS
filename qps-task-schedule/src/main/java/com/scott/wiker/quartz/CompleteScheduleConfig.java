package com.scott.wiker.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @calssname:CompleteScheduleConfig
 * @author:Mr.薛
 * @date:2021/2/1 9:45
 * @version:v1.0
 * @status:create
 * 动态任务调度模拟中心
 */
@Slf4j
@Configuration
@EnableScheduling
public class CompleteScheduleConfig implements SchedulingConfigurer {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String cron = "0/5 * * * * ?";

    /**
     * 执行定时任务
     * 每次系统启动的时候回去加载系统中所有的定时任务，然后将cron加入任务调度列表，依次加载
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)，可以为方法
                () -> log.debug("定时任务开始执行写入MQ测试 - 时间戳:{} ", df.format(new Date())),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    for (int i = 0; i < 1; i++) {
//                        direct.sendDirectMessage();
//                        fanout.sendFanoutMessage();
//                        topic.sendTopicMessage();
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }


}
