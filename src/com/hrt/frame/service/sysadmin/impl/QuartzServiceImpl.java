package com.hrt.frame.service.sysadmin.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

import com.hrt.frame.constant.Constant;
import com.hrt.frame.dao.sysadmin.IQuartzDao;
import com.hrt.frame.service.sysadmin.IQuartzService;


public class QuartzServiceImpl implements IQuartzService {

	private Log log = LogFactory.getLog(QuartzServiceImpl.class);
	
	private IQuartzDao quartzDao;
	
	private Scheduler scheduler;

	public void setQuartzDao(IQuartzDao quartzDao) {
		this.quartzDao = quartzDao;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	/**
	 * 时间格式转换
	 */
	private Date parseDate(String time) {
		try {
			return DateUtils.parseDate(time,new String[] { "yyyy-MM-dd HH:mm:ss" });
		} catch (ParseException e) {
			log.error("日期格式错误{}，正确格式为：yyyy-MM-dd HH:mm:ss " + time);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List queryQrtzTriggers() throws Exception{
		String sql = "SELECT TRIGGER_NAME,TRIGGER_GROUP,NEXT_FIRE_TIME,PREV_FIRE_TIME,TRIGGER_STATE,START_TIME,END_TIME FROM QRTZ_TRIGGERS";
		List<Map<String, String>> results = quartzDao.queryObjectsBySqlList(sql);
		long val = 0;
		String temp = null;
		List list = new ArrayList();
		for (Map<String, String> map : results) {
			temp = MapUtils.getString(map, Constant.TRIGGER_NAME);
			if (StringUtils.indexOf(temp, "&") != -1) {
				map.put(Constant.DISPLAY_NAME, StringUtils.substringBefore(temp, "&"));
			} else {
				map.put(Constant.DISPLAY_NAME, temp);
			}

			val = MapUtils.getLongValue(map, Constant.NEXT_FIRE_TIME);
			if (val > 0) {
				map.put(Constant.NEXT_FIRE_TIME, DateFormatUtils.format(val,"yyyy-MM-dd HH:mm:ss"));
			}

			val = MapUtils.getLongValue(map, Constant.PREV_FIRE_TIME);
			if (val > 0) {
				map.put(Constant.PREV_FIRE_TIME, DateFormatUtils.format(val,"yyyy-MM-dd HH:mm:ss"));
			}

			temp = MapUtils.getString(map, Constant.TRIGGER_STATE);
			map.put(Constant.DISPLAY_STATE, Constant.STATUS.get(temp));

			val = MapUtils.getLongValue(map, Constant.START_TIME);
			if (val > 0) {
				map.put(Constant.START_TIME, DateFormatUtils.format(val,"yyyy-MM-dd HH:mm:ss"));
			}

			val = MapUtils.getLongValue(map, Constant.END_TIME);
			if (val > 0) {
				map.put(Constant.END_TIME, DateFormatUtils.format(val,"yyyy-MM-dd HH:mm:ss"));
			}

			list.add(map);
		}
		return list;
	}

	@Override
	public void insertQrtzTrigger(Map<String, Object> map) throws Exception {
		String temp = null;

		temp = map.get("jobClass").toString();
		Class jobClass = Class.forName(temp);
		JobDetail jobDetail = new JobDetail(jobClass.getSimpleName(),null, jobClass);

		CronTrigger cronTrigger=new CronTrigger();
		cronTrigger.setJobName(jobDetail.getName());
		cronTrigger.setJobGroup(scheduler.DEFAULT_GROUP);
		temp = map.get("cron").toString();
		cronTrigger.setCronExpression(temp);
		
		// 设置名称
		temp = map.get("triggerName").toString()+"&"+UUID.randomUUID().toString();
		cronTrigger.setName(temp);
		
		// 设置分组
		cronTrigger.setGroup(Scheduler.DEFAULT_GROUP);
		
		//开始时间
		temp = map.get("kssj").toString();
		if(StringUtils.isNotEmpty(temp)){
			cronTrigger.setStartTime(this.parseDate(temp));
		}
		
		//结束时间
		temp = map.get("jssj").toString();
		if(StringUtils.isNotEmpty(temp)){
			cronTrigger.setEndTime(this.parseDate(temp));
		}
		scheduler.addJob(jobDetail, true);
		scheduler.scheduleJob(cronTrigger);
		scheduler.rescheduleJob(cronTrigger.getName(), cronTrigger.getGroup(), cronTrigger);
	}

	@Override
	public boolean removeTrigger(String triggerName, String group) throws Exception {
		// 停止触发器
		scheduler.pauseTrigger(triggerName, group);
		// 移除触发器
		return scheduler.unscheduleJob(triggerName, group);
	}

	@Override
	public void pauseTrigger(String triggerName, String group) throws Exception {
		// 停止触发器
		scheduler.pauseTrigger(triggerName, group);
	}

	@Override
	public void resumeTrigger(String triggerName, String group) throws Exception {
		// 重启触发器
		scheduler.resumeTrigger(triggerName, group);
	}

	@Override
	public void batchJob1() throws Exception{
		quartzDao.batchJob1();
	}
	
}
