package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantThreeUploadDao;
import com.hrt.biz.bill.entity.model.MerchantThreeUploadModel;
import com.hrt.biz.bill.entity.pagebean.MerchantThreeUploadBean;
import com.hrt.biz.bill.service.IMerchantThreeUploadService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public class MerchantThreeUploadServiceImpl implements IMerchantThreeUploadService{
	
	private IMerchantThreeUploadDao MerchantThreeUploadDao;

	public IMerchantThreeUploadDao getMerchantThreeUploadDao() {
		return MerchantThreeUploadDao;
	}

	public void setMerchantThreeUploadDao(IMerchantThreeUploadDao MerchantThreeUploadDao) {
		this.MerchantThreeUploadDao = MerchantThreeUploadDao;
	}

	@Override
	public DataGridBean queryMerchantThreeUpload(MerchantThreeUploadBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> map = new HashMap<String,Object>();
		String sql = "";
		if("110000".equals(user.getUnNo())) {
			sql = "select a.*,b.rname from bill_merchantthreeupload a,bill_merchantinfo b where a.mid=b.mid ";
		}else {
			sql = "select a.*,b.rname from bill_merchantthreeupload a,bill_merchantinfo b where a.mid=b.mid and b.unno='"+user.getUnNo()+"' ";
		}
		if(bean.getMid()!=null && !"".equals(bean.getMid())) {
			sql += " and a.mid=:mid";
			map.put("mid", bean.getMid());
		}
		if(bean.getSn()!=null && !"".equals(bean.getSn())) {
			sql += " and a.sn=:sn";
			map.put("sn", bean.getSn());
		}
		if(bean.getApproveStatus()!=null && !"".equals(bean.getApproveStatus())) {
			sql += " and a.approveStatus=:approveStatus";
			map.put("approveStatus", bean.getApproveStatus());
		}
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by bmthid desc";
		List<Map<String,Object>> list = MerchantThreeUploadDao.queryObjectsBySqlList2(sql, map, bean.getPage(), bean.getRows());
		BigDecimal counts = MerchantThreeUploadDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}
	
	@Override
	public DataGridBean queryMerchantThreeUploadFor(MerchantThreeUploadBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		Map<String, Object> map = new HashMap<String,Object>();
		String sql = "select a.*,b.rname from bill_merchantthreeupload a,bill_merchantinfo b where a.mid=b.mid and a.approvestatus='W' ";
		if(bean.getMid()!=null && !"".equals(bean.getMid())) {
			sql += " and a.mid=:mid";
			map.put("mid", bean.getMid());
		}
		if(bean.getSn()!=null && !"".equals(bean.getSn())) {
			sql += " and a.sn=:sn";
			map.put("sn", bean.getSn());
		}
		if(bean.getApproveStatus()!=null && !"".equals(bean.getApproveStatus())) {
			sql += " and a.approveStatus=:approveStatus";
			map.put("approveStatus", bean.getApproveStatus());
		}
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by bmthid desc";
		List<Map<String,Object>> list = MerchantThreeUploadDao.queryObjectsBySqlList2(sql, map, bean.getPage(), bean.getRows());
		BigDecimal counts = MerchantThreeUploadDao.querysqlCounts(sqlCount, map);
		dgb.setRows(list);
		dgb.setTotal(counts.longValue());
		return dgb;
	}

	@Override
	public List<Object> queryDetail(MerchantThreeUploadBean bean) {
		List<Object> list = new ArrayList<Object>();
		String sql = "select * from SYS_PARAM t WHERE t.title='YSFUpload'";
		List<Map<String, Object>> list2 = MerchantThreeUploadDao.queryObjectsBySqlList(sql);
		String upload = (String) list2.get(0).get("UPLOAD_PATH");
		MerchantThreeUploadModel model = MerchantThreeUploadDao.queryObjectByHql("from MerchantThreeUploadModel where bmthid=? ", new Object[] {bean.getBmthid()});
		list.add(model);
		if(model.getMerUpload1()!=null && !"".equals(model.getMerUpload1())) {
			list.add(upload + File.separator + model.getMerUpload1().substring(16, 24) + File.separator + model.getMerUpload1());
		}
		if(model.getMerUpload2()!=null && !"".equals(model.getMerUpload2())) {
			list.add(upload + File.separator + model.getMerUpload2().substring(16, 24) + File.separator + model.getMerUpload2());
		}
		if(model.getMerUpload3()!=null && !"".equals(model.getMerUpload3())) {
			list.add(upload + File.separator + model.getMerUpload3().substring(16, 24) + File.separator + model.getMerUpload3());
		}
		if(model.getMerUpload4()!=null && !"".equals(model.getMerUpload4())) {
			list.add(upload + File.separator + model.getMerUpload4().substring(16, 24) + File.separator + model.getMerUpload4());
		}
		if(model.getMerUpload5()!=null && !"".equals(model.getMerUpload5())) {
			list.add(upload + File.separator + model.getMerUpload5().substring(16, 24) + File.separator + model.getMerUpload5());
		}
		if(model.getMerUpload6()!=null && !"".equals(model.getMerUpload6())) {
			list.add(upload + File.separator + model.getMerUpload6().substring(16, 24) + File.separator + model.getMerUpload6());
		}
		if(model.getMerUpload7()!=null && !"".equals(model.getMerUpload7())) {
			list.add(upload + File.separator + model.getMerUpload7().substring(16, 24) + File.separator + model.getMerUpload7());
		}
		if(model.getMerUpload8()!=null && !"".equals(model.getMerUpload8())) {
			list.add(upload + File.separator + model.getMerUpload8().substring(16, 24) + File.separator + model.getMerUpload8());
		}
		return list;
	}

	@Override
	public JsonBean updateMerchantThreeUploadGo(MerchantThreeUploadBean bean, UserBean user) {
		JsonBean json = new JsonBean();
		MerchantThreeUploadModel model = MerchantThreeUploadDao.queryObjectByHql("from MerchantThreeUploadModel where bmthid=? and approveStatus='W'", new Object[] {bean.getBmthid()});
		if(model==null) {
			json.setSuccess(false);
			json.setMsg("未查询到待审核");
		}
		model.setApproveDate(new Date());
		model.setApproveStatus("Y");
		MerchantThreeUploadDao.updateObject(model);
		json.setSuccess(true);
		json.setMsg("通过成功");
		return json;
	}

	@Override
	public JsonBean updateMerchantThreeUploadBack(MerchantThreeUploadBean bean, UserBean user) {
		JsonBean json = new JsonBean();
		MerchantThreeUploadModel model = MerchantThreeUploadDao.queryObjectByHql("from MerchantThreeUploadModel where bmthid=? and approveStatus='W'", new Object[] {bean.getBmthid()});
		if(model==null) {
			json.setSuccess(false);
			json.setMsg("未查询到待审核");
		}
		model.setApproveDate(new Date());
		model.setApproveStatus("K");
		model.setApproveNote(bean.getApproveNote());
		MerchantThreeUploadDao.updateObject(model);
		json.setSuccess(true);
		json.setMsg("退回成功");
		return json;
	}
	
}
