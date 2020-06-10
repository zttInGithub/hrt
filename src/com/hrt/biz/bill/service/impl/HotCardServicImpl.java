package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;

import com.hrt.biz.bill.dao.IHotCardDao;
import com.hrt.biz.bill.entity.model.HotCardModel;
import com.hrt.biz.bill.entity.pagebean.HotCardBean;
import com.hrt.biz.bill.service.IHotCardService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class HotCardServicImpl implements IHotCardService {

	private IHotCardDao hotCardDao;

	public IHotCardDao getHotCardDao() {
		return hotCardDao;
	}

	public void setHotCardDao(IHotCardDao hotCardDao) {
		this.hotCardDao = hotCardDao;
	}

	@Override
	public DataGridBean queryHotCardMerchant(HotCardBean bean) {
		
		String sql ="select * from  pg_hotmerch  t where 1=1 ";
		String sqlCount="select count(*) from  pg_hotmerch t where 1=1  ";
		Map<String ,Object> map = new HashMap<String, Object>();
		if (bean.getTname() !=null && !"".equals(bean.getTname())) {
			sql += " and t.tname like :TNAME " ;
			sqlCount += " and t.tname like :TNAME " ;
			map.put("TNAME", '%'+bean.getTname()+'%');
		}
		
		if (bean.getIdentificationNumber()!=null && !"".equals(bean.getIdentificationNumber())) {
			sql += " and t.identificationNumber =:NUMBER";
			sqlCount += " and t.identificationNumber =:NUMBER";
			map.put("NUMBER", bean.getIdentificationNumber());
		}
		
		if(bean.getSn()!=null && !"".equals(bean.getSn())){
			sql +=" and t.sn=:SN";
			sqlCount +=" and t.sn=:SN";
			map.put("SN", bean.getSn());
		}
		
		if(bean.getLicense()!=null && !"".equals(bean.getLicense())){
			sql +=" and t.license like '%"+bean.getLicense()+"%'";
			sqlCount +=" and t.license like '%"+bean.getLicense()+"%'";
		}
		
		if(bean.getBankAccNo()!=null && !"".equals(bean.getBankAccNo())){
			sql +=" and t.bankAccNo like '%"+bean.getBankAccNo()+"%'";
			sqlCount +=" and t.bankAccNo like '%"+bean.getBankAccNo()+"%'";
		}
		
		List<HotCardModel> list=hotCardDao.queryObjectsBySqlLists(sql, map, bean.getPage(), bean.getRows(), HotCardModel.class);
		Integer count =hotCardDao.querysqlCounts2(sqlCount, map);
		DataGridBean dgd = formatToDataGrid(list,count);
		return dgd;
	}
	
	
	/**
	 * 方法功能：格式化MachineInfoModel为datagrid数据格式
	 * 参数：machineInfoList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<HotCardModel> hotCardModelList, long total) {
		List<HotCardBean> hotCardBeanList = new ArrayList<HotCardBean>();
		for(HotCardModel hotCardModel : hotCardModelList) {
			HotCardBean hotCardBean = new HotCardBean();
			BeanUtils.copyProperties(hotCardModel, hotCardBean);
			
			hotCardBeanList.add(hotCardBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(hotCardBeanList);
		
		return dgb;
	}

	
	@Override
	public void saveHotCardMerchant(HotCardBean bean,UserBean user) {
		HotCardModel hcm = new HotCardModel();
		BeanUtils.copyProperties(bean, hcm);
		hcm.setCdate(new Date());
		hcm.setCby(user.getUserName());
		hotCardDao.saveObject(hcm);
		
	}

	@Override
	public void updateHotCardMerchantInfo(HotCardBean bean, UserBean userSession) {
		HotCardModel hcm = new HotCardModel();
		BeanUtils.copyProperties(bean, hcm);
		hcm.setCby(userSession.getUserName());
		hcm.setCdate(new Date());
		hotCardDao.updateObject(hcm);
		
	}

	@Override
	public void deleteHotCardMerchantInfo(HotCardBean bean, UserBean userSession) {
		HotCardModel hcm = new HotCardModel();
		hcm.setPhid(bean.getPhid());
		hotCardDao.deleteObject(hcm);
	}
	
	/**
	 * 上传订单
	 */
	@Override
	public List<Map<String,String>> saveimportHotCardMerchant(String xlsfile,UserBean user) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try { 
			File filename=new File(xlsfile);
			Workbook book = Workbook.getWorkbook(filename);
			Sheet sheet = book.getSheet(0);
			for(int i=1;i<sheet.getRows();i++){
				
				Cell cell1=sheet.getCell(0,i);//（列，行）  
                Cell cell2=sheet.getCell(1,i);  
                Cell cell3=sheet.getCell(2,i);
				Cell cell4=sheet.getCell(3,i);
                Cell cell5=sheet.getCell(4,i);  
                Cell cell6=sheet.getCell(5,i);
                
                String tname = cell1.getContents();//商户名称
				String sn = cell2.getContents();//SN号
				String identificationNumber = cell3.getContents();//法人身份证
				String license = cell4.getContents();//营业执照号
				String bankAccNo = cell5.getContents();//入账卡号
				String remarks = cell6.getContents();//备注
				
				if(tname==null||"".equals(tname)){
					Map<String,String> map = new HashMap<String,String>();
					map.put("tname", tname);
					map.put("sn", sn);
					map.put("identificationNumber", identificationNumber);
					map.put("license", license);
					map.put("bankAccNo", bankAccNo);
					map.put("remarks", "商户名称未填写");
					list.add(map);
					continue;
				}
				if(sn==null||"".equals(sn)){
					Map<String,String> map = new HashMap<String,String>();
					map.put("tname", tname);
					map.put("sn", sn);
					map.put("identificationNumber", identificationNumber);
					map.put("license", license);
					map.put("bankAccNo", bankAccNo);
					map.put("remarks", "sn未填写");
					list.add(map);
					continue;
				}
				if(identificationNumber==null||"".equals(identificationNumber)){
					Map<String,String> map = new HashMap<String,String>();
					map.put("tname", tname);
					map.put("sn", sn);
					map.put("identificationNumber", identificationNumber);
					map.put("license", license);
					map.put("bankAccNo", bankAccNo);
					map.put("remarks", "法人身份证未填写");
					list.add(map);
					continue;
				}
				if(license==null||"".equals(license)){
					Map<String,String> map = new HashMap<String,String>();
					map.put("tname", tname);
					map.put("sn", sn);
					map.put("identificationNumber", identificationNumber);
					map.put("license", license);
					map.put("bankAccNo", bankAccNo);
					map.put("remarks", "营业执照号未填写");
					list.add(map);
					continue;
				}
				if(bankAccNo==null||"".equals(bankAccNo)){
					Map<String,String> map = new HashMap<String,String>();
					map.put("tname", tname);
					map.put("sn", sn);
					map.put("identificationNumber", identificationNumber);
					map.put("license", license);
					map.put("bankAccNo", bankAccNo);
					map.put("remarks", "入账卡号未填写");
					list.add(map);
					continue;
				}
				HotCardModel hcm = new HotCardModel();
				hcm.setTname(tname);
				hcm.setSn(sn);
				hcm.setIdentificationNumber(identificationNumber);
				hcm.setBankAccNo(bankAccNo);
				hcm.setLicense(license);
				hcm.setRemarks(remarks);
				hcm.setCdate(new Date());
				hcm.setCby(user.getUserName());
				hotCardDao.saveObject(hcm);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> exportHotCardMerchant(HotCardBean bean) {
		String sql ="select * from  pg_hotmerch  t where 1=1 ";
		Map<String ,Object> map = new HashMap<String, Object>();
		if (bean.getPhids() !=null && !"".equals(bean.getPhids())) {
			sql += " and t.phid in ("+bean.getPhids()+") " ;
		}
		sql += "order by phid desc";
		List<Map<String, Object>> list = hotCardDao.queryObjectsBySqlListMap2(sql, map);
		return list;
	}

	@Override
	public void updateBatchHotCardMerchant(HotCardBean bean, UserBean userSession) {
		String sql ="update pg_hotmerch set cby='"+userSession.getUserName();
		if(bean.getTname()!=null&&!"".equals(bean.getTname())){
			sql+="',tname='"+bean.getTname();
		}
		if(bean.getSn()!=null&&!"".equals(bean.getSn())){
			sql+="',sn='"+bean.getSn();
		}
		if(bean.getIdentificationNumber()!=null&&!"".equals(bean.getIdentificationNumber())){
			sql+="',identificationNumber='"+bean.getIdentificationNumber();
		}
		if(bean.getLicense()!=null&&!"".equals(bean.getLicense())){
			sql+="',license='"+bean.getLicense();
		}
		if(bean.getBankAccNo()!=null&&!"".equals(bean.getBankAccNo())){
			sql+="',bankAccNo='"+bean.getBankAccNo();
		}
		if(bean.getRemarks()!=null&&!"".equals(bean.getRemarks())){
			sql+="',remarks='"+bean.getRemarks();
		}
		sql+="' where phid in ("+bean.getPhids()+")";
		hotCardDao.executeUpdate(sql);
	}
}
