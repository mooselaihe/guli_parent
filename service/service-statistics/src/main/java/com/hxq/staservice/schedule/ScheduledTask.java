package com.hxq.staservice.schedule;

import com.hxq.staservice.service.StatisticsDailyService;
import com.hxq.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/***
 *@author 何鑫强
 *@date 2021/2/1
 */
@Component
public class ScheduledTask  {
    @Autowired
    private StatisticsDailyService staService;

    //在明天凌晨一点，把前一天数据进行数据查询添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        //由于无法接收参数day，使用工具类生成前一天时间
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
