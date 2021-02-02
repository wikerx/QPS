package com.scott.wiker.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author :Mr.薛
 * @version :V1.0
 * @className :SapmleAlone
 * @description :
 * @data :2020/11/27 0027 上午 11:57
 * @status : 编写
 **/
@Slf4j
public class SapmleAloneJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("任务调度：{}, job-{}",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
        new SecureRandom().nextInt(100));
    }
}
