package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.dao.IAgentUnitTaskDetail1Dao;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail1Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail2Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.biz.bill.service.IAgentUnitTaskDetail1Service;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.AgentUnitInfo;
import com.hrt.util.StringUtil;

public class AgentUnitTaskDetail1ServiceImpl implements IAgentUnitTaskDetail1Service {
	
	private IAgentUnitTaskDetail1Dao agentUnitTaskDetail1Dao;
	private IAgentUnitDao agentUnitDao;

	public IAgentUnitDao getAgentUnitDao() {
		return agentUnitDao;
	}

	public void setAgentUnitDao(IAgentUnitDao agentUnitDao) {
		this.agentUnitDao = agentUnitDao;
	}

	public IAgentUnitTaskDetail1Dao getAgentUnitTaskDetail1Dao() {
		return agentUnitTaskDetail1Dao;
	}

	public void setAgentUnitTaskDetail1Dao(IAgentUnitTaskDetail1Dao agentUnitTaskDetail1Dao) {
		this.agentUnitTaskDetail1Dao = agentUnitTaskDetail1Dao;
	}

	@Override
	public AgentUnitTaskDetail1Model queryAgentUnitTaskDetail1(AgentUnitTaskBean agentUnitTask,UserBean user) {
		AgentUnitTaskDetail1Model model = agentUnitTaskDetail1Dao.queryObjectByHql("from AgentUnitTaskDetail1Model where bautdid="+agentUnitTask.getBautdid()+" ", new Object[]{});
		return model;
	}

	@Override
	public AgentUnitTaskDetail1Model saveAgentUnitTaskDetail1(AgentUnitTaskBean agentUnitTask, UserBean user) throws Exception {
		AgentUnitTaskDetail1Model model = new AgentUnitTaskDetail1Model();
		BeanUtils.copyProperties(agentUnitTask,model);
		AgentUnitTaskDetail1Model model2 = uploadImg(agentUnitTask,model,user);
		agentUnitTaskDetail1Dao.saveObject(model2);
		return model;
	}

	@Override
	public AgentUnitTaskDetail1Model updateAgentUnitTaskDetail1(AgentUnitTaskBean agentUnitTask, UserBean user) throws Exception {
		AgentUnitTaskDetail1Model model = agentUnitTaskDetail1Dao.queryObjectByHql("from AgentUnitTaskDetail1Model where bautdid="+agentUnitTask.getBautdid()+"", new Object[]{});
		model.setAgentName(agentUnitTask.getAgentName());
		model.setBno(agentUnitTask.getBno());
		model.setBaddr(agentUnitTask.getBaddr());
		model.setLegalPerson(agentUnitTask.getLegalPerson());
		model.setLegalType(agentUnitTask.getLegalType());
		model.setLegalNum(agentUnitTask.getLegalNum());
		if(agentUnitTask.getLegalAUpLoad()!=null&&!"".equals(agentUnitTask.getLegalAUpLoad())){
			model.setLegalAUpLoad(agentUnitTask.getLegalAUpLoad());
		}
		if(agentUnitTask.getLegalBUpLoad()!=null&&!"".equals(agentUnitTask.getLegalBUpLoad())){
			model.setLegalBUpLoad(agentUnitTask.getLegalBUpLoad());
		}
		if(agentUnitTask.getBusLicUpLoad()!=null&&!"".equals(agentUnitTask.getBusLicUpLoad())){
			model.setBusLicUpLoad(agentUnitTask.getBusLicUpLoad());
		}
		if(agentUnitTask.getDealUpLoad()!=null&&!"".equals(agentUnitTask.getDealUpLoad())){
			model.setDealUpLoad(agentUnitTask.getDealUpLoad());
		}
		AgentUnitTaskDetail1Model model2 = uploadImg(agentUnitTask,model,user);
		agentUnitTaskDetail1Dao.updateObject(model2);
		return model;
	}
	private AgentUnitTaskDetail1Model uploadImg(AgentUnitTaskBean agentUnitTask, AgentUnitTaskDetail1Model model, UserBean user)
			throws Exception {
		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		Integer buid = agentUnitTaskDetail1Dao.querysqlCounts2("select buid from BILL_AGENTUNITINFO where unno='"+user.getUnNo()+"'",null);
		String nextVal = buid.toString();
		
		//保存图片
		//机构号.id.日期yyyyMMdd.字段名.扩展名
		//协议签章页照片
		if (agentUnitTask.getDealUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getDealUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".dealUpLoadFile");
			fName.append(agentUnitTask.getDealUpLoad().substring(agentUnitTask.getDealUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getDealUpLoadFile(), fName.toString(), imageDay);
			model.setDealUpLoad(imageDay+File.separator+fName.toString());
		}
		//营业执照（企业必传）
		if (agentUnitTask.getBusLicUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getBusLicUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".busLicUpLoadFile");
			fName.append(agentUnitTask.getBusLicUpLoad().substring(agentUnitTask.getBusLicUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getBusLicUpLoadFile(), fName.toString(), imageDay);
			model.setBusLicUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证正面
		if (agentUnitTask.getLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".legalAUploadFile");
			fName.append(agentUnitTask.getLegalAUpLoad().substring(agentUnitTask.getLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getLegalAUpLoadFile(), fName.toString(), imageDay);
			model.setLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//法人身份证反面
		if (agentUnitTask.getLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".legalBUploadFile");
			fName.append(agentUnitTask.getLegalBUpLoad().substring(agentUnitTask.getLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getLegalBUpLoadFile(), fName.toString(), imageDay);
			model.setLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		return model;
	}
	private void uploadFile(File upload, String fName, String imageDay) throws Exception {
		try {
			String title="AgentInfo";
			List<Map<String, String>> list = agentUnitTaskDetail1Dao.queryObjectsBySqlListMap("SELECT * FROM SYS_PARAM WHERE TITLE='"+title+"'",null);
			String savePath = list.get(0).get("UPLOAD_PATH");
			if(null==savePath){
				throw new Exception("获取代理商图片保存路径错误");
			}
			String realPath = savePath + File.separator + imageDay;
			File dir = new File(realPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String fPath = realPath + File.separator + fName;
			File destFile = new File(fPath);
			FileInputStream fis = new FileInputStream(upload);
			FileOutputStream fos = new FileOutputStream(destFile);
			byte [] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer))>0){
				fos.write(buffer,0,len);
			} 
			fos.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("代理商图片保存错误");
		}
	}
	
	@Override
	public AgentUnitInfo updateAgentUnitTaskDetail1Y(AgentUnitInfo agentUnitInfo,AgentUnitTaskModel model, UserBean user) {
		AgentUnitTaskDetail1Model model2 = agentUnitTaskDetail1Dao.queryObjectByHql("from AgentUnitTaskDetail1Model where bautdid="+model.getBautdid()+"", new Object[]{});
		AgentUnitModel agentUnitModel = agentUnitDao.queryObjectByHql("from AgentUnitModel where unno = '"+model.getUnno()+"' ", new Object[]{});
		agentUnitModel.setAgentName(model2.getAgentName());
		agentUnitModel.setBno(model2.getBno());
		agentUnitModel.setBaddr(model2.getBaddr());
		agentUnitModel.setLegalPerson(model2.getLegalPerson());
		agentUnitModel.setLegalType(model2.getLegalType());
		agentUnitModel.setLegalNum(model2.getLegalNum());
		agentUnitDao.updateObject(agentUnitModel);
		//同步修改sys_unit的机构名称
		agentUnitDao.executeUpdate("update sys_unit set un_name='"+model2.getAgentName()+"' where unno='"+model.getUnno()+"'");
		//同步修改sys_user的管理员名称
		agentUnitDao.executeUpdate("update sys_user set user_name='"+model2.getAgentName()+"管理员' where user_id = (select min(user_id) from sys_unit_user where unno = '"+model.getUnno()+"')");
		agentUnitInfo.setAgentName(model2.getAgentName());
		agentUnitInfo.setBno(model2.getBno());
		agentUnitInfo.setBaddr(model2.getBaddr());
		agentUnitInfo.setLegalPerson(model2.getLegalPerson());
		agentUnitInfo.setLegalType(model2.getLegalType());
		agentUnitInfo.setLegalNum(model2.getLegalNum());
		return agentUnitInfo;
	}
}
