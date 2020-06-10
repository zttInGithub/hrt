package com.hrt.frame.service.sysadmin;

import java.util.List;
import java.util.Map;

public interface IQuartzService {

	/**
	 * 查询任务监控列表Grid
	 * @return
	 * @throws Exception
	 */
	List queryQrtzTriggers() throws Exception;

	/**
	 * 添加
	 * @param map
	 * @throws Exception
	 */
	void insertQrtzTrigger(Map<String, Object> map) throws Exception;

	/**
	 * 暂停
	 * @param triggerName
	 * @param group
	 * @throws Exception
	 */
	void pauseTrigger(String triggerName,String group) throws Exception;

	/**
	 * 恢复
	 * @param triggerName
	 * @param group
	 * @throws Exception
	 */
	void resumeTrigger(String triggerName,String group) throws Exception;

	/**
	 * 刪除
	 * @param triggerName
	 * @param group
	 * @return
	 * @throws Exception
	 */
	boolean removeTrigger(String triggerName,String group) throws Exception;
	
	/**
	 * 方法一号（调用存储过程）
	 * @throws Exception
	 */
	void batchJob1() throws Exception;
}
