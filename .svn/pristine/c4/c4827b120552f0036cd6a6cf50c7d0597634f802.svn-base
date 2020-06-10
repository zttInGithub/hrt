package com.hrt.biz.bill.service;

import java.util.List;

import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.biz.bill.entity.pagebean.MachineInfoBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IMachineInfoService {
	
	/**
	 * 方法功能：分页查询机具库存
	 */
	DataGridBean queryMachineInfos(MachineInfoBean machineInfo) throws Exception;
	
	/**
	 * 添加机具库存
	 */
	void save(MachineInfoBean machineInfoBean, UserBean userSession);

	/**
	 * 删除机具库存
	 */
	void delete(Integer parseInt);

	/**
	 * 修改机具库存
	 */
	void update(MachineInfoBean machineInfoBean, UserBean userSession);

	/**
	 * 分页查询机具库存（可根据条件查询）
	 */
	List<MachineInfoModel> getInfo(MachineInfoBean machineInfoBean);
	
	/**
	 * 查询机具库存在下拉中显式（机具预购申请中使用）
	 */
	List<MachineInfoModel> searchMachineInfoType(MachineInfoBean machineInfoBean);
	
	/**
	 * 查询机具库存显示到相应的select
	 */
	List<MachineInfoBean> searchMachineInfo(String nameCode);

	/**
	 * 判断是否是重复记录
	 */
	Boolean checkInfo(MachineInfoBean machineInfoBean);
	List<MachineInfoBean> searchNormalMachineInfo(String nameCode);
}
