package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.dao.IAgentUnitTaskDetail2Dao;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail1Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail2Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskDetail3Model;
import com.hrt.biz.bill.entity.model.AgentUnitTaskModel;
import com.hrt.biz.bill.entity.pagebean.AgentUnitTaskBean;
import com.hrt.biz.bill.service.IAgentUnitTaskDetail2Service;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.gmms.webservice.AgentUnitInfo;
import com.hrt.util.StringUtil;

public class AgentUnitTaskDetail2ServiceImpl implements IAgentUnitTaskDetail2Service {
	
	private IAgentUnitTaskDetail2Dao agentUnitTaskDetail2Dao;
	private IAgentUnitDao agentUnitDao;

	public IAgentUnitDao getAgentUnitDao() {
		return agentUnitDao;
	}

	public void setAgentUnitDao(IAgentUnitDao agentUnitDao) {
		this.agentUnitDao = agentUnitDao;
	}
	
	public IAgentUnitTaskDetail2Dao getAgentUnitTaskDetail2Dao() {
		return agentUnitTaskDetail2Dao;
	}

	public void setAgentUnitTaskDetail2Dao(IAgentUnitTaskDetail2Dao agentUnitTaskDetail2Dao) {
		this.agentUnitTaskDetail2Dao = agentUnitTaskDetail2Dao;
	}

	@Override
	public AgentUnitTaskDetail2Model queryAgentUnitTaskDetail2(AgentUnitTaskBean agentUnitTask,UserBean user) {
		AgentUnitTaskDetail2Model model = agentUnitTaskDetail2Dao.queryObjectByHql("from AgentUnitTaskDetail2Model where bautdid="+agentUnitTask.getBautdid()+" ", new Object[]{});
		return model;
	}

	@Override
	public AgentUnitTaskDetail2Model saveAgentUnitTaskDetail2(AgentUnitTaskBean agentUnitTask, UserBean user) throws Exception {
		AgentUnitTaskDetail2Model model = new AgentUnitTaskDetail2Model();
		BeanUtils.copyProperties(agentUnitTask,model);
		AgentUnitTaskDetail2Model model2 = uploadImg(agentUnitTask,model,user);
		agentUnitTaskDetail2Dao.saveObject(model2);
		return model;
	}

	@Override
	public AgentUnitTaskDetail2Model updateAgentUnitTaskDetail2(AgentUnitTaskBean agentUnitTask, UserBean user) throws Exception {
		AgentUnitTaskDetail2Model model = agentUnitTaskDetail2Dao.queryObjectByHql("from AgentUnitTaskDetail2Model where bautdid="+agentUnitTask.getBautdid()+"", new Object[]{});
		model.setAccType(agentUnitTask.getAccType());
		model.setBankBranch(agentUnitTask.getBankBranch());
		model.setBankAccNo(agentUnitTask.getBankAccNo());
		model.setBankAccName(agentUnitTask.getBankAccName());
		model.setBankType(agentUnitTask.getBankType());
		model.setAreaType(agentUnitTask.getAreaType());
		model.setBankArea(agentUnitTask.getBankArea());
		if(agentUnitTask.getAccountAuthUpLoad()!=null&&!"".equals(agentUnitTask.getAccountAuthUpLoad())){
			model.setAccountAuthUpLoad(agentUnitTask.getAccountAuthUpLoad());
		}
		if(agentUnitTask.getAccountLegalAUpLoad()!=null&&!"".equals(agentUnitTask.getAccountLegalAUpLoad())){
			model.setAccountLegalAUpLoad(agentUnitTask.getAccountLegalAUpLoad());
		}
		if(agentUnitTask.getAccountLegalBUpLoad()!=null&&!"".equals(agentUnitTask.getAccountLegalBUpLoad())){
			model.setAccountLegalBUpLoad(agentUnitTask.getAccountLegalBUpLoad());
		}
		if(agentUnitTask.getAccountLegalHandUpLoad()!=null&&!"".equals(agentUnitTask.getAccountLegalHandUpLoad())){
			model.setAccountLegalHandUpLoad(agentUnitTask.getAccountLegalHandUpLoad());
		}
		AgentUnitTaskDetail2Model model2 = uploadImg(agentUnitTask,model,user);
		agentUnitTaskDetail2Dao.updateObject(model2);
		return model;
	}
	private AgentUnitTaskDetail2Model uploadImg(AgentUnitTaskBean agentUnitTask, AgentUnitTaskDetail2Model model, UserBean user)
			throws Exception {
		String imageDay = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		Integer buid = agentUnitTaskDetail2Dao.querysqlCounts2("select buid from BILL_AGENTUNITINFO where unno='"+user.getUnNo()+"'",null);
		String nextVal = buid.toString();
		
		//保存图片
		//机构号.id.日期yyyyMMdd.字段名.扩展名
		//协议签章页照片
		//入账授权书
		if (agentUnitTask.getAccountAuthUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getAccountAuthUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountAuthUpLoadFile");
			fName.append(agentUnitTask.getAccountAuthUpLoad().substring(agentUnitTask.getAccountAuthUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getAccountAuthUpLoadFile(), fName.toString(), imageDay);
			model.setAccountAuthUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证正面
		if (agentUnitTask.getAccountLegalAUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getAccountLegalAUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalAUpLoadFile");
			fName.append(agentUnitTask.getAccountLegalAUpLoad().substring(agentUnitTask.getAccountLegalAUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getAccountLegalAUpLoadFile(), fName.toString(), imageDay);
			model.setAccountLegalAUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人身份证反面
		if (agentUnitTask.getAccountLegalBUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getAccountLegalBUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalBUpLoadFile");
			fName.append(agentUnitTask.getAccountLegalBUpLoad().substring(agentUnitTask.getAccountLegalBUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getAccountLegalBUpLoadFile(), fName.toString(), imageDay);
			model.setAccountLegalBUpLoad(imageDay+File.separator+fName.toString());
		}
		//入账人手持身份证
		if (agentUnitTask.getAccountLegalHandUpLoadFile() != null && StringUtil.isNotEmpty(agentUnitTask.getAccountLegalHandUpLoad())) {
			StringBuffer fName = new StringBuffer();
			fName.append(user.getUnNo() + ".");
			fName.append(nextVal + ".");
			fName.append(imageDay);
			fName.append(".accountLegalHandUpLoadFile");
			fName.append(agentUnitTask.getAccountLegalHandUpLoad().substring(agentUnitTask.getAccountLegalHandUpLoad().lastIndexOf(".")).toLowerCase());
			uploadFile(agentUnitTask.getAccountLegalHandUpLoadFile(), fName.toString(), imageDay);
			model.setAccountLegalHandUpLoad(imageDay+File.separator+fName.toString());
		}
		return model;
	}
	private void uploadFile(File upload, String fName, String imageDay) throws Exception {
		try {
			String title="AgentInfo";
			List<Map<String, String>> list = agentUnitTaskDetail2Dao.queryObjectsBySqlListMap("SELECT * FROM SYS_PARAM WHERE TITLE='"+title+"'",null);
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
	public AgentUnitInfo updateAgentUnitTaskDetail2Y(AgentUnitInfo agentUnitInfo,AgentUnitTaskModel model, UserBean user) {
		AgentUnitTaskDetail2Model model2 = agentUnitTaskDetail2Dao.queryObjectByHql("from AgentUnitTaskDetail2Model where bautdid="+model.getBautdid()+"", new Object[]{});
		AgentUnitModel agentUnitModel = agentUnitDao.queryObjectByHql("from AgentUnitModel where unno = '"+model.getUnno()+"' ", new Object[]{});
		agentUnitModel.setAccType(model2.getAccType());
		agentUnitModel.setBankBranch(model2.getBankBranch());
		agentUnitModel.setBankAccNo(model2.getBankAccNo());
		agentUnitModel.setBankAccName(model2.getBankAccName());
		agentUnitModel.setBankType(model2.getBankType());
		agentUnitModel.setAreaType(model2.getAreaType());
		agentUnitModel.setBankArea(model2.getBankArea());
		agentUnitDao.updateObject(agentUnitModel);
		agentUnitInfo.setAccType(model2.getAccType());
		agentUnitInfo.setBankBranch(model2.getBankBranch());  
		agentUnitInfo.setBankAccNo(model2.getBankAccNo());    
		agentUnitInfo.setBankAccName(model2.getBankAccName());
		agentUnitInfo.setBankType(model2.getBankType());      
		agentUnitInfo.setAreaType(model2.getAreaType());      
		agentUnitInfo.setBankArea(model2.getBankArea());      
		return agentUnitInfo;
	}
}
