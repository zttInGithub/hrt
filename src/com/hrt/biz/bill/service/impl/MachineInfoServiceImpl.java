package com.hrt.biz.bill.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IMachineInfoDao;
import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.biz.bill.entity.pagebean.MachineInfoBean;
import com.hrt.biz.bill.service.IMachineInfoService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class MachineInfoServiceImpl implements IMachineInfoService{
	
	private IMachineInfoDao machineInfoDao;
	
	public IMachineInfoDao getMachineInfoDao() {
		return machineInfoDao;
	}

	public void setMachineInfoDao(IMachineInfoDao machineInfoDao) {
		this.machineInfoDao = machineInfoDao;
	}

	/**
	 * 方法功能：格式化MachineInfoModel为datagrid数据格式
	 * 参数：machineInfoList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<MachineInfoModel> machineInfoList, long total) {
		List<MachineInfoBean> machineInfoBeanList = new ArrayList<MachineInfoBean>();
		for(MachineInfoModel machineInfoModel : machineInfoList) {
			MachineInfoBean machineInfoBean = new MachineInfoBean();
			BeanUtils.copyProperties(machineInfoModel, machineInfoBean);
			
			machineInfoBeanList.add(machineInfoBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(machineInfoBeanList);
		
		return dgb;
	}
	
	/**
	 * 方法功能：格式化MachineInfoModel为combogrid数据格式
	 * 参数：machineInfoList
	 * 	   total 总记录数
	 */
	private DataGridBean formatToCombogrid(List<MachineInfoModel> machineInfoList, long total) {
		List<MachineInfoBean> machineInfoBeanList = new ArrayList<MachineInfoBean>();
		for(MachineInfoModel machineInfoModel : machineInfoList) {
			MachineInfoBean machineInfoBean = new MachineInfoBean();
			BeanUtils.copyProperties(machineInfoModel, machineInfoBean);
			
			//
			machineInfoBeanList.add(machineInfoBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(machineInfoBeanList);
		
		return dgb;
	}
	
	/**
	 * 分页查询机具库存
	 */
	@Override
	public DataGridBean queryMachineInfos(MachineInfoBean machineInfo)
			throws Exception {
		String hql = "from MachineInfoModel where 1 = 1";
		String hqlCount = "select count(*) from MachineInfoModel where 1 = 1";
		if (machineInfo.getSort() != null) {
			hql += " order by " + machineInfo.getSort() + " " + machineInfo.getOrder();
		}
		long counts = machineInfoDao.queryCounts(hqlCount, null);
		List<MachineInfoModel> machineInfoList = machineInfoDao.queryMachineInfos(hql, null, machineInfo.getPage(), machineInfo.getRows());
		
		DataGridBean dataGridBean = formatToDataGrid(machineInfoList, counts);
		
		return dataGridBean;
	}

	/**
	 * 添加机具库存
	 */
	@Override
	public void save(MachineInfoBean machineInfoBean, UserBean user) {
		MachineInfoModel machineInfoModel = new MachineInfoModel();
		BeanUtils.copyProperties(machineInfoBean, machineInfoModel);
		machineInfoModel.setMaintainDate(new Date());//操作日期默认当前
		machineInfoModel.setMaintainType("A");//操作类型添加
		machineInfoModel.setMaintainUID(user.getUserID());
		machineInfoDao.saveObject(machineInfoModel);
	}

	/**
	 * 删除机具库存
	 */
	@Override
	public void delete(Integer id) {
		MachineInfoModel machineInfo=machineInfoDao.getObjectByID(MachineInfoModel.class, id);
		machineInfoDao.deleteObject(machineInfo);
	}

	/**
	 * 修改机具库存
	 */
	@Override
	public void update(MachineInfoBean machineInfoBean, UserBean user) {
		MachineInfoModel machineInfoModel = new MachineInfoModel();
		BeanUtils.copyProperties(machineInfoBean, machineInfoModel);
		machineInfoModel.setMaintainDate(new Date());//操作日期默认当前
		machineInfoModel.setMaintainType("M");//操作类型添加
		machineInfoModel.setMaintainUID(user.getUserID());
		machineInfoDao.updateObject(machineInfoModel);
	}

	/**
	 * 分页查询机具库存（可根据条件查询）
	 */
	@Override
	public List<MachineInfoModel> getInfo(MachineInfoBean machineInfo) {
		Map<String, Object> param=new HashMap<String, Object>();
		String hql = "from MachineInfoModel where 1 = 1 AND machinePrice IS NOT NULL ";
		if(machineInfo.getBmaID()!=null&&!"".equals(machineInfo.getBmaID())){
			hql+=" AND bmaID=:bmaID";
			param.put("bmaID", machineInfo.getBmaID());
		}
		if(machineInfo.getMachineType()!=null&&!"".equals(machineInfo.getMachineType())){
			hql+=" AND machineType=:machineType";
			param.put("machineType", machineInfo.getMachineType());
		}
		if (machineInfo.getSort() != null) {
			hql += " order by " + machineInfo.getSort() + " " + machineInfo.getOrder();
		}
		List<MachineInfoModel> machineInfoList = machineInfoDao.queryMachineInfos(hql, param, machineInfo.getPage(), machineInfo.getRows());
		
		return machineInfoList;
	}
	
	/**
	 * 分页查询机具库存（可根据条件查询）
	 */
	@Override
	public List<MachineInfoModel> searchMachineInfoType(MachineInfoBean machineInfo) {
		Map<String, Object> param=new HashMap<String, Object>();
		String hql = "from MachineInfoModel where 1 = 1 AND machinePrice IS NOT NULL AND machinePrice != 0 ";
		if(machineInfo.getBmaID()!=null&&!"".equals(machineInfo.getBmaID())){
			hql+=" AND bmaID=:bmaID";
			param.put("bmaID", machineInfo.getBmaID());
		}
		if(machineInfo.getMachineType()!=null&&!"".equals(machineInfo.getMachineType())){
			hql+=" AND machineType=:machineType";
			param.put("machineType", machineInfo.getMachineType());
		}
		if (machineInfo.getSort() != null) {
			hql += " order by " + machineInfo.getSort() + " " + machineInfo.getOrder();
		}
		List<MachineInfoModel> machineInfoList = machineInfoDao.queryObjectsByHqlList(hql, param);
		
		return machineInfoList;
	}

	/**
	 * 查询机具库存显示到相应的select
	 */
	@Override
	public List<MachineInfoBean> searchMachineInfo(String nameCode) {
		StringBuffer sb = new StringBuffer("from MachineInfoModel m where m.maintainType != :maintainType and m.machineType=4 ");
		if(nameCode !=null){
			sb.append(" and (m.machineModel like '%" + nameCode + "%' or m.machineType like '%" + nameCode + "%')");
		}
		sb.append(" order by m.bmaID desc");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maintainType", "D");
		List<MachineInfoModel> machineInfoModelList = machineInfoDao.queryObjectsByHqlList(sb.toString(), map);
		List<MachineInfoBean> machineInfoList = new ArrayList<MachineInfoBean>();
		for (MachineInfoModel machineInfo : machineInfoModelList) {
			MachineInfoBean merchantInfoBean = new MachineInfoBean();
			BeanUtils.copyProperties(machineInfo, merchantInfoBean);
			
			if("1".equals(merchantInfoBean.getMachineType())){
				merchantInfoBean.setMachineTypeName("有线(拨号)");
			}else if("2".equals(merchantInfoBean.getMachineType())){
				merchantInfoBean.setMachineTypeName("有线(拨号和网络)");
			}else if("3".equals(merchantInfoBean.getMachineType())){
				merchantInfoBean.setMachineTypeName("无线");
			}else{
				merchantInfoBean.setMachineTypeName("手刷");
			}
			
			machineInfoList.add(merchantInfoBean);
		}
		
		return machineInfoList;
	}

	/**
	 * 判断是否是重复记录
	 */
	@Override
	public Boolean checkInfo(MachineInfoBean machineInfoBean) {
		Map<String, Object> param=new HashMap<String, Object>();
		String hql = "from MachineInfoModel where 1 = 1";
		if(machineInfoBean.getMachineModel()!=null&&!"".equals(machineInfoBean.getMachineModel())){
			hql+=" AND machineModel=:machineModel";
			param.put("machineModel", machineInfoBean.getMachineModel());
		}
		if(machineInfoBean.getMachineType()!=null&&!"".equals(machineInfoBean.getMachineType())){
			hql+=" AND machineType=:machineType";
			param.put("machineType", machineInfoBean.getMachineType());
		}
		List<MachineInfoModel> list=machineInfoDao.queryObjectsByHqlList(hql, param);
		if(list.size()>0){
			return false;
		}
		return true;
	}
	/**
	 * 查询机具库存显示到相应的select(非M35)
	 */
	@Override
	public List<MachineInfoBean> searchNormalMachineInfo(String nameCode) {
		StringBuffer sb = new StringBuffer("from MachineInfoModel m where m.maintainType != :maintainType and m.machineType !=4  ");
		if(nameCode !=null){
			sb.append(" and (m.machineModel like '%" + nameCode + "%' or m.machineType like '%" + nameCode + "%')");
		}
		sb.append(" order by m.bmaID desc");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maintainType", "D");
		List<MachineInfoModel> machineInfoModelList = machineInfoDao.queryObjectsByHqlList(sb.toString(), map);
		List<MachineInfoBean> machineInfoList = new ArrayList<MachineInfoBean>();
		for (MachineInfoModel machineInfo : machineInfoModelList) {
			MachineInfoBean merchantInfoBean = new MachineInfoBean();
			BeanUtils.copyProperties(machineInfo, merchantInfoBean);
			
			if("1".equals(merchantInfoBean.getMachineType())){
				merchantInfoBean.setMachineTypeName("有线(拨号)");
			}else if("2".equals(merchantInfoBean.getMachineType())){
				merchantInfoBean.setMachineTypeName("有线(拨号和网络)");
			}else if("3".equals(merchantInfoBean.getMachineType())){
				merchantInfoBean.setMachineTypeName("无线");
			}else{
				merchantInfoBean.setMachineTypeName("手刷");
			}
			
			machineInfoList.add(merchantInfoBean);
		}
		
		return machineInfoList;
	}
}
