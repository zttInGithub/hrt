package com.hrt.biz.bill.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hrt.biz.bill.entity.pagebean.MachineTaskDataBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMachineTaskDataService {

	DataGridBean queryMachineTaskData(MachineTaskDataBean machineTaskDataBean,UserBean userBean);
	
	DataGridBean queryAddMachineTaskData(MachineTaskDataBean machineTaskDataBean,UserBean userBean);
	
	DataGridBean queryMachineTaskDataTCD(MachineTaskDataBean machineTaskDataBean,UserBean userBean);
	
	DataGridBean queryMachineTaskDataJudge(MachineTaskDataBean machineTaskDataBean,UserBean userBean);
	
	void updateMachineTaskData(MachineTaskDataBean machineTaskDataBean, UserBean user) throws Exception;
	
	void updateMachineTaskDataY(MachineTaskDataBean machineTaskDataBean, UserBean user) throws Exception;
	
	void updateMachineTaskDataK(MachineTaskDataBean machineTaskDataBean, UserBean user) throws Exception;
	
	void updateMachineTaskDataTask(MachineTaskDataBean machineTaskDataBean,UserBean user) throws Exception;
	void updateMachineTaskDataTask1(MachineTaskDataBean machineTaskDataBean) throws Exception;
	void updateMachineTaskDataTask2(MachineTaskDataBean machineTaskDataBean) throws Exception;
	/**
	 * 导出商户 -终端   ---word
	 */
	List<Map<String, Object>> exportMerchantInfo1(int bmtd);
	/**
	 * 工单申请
	 */
	void saveMachineTaskData(MachineTaskDataBean machineTaskData, UserBean user) throws Exception;
	
	HSSFWorkbook export(String ids);
	
	/**
	 * 删除退回的工单
	 */
	void deleteMachineTaskData(Integer bmtdID,Integer userID);
	
	
	void updateType(MachineTaskDataBean machineTaskDataBean, UserBean user);

	List<Map<String, Object>> queryMachineTaskSingleData(
			MachineTaskDataBean machineTaskDataBean);

	String queryFileNameBybmtdID(String bmtdIDs);

	HSSFWorkbook exportMachineTaskDataJudge(String ids);
}
