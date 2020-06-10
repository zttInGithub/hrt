package com.hrt.biz.bill.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IMerchantTerminalInfoDao;
import com.hrt.biz.bill.dao.IMerchantUpdateTerDao;
import com.hrt.biz.bill.dao.ITerminalInfoDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTerminalInfoModel;
import com.hrt.biz.bill.entity.model.MerchantUpdateTerModel;
import com.hrt.biz.bill.entity.model.TerminalInfoModel;
import com.hrt.biz.bill.entity.pagebean.MerchantUpdateTerBean;
import com.hrt.biz.bill.service.IMerchantUpdateTerService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.HttpXmlClient;

public class MerchantUpdateTerServiceImpl implements IMerchantUpdateTerService{
	
	private IMerchantUpdateTerDao merchantUpdateTerDao;
	private ITerminalInfoDao terminalInfoDao;
	private IMerchantInfoDao merchantInfoDao;
	private IMerchantTerminalInfoDao merchantTerminalInfoDao;
	private String admAppIp;

	public String getAdmAppIp() {
		return admAppIp;
	}

	public void setAdmAppIp(String admAppIp) {
		this.admAppIp = admAppIp;
	}

	public IMerchantTerminalInfoDao getMerchantTerminalInfoDao() {
		return merchantTerminalInfoDao;
	}

	public void setMerchantTerminalInfoDao(IMerchantTerminalInfoDao merchantTerminalInfoDao) {
		this.merchantTerminalInfoDao = merchantTerminalInfoDao;
	}

	public IMerchantInfoDao getMerchantInfoDao() {
		return merchantInfoDao;
	}

	public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
		this.merchantInfoDao = merchantInfoDao;
	}

	public ITerminalInfoDao getTerminalInfoDao() {
		return terminalInfoDao;
	}

	public void setTerminalInfoDao(ITerminalInfoDao terminalInfoDao) {
		this.terminalInfoDao = terminalInfoDao;
	}

	public IMerchantUpdateTerDao getMerchantUpdateTerDao() {
		return merchantUpdateTerDao;
	}

	public void setMerchantUpdateTerDao(IMerchantUpdateTerDao merchantUpdateTerDao) {
		this.merchantUpdateTerDao = merchantUpdateTerDao;
	}

	@Override
	public DataGridBean queryMerchantUpdateTer(MerchantUpdateTerBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "";
		if("110000".equals(user.getUnNo())) {
			sql = "select a.*,b.rname from bill_merchantupdateter a,bill_merchantinfo b where a.mid=b.mid ";
		}else {
			sql = "select a.*,b.rname from bill_merchantupdateter a,bill_merchantinfo b where a.mid=b.mid and unno = '"+user.getUnNo()+"'";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if(bean.getMid()!=null && !"".equals(bean.getMid())) {
			sql += " and a.mid=:mid ";
			map.put("mid", bean.getMid());
		}
		if(bean.getType()!=null && !"".equals(bean.getType())) {
			sql += " and a.type=:type";
			map.put("type", bean.getType());
		}
		if(bean.getApproveType()!=null && !"".equals(bean.getApproveType())) {
			sql += " and a.approvetype=:approvetype";
			map.put("approvetype", bean.getApproveType());
		}
		if(bean.getApproveDate()!=null && !"".equals(bean.getApproveDate())) {
			sql += " and a.approvedate >= to_date('"+sdf.format(bean.getApproveDate())+"000000','yyyymmddhh24miss')";
		}
		if(bean.getApproveDateEnd()!=null && !"".equals(bean.getApproveDateEnd())) {
			sql += " and a.approvedate <= to_date('"+sdf.format(bean.getApproveDateEnd())+"235959','yyyymmddhh24miss')";
		}
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by a.bmutID desc";
		List<Map<String,Object>> list = merchantUpdateTerDao.queryObjectsBySqlList2(sql, map, bean.getPage(), bean.getRows());
		BigDecimal counts = merchantUpdateTerDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public DataGridBean queryMerchantUpdateTerFor(MerchantUpdateTerBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "select a.*,b.rname from bill_merchantupdateter a,bill_merchantinfo b where a.mid=b.mid and approvetype='W' ";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if(bean.getMid()!=null && !"".equals(bean.getMid())) {
			sql += " and a.mid=:mid ";
			map.put("mid", bean.getMid());
		}
		if(bean.getType()!=null && !"".equals(bean.getType())) {
			sql += " and a.type=:type";
			map.put("type", bean.getType());
		}
		if(bean.getApproveType()!=null && !"".equals(bean.getApproveType())) {
			sql += " and a.approvetype=:approvetype";
			map.put("approvetype", bean.getApproveType());
		}
		if(bean.getApproveDate()!=null && !"".equals(bean.getApproveDate())) {
			sql += " and a.approvedate >= to_date('"+sdf.format(bean.getApproveDate())+"000000','yyyymmddhh24miss')";
		}
		if(bean.getApproveDateEnd()!=null && !"".equals(bean.getApproveDateEnd())) {
			sql += " and a.approvedate <= to_date('"+sdf.format(bean.getApproveDateEnd())+"235959','yyyymmddhh24miss')";
		}
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by a.bmutID desc";
		List<Map<String,Object>> list = merchantUpdateTerDao.queryObjectsBySqlList2(sql, map, bean.getPage(), bean.getRows());
		BigDecimal counts = merchantUpdateTerDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	
	@Override
	public JsonBean updateMerTerBack(MerchantUpdateTerBean bean, UserBean user) {
		JsonBean json = new JsonBean();
		MerchantUpdateTerModel model = merchantUpdateTerDao.queryObjectByHql("from MerchantUpdateTerModel where bmutID=? and approveType='W'", new Object[] {bean.getBmutID()});
		if(model==null) {
			json.setSuccess(false);
			json.setMsg("未查询到待审核申请");
			return json;
		}
		model.setApproveType("K");
		model.setApproveDate(new Date());
		json.setSuccess(true);
		json.setMsg("退回成功");
		return json;
	}

	@Override
	public JsonBean updateMerTerGo(MerchantUpdateTerBean bean, UserBean user) {
		JsonBean json = new JsonBean();
		MerchantUpdateTerModel model = merchantUpdateTerDao.queryObjectByHql("from MerchantUpdateTerModel where bmutID=? and approveType='W'", new Object[] {bean.getBmutID()});
		if(model==null) {
			json.setSuccess(false);
			json.setMsg("未查询到待审核申请");
			return json;
		}
		TerminalInfoModel infoModel = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where sn=? and maintainType!='D'", new Object[] {model.getSn()});
		if(infoModel!=null) {
			json.setSuccess(false);
			json.setMsg("所申请SN已存在请重新选择");
			return json;
		}
		TerminalInfoModel infoModel2 = terminalInfoDao.queryObjectByHql("from TerminalInfoModel where termID=? and maintainType!='D'", new Object[] {model.getTid()});
		if(infoModel2==null) {
			json.setSuccess(false);
			json.setMsg("未查询到设备");
			return json;
		}
		//推送综合
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("type", "2");
		map2.put("mid", model.getMid());
		map2.put("tid", model.getTid());
		map2.put("sn", model.getSn());
		String post = HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/merchant_receiveUpBD.action", map2);
		boolean flag = (Boolean) ((Map<String, Object>) JSONObject.parse(post)).get("success");
		if(!flag){
			json.setSuccess(false);
			json.setMsg("通过失败");
			return json;
		}
		infoModel2.setSn(model.getSn());
		infoModel2.setMaintainType("M");
		infoModel2.setMaintainDate(new Date());
		infoModel2.setMaintainUID(user.getUserID());
		terminalInfoDao.updateObject(infoModel2);
		MerchantTerminalInfoModel model2 = merchantTerminalInfoDao.queryObjectByHql("from MerchantTerminalInfoModel where tid=? and mid=?", new Object[] {model.getTid(),model.getMid()});
		model2.setSn(model.getSn());
		merchantTerminalInfoDao.updateObject(model2);
		model.setApproveDate(new Date());
		model.setApproveType("Y");
		merchantUpdateTerDao.updateObject(model);
		json.setSuccess(true);
		json.setMsg("通过成功");
		return json;
	}

	@Override
	public List<Object> queryDetail(MerchantUpdateTerBean bean) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		List<Map<String, Object>> list2 = merchantUpdateTerDao.queryObjectsBySqlList(sql);
		String upload = (String) list2.get(0).get("UPLOAD_PATH");
		MerchantUpdateTerModel model = merchantUpdateTerDao.queryObjectByHql("from MerchantUpdateTerModel where bmutID=? ", new Object[] {bean.getBmutID()});
		list.add(model);
		if(model.getSnImg()!=null && !"".equals(model.getSnImg())) {
			list.add(upload + File.separator + model.getSnImg().substring(16, 24) + File.separator + model.getSnImg());
		}
		return list;
	}

	@Override
	public JsonBean addMerUpdateTer1(MerchantUpdateTerBean bean, UserBean user) throws Exception {
		JsonBean json = new JsonBean();
		MerchantUpdateTerModel merchantUpdateTerModel = merchantUpdateTerDao.queryObjectByHql("from MerchantUpdateTerModel where mid=? and approveType='W'", new Object[] {bean.getMid()});
		if(merchantUpdateTerModel!=null) {
			json.setSuccess(false);
			json.setMsg("该商户有待审核换机申请，请耐心等待");
			return json;
		}
		//确认商户是当前机构的直属商户
		MerchantInfoModel merchantInfoModel = merchantInfoDao.queryObjectByHql("from MerchantInfoModel where unno=? and mid=?", new Object[] {user.getUnNo(),bean.getMid()});
		if(merchantInfoModel==null) {
			json.setSuccess(false);
			json.setMsg("商户号填写错误，请确认");
			return json;
		}
		//查询中间关系
		MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.queryObjectByHql("from MerchantTerminalInfoModel where mid=? and tid=? and maintainType!='D'", new Object[] {bean.getMid(),bean.getTid()});
		if(merchantTerminalInfoModel==null) {
			json.setSuccess(false);
			json.setMsg("商户号或终端号填写错误，请确认");
			return json;
		}
		//请求综合，判断当前入网TUSN是否重复
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("sn", bean.getSn());
		String post = HttpXmlClient.post(admAppIp+"/AdmApp/bank/bankTermAcc_querySnIfExist.action", map2);
		Integer flag = (Integer) ((Map<String, Object>) JSONObject.parse(post)).get("obj");
		if(flag==1){
			json.setSuccess(false);
			json.setMsg("TUSN已使用，请确认");
			return json;
		}
		MerchantUpdateTerModel model = new MerchantUpdateTerModel();
		model.setUnno(user.getUnNo());
		model.setMid(bean.getMid());
		model.setTid(bean.getTid());
		model.setSn(bean.getSn());
		//SN照片保存
		String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
		if(bean.getSnImg() != null && !"".equals(bean.getSnImg())){
			StringBuffer fName = new StringBuffer();
			fName.append(model.getMid());
			fName.append(".");
			fName.append(imageDay);
			fName.append(".snImg");
			fName.append(bean.getSnImg().substring(bean.getSnImg().lastIndexOf(".")).toLowerCase());
			uploadFile(bean.getSnImgFile(), fName.toString(),imageDay);
			model.setSnImg(fName.toString());
		}
		model.setType("1");
		model.setMaintainDate(new Date());
		model.setMaintainType("A");
		model.setApproveType("Y");
		merchantUpdateTerDao.saveObject(model);
		
		json.setSuccess(true);
		json.setMsg("换机申请提交成功");
		return json;
	}

	@Override
	public JsonBean addMerUpdateTer2(MerchantUpdateTerBean bean, UserBean user) {
		JsonBean json = new JsonBean();
		//确认商户是当前机构的直属商户
		MerchantInfoModel merchantInfoModel = merchantInfoDao.queryObjectByHql("from MerchantInfoModel where unno=? and mid=?", new Object[] {user.getUnNo(),bean.getMid()});
		if(merchantInfoModel==null) {
			json.setSuccess(false);
			json.setMsg("商户号填写错误，请确认");
			return json;
		}
		//查询中间关系
		MerchantTerminalInfoModel merchantTerminalInfoModel = merchantTerminalInfoDao.queryObjectByHql("from MerchantTerminalInfoModel where mid=? and tid=? and maintainType!='D'", new Object[] {bean.getMid(),bean.getTid()});
		if(merchantTerminalInfoModel==null) {
			json.setSuccess(false);
			json.setMsg("商户号或终端号填写错误，请确认");
			return json;
		}
		//推送综合
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("type", "3");
		map2.put("mid", merchantInfoModel.getMid());
		map2.put("tid", merchantTerminalInfoModel.getTid());
		map2.put("sn", merchantTerminalInfoModel.getSn());
		String post = HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/merchant_receiveUpBD.action", map2);
		boolean flag = (Boolean) ((Map<String, Object>) JSONObject.parse(post)).get("success");
		if(!flag){
			json.setSuccess(false);
			json.setMsg("通过失败");
			return json;
		}
		MerchantUpdateTerModel model = new MerchantUpdateTerModel();
		model.setUnno(user.getUnNo());
		model.setMid(bean.getMid());
		model.setTid(bean.getTid());
		model.setType("2");
		model.setMaintainDate(new Date());
		model.setMaintainType("A");
		model.setApproveType("Y");
		merchantUpdateTerDao.saveObject(model);
		
		json.setSuccess(true);
		json.setMsg("撤机成功");
		return json;
	}

	@Override
	public JsonBean addMerUpdateTer3(MerchantUpdateTerBean bean, UserBean user) {
		JsonBean json = new JsonBean();
		//确认商户是当前机构的直属商户
		MerchantInfoModel merchantInfoModel = merchantInfoDao.queryObjectByHql("from MerchantInfoModel where unno=? and mid=?", new Object[] {user.getUnNo(),bean.getMid()});
		if(merchantInfoModel==null) {
			json.setSuccess(false);
			json.setMsg("商户号填写错误，请确认");
			return json;
		}
		//推送综合
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("type", "1");
		map2.put("mid", merchantInfoModel.getMid());
		String post = HttpXmlClient.post(admAppIp+"/AdmApp/merchacc/merchant_receiveUpBD.action", map2);
		boolean flag = (Boolean) ((Map<String, Object>) JSONObject.parse(post)).get("success");
		if(!flag){
			json.setSuccess(false);
			json.setMsg("通过失败");
			return json;
		}
		MerchantUpdateTerModel model = new MerchantUpdateTerModel();
		model.setUnno(user.getUnNo());
		model.setMid(bean.getMid());
		model.setType("3");
		model.setMaintainDate(new Date());
		model.setMaintainType("A");
		model.setApproveType("Y");
		merchantUpdateTerDao.saveObject(model);
		
		json.setSuccess(true);
		json.setMsg("关闭商户成功");
		return json;
	}
	private void uploadFile(File upload, String fName, String imageDay) throws Exception {
		try {
			String realPath = queryUpLoadPath() + File.separator + imageDay;
			File dir = new File(realPath);
			dir.mkdirs();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	/**
	 * 查询上传路径
	 */
	private String queryUpLoadPath() {
		String title="HrtFrameWork";
		return merchantUpdateTerDao.executeSql("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'").get(0).get("UPLOAD_PATH");
	}
}
