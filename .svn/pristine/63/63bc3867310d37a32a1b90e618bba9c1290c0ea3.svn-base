package com.hrt.biz.bill.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hrt.biz.bill.entity.model.MachineInfoModel;
import com.hrt.biz.bill.entity.pagebean.MachineInfoBean;
import com.hrt.biz.bill.service.IMachineInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 机具库存管理
 */
public class MachineInfoAction extends BaseAction implements ModelDriven<MachineInfoBean>{

	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(MachineInfoAction.class);
	
	private IMachineInfoService machineInfoService;
	
	private String ids;
	
	private MachineInfoBean machineInfoBean=new MachineInfoBean();

	@Override
	public MachineInfoBean getModel() {
		return machineInfoBean;
	}

	public IMachineInfoService getMachineInfoService() {
		return machineInfoService;
	}

	public void setMachineInfoService(IMachineInfoService machineInfoService) {
		this.machineInfoService = machineInfoService;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 分页查询机具库存
	 */
	public void init(){
		DataGridBean dgb = new DataGridBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		}else{
			try {
				dgb = machineInfoService.queryMachineInfos(machineInfoBean);
			} catch (Exception e) {
				log.error("分页查询机具库存信息异常：" + e);
			}
		}
		super.writeJson(dgb);
	}
	
	/**
	 * 分页查询机具库存（可根据条件查询）
	 */
	public void getInfo(){
		String type=getRequest().getParameter("machineType");
		String id=getRequest().getParameter("bmaID");
		if(type!=null&&!"".equals(type)){
			machineInfoBean.setMachineType(type);
		}
		if(id!=null&&!"".equals(id)){
			machineInfoBean.setBmaID(Integer.valueOf(id));
		}
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		}else{
			List<MachineInfoModel> list = new ArrayList<MachineInfoModel>();
			try {
				list = machineInfoService.getInfo(machineInfoBean);
			} catch (Exception e) {
				log.error("分页查询机具库存信息异常：" + e);
			}
			super.writeJson(list);
		}
	}
	
	/**
	 * 查询机具库存在下拉中显式（机具预购申请中使用）
	 */
	public void searchMachineInfoType(){
		String type=getRequest().getParameter("machineType");
		String id=getRequest().getParameter("bmaID");
		if(type!=null&&!"".equals(type)){
			machineInfoBean.setMachineType(type);
		}
		if(id!=null&&!"".equals(id)){
			machineInfoBean.setBmaID(Integer.valueOf(id));
		}
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		}else{
			List<MachineInfoModel> list = new ArrayList<MachineInfoModel>();
			try {
				list = machineInfoService.searchMachineInfoType(machineInfoBean);
			} catch (Exception e) {
				log.error("查询机具库存信息异常：" + e);
			}
			super.writeJson(list);
		}
	}
	
	/**
	 * 添加机具库存
	 */
	public void add(){
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				Boolean falg=machineInfoService.checkInfo(machineInfoBean);
				if(!falg){
					json.setSuccess(false);
					json.setMsg("不能添加重复记录");
				}else{
					machineInfoService.save(machineInfoBean, (UserBean)userSession);
					json.setSuccess(true);
					json.setMsg("添加机具库存成功");
				}
			} catch (Exception e) {
				log.error("新增机具库存异常：" + e);
				json.setMsg("添加机具库存失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 修改机具库存
	 */
	public void edit() {
		JsonBean json = new JsonBean();
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
			json.setSessionExpire(true);
		} else {
			try {
				machineInfoService.update(machineInfoBean, (UserBean)userSession);
				json.setSuccess(true);
				json.setMsg("修改机具库存成功");
			} catch (Exception e) {
				log.error("修改机具库存异常" + e);
				json.setMsg("修改机具库存失败");
			}
		}
		super.writeJson(json);
	}
	
	/**
	 * 删除机具库存
	 */
	public void delete() {
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		}else{
			JsonBean json = new JsonBean();
			try {
				machineInfoService.delete(Integer.parseInt(ids));
				json.setSuccess(true);
				json.setMsg("删除机具库存成功");
			} catch (Exception e) {
				log.error("删除机具库存异常：" + e);
				json.setMsg("删除机具库存失败");
			}
			super.writeJson(json);
		}
	}

	/**
	 * 查询机具库存显示到相应的select
	 */
	public void searchMachineInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		}else{
			List<MachineInfoBean> machineInfoList = null;
			try {
				String nameCode = super.getRequest().getParameter("q");
				machineInfoList = machineInfoService.searchMachineInfo(nameCode);
			} catch (Exception e) {
				log.error("查询机具异常：" + e);
			}
			super.writeJson(machineInfoList);
		}
	}
	/**
	 * 查询机具库存显示到相应的select(非M35)
	 */
	public void searchNormalMachineInfo(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		//session失效
		if (userSession == null) {
		}else{
			List<MachineInfoBean> machineInfoList = null;
			try {
				String nameCode = super.getRequest().getParameter("q");
				machineInfoList = machineInfoService.searchNormalMachineInfo(nameCode);
			} catch (Exception e) {
				log.error("查询机具异常：" + e);
			}
			super.writeJson(machineInfoList);
		}
	}
}
