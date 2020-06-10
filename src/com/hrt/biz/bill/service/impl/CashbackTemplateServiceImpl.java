package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hrt.biz.bill.dao.ICashbackTemplateDao;
import com.hrt.biz.bill.entity.pagebean.CashbackTemplateBean;
import com.hrt.biz.bill.service.ICashbackTemplateService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.DateTools;

public class CashbackTemplateServiceImpl implements ICashbackTemplateService {
	private Log log = LogFactory.getLog(CashbackTemplateServiceImpl.class);
	
	private ICashbackTemplateDao cashbackTemplateDao;
	
	public ICashbackTemplateDao getCashbackTemplateDao() {
		return cashbackTemplateDao;
	}

	public void setCashbackTemplateDao(ICashbackTemplateDao cashbackTemplateDao) {
		this.cashbackTemplateDao = cashbackTemplateDao;
	}

	@Override
	public DataGridBean queryCashbackTaxPoint(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		DataGridBean bean = new DataGridBean();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = getCashbackTaxPoint(cashbackTemplateBean,userSession,map);
		String countSql = " select count(1) from ("+sql+")";
		List<Map<String,String>> list = cashbackTemplateDao.queryObjectsBySqlList(sql, map, 
				cashbackTemplateBean.getPage(), cashbackTemplateBean.getRows());
		Integer counts2 = cashbackTemplateDao.querysqlCounts2(countSql, map);
		bean.setRows(list);
		bean.setTotal(counts2);
		return bean;
	}
	
	@Override
	public List<Map<String, Object>> reportCashbackTaxPoint(CashbackTemplateBean cashbackTemplateBean,
			UserBean userBean) {
		Map<String,Object> param=new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = getCashbackTaxPoint(cashbackTemplateBean,userBean,param);
		
		list=cashbackTemplateDao.executeSql2(sql, param);
		return list;
	}
	
	/**
	 * 查询返现税点sql
	 * @param cashbackTemplateBean
	 * @param userSession
	 * @param map
	 * @return
	 */
	private String getCashbackTaxPoint(CashbackTemplateBean cashbackTemplateBean, UserBean userSession,Map<String, Object> map) {
		String sql = " SELECT a.id, a.unno,a.rebatetype,a.taxpoint,a.upperunno,"
				+ " decode(a.cashbacktype,1,'刷卡',2,'押金',3,'花呗分期','其他') cashbacktype"
				+ " FROM (SELECT c.*,(SELECT u.upper_unit FROM ppusr.sys_unit u where u.unno = c.unno) upperunno"
 				+ " FROM ppusr.bill_cashbacktaxpoint c)a where 1=1";
		
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getUnno())) {
			sql += " and a.unno = :unno";
			map.put("unno", cashbackTemplateBean.getUnno());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getRebatetype())) {
			sql += " and a.rebatetype = :rebatetype";
			map.put("rebatetype", cashbackTemplateBean.getRebatetype());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getCashbacktype())) {
			sql += " and a.cashbacktype = :cashbacktype";
			map.put("cashbacktype", cashbackTemplateBean.getCashbacktype());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getUpperUnno())) {
			sql += " and a.upperunno = :upperunno";
			map.put("upperunno", cashbackTemplateBean.getUpperUnno());
		}
		return sql;
	}

	@Override
	public Map queryCashbackTaxPointForId(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		Map params = new HashMap();
		params.put("id", cashbackTemplateBean.getNid());
		String sql = "select p.id,p.unno,p.rebatetype,p.taxpoint,"
				+ " decode(p.cashbacktype,1,'刷卡',2,'押金',3,'花呗分期','其他')cashbacktype"
				+ " from bill_cashbacktaxpoint p where 1=1 " + " and p.id =:id";
		List<Map<String, String>> list = cashbackTemplateDao.queryObjectsBySqlListMap(sql, params);
		if (list.size() == 1) {
			return list.get(0);
		}
		return new HashMap();
	}

	@Override
	public String updateCashbackTaxPoint(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		String sql = " update bill_cashbacktaxpoint p set p.taxpoint = '"+cashbackTemplateBean.getTaxpoint()+"'"+
				" ,p.lby = '"+userSession.getLoginName()+"',p.maintaindate = sysdate,p.maintaintype = 'M'"+
				" where p.id = '"+cashbackTemplateBean.getNid()+"'";
		Integer integer = cashbackTemplateDao.executeSqlUpdate(sql, null);
		if(integer<1) {
			return "修改失败";
		}
		return "修改成功";
	}

	@Override
	public String importCashbackTaxPointUpdate(String xlsfile, String fileName, UserBean user) {
		String flag1= null;
        Map uniqeMap = new HashMap();
        int sumCount = 0;
        File filename = new File(xlsfile);
        HSSFWorkbook workbook = null;
        Map<String,String> uniqueUnno = new HashMap<String,String>();
        List<CashbackTemplateBean> list = new ArrayList<CashbackTemplateBean>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(filename));
        } catch (IOException e) {
            log.info("导入批量变更文件异常:"+e.getMessage());
        }
        HSSFSheet sheet = workbook.getSheetAt(0);
        sumCount = sheet.getPhysicalNumberOfRows()-1;
        String numberInfo="";
        for(int i = 1; i <= sumCount; i++){
            numberInfo="第"+(i+1)+"行,";
            CashbackTemplateBean bean = new CashbackTemplateBean();
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell((short)0);
            if(cell == null || cell.toString().trim().equals("")){
                break;
            }else{
                // 代理机构号
                row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
                String unno = row.getCell((short)0).getStringCellValue().trim();
                bean.setUnno(unno);

                // 活动编号
                row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
                String rebatetype=row.getCell((short)1).getStringCellValue().trim();
                bean.setRebatetype(rebatetype);
              
                //返现类型
                row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
                String cashbacktype = row.getCell((short)2).getStringCellValue().trim();
                if(StringUtils.isNotEmpty(cashbacktype)) {
                	if("刷卡".equals(cashbacktype)) {
                		bean.setCashbacktype("1");
                	}else if("押金".equals(cashbacktype)) {
                		bean.setCashbacktype("2");
                	}else if("花呗分期".equals(cashbacktype)){
                		bean.setCashbacktype("3");
                	}
                }
                
                //税点
                row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
                String taxpoint = row.getCell((short)3).getStringCellValue().trim();
                bean.setTaxpoint(new BigDecimal(taxpoint));
                list.add(bean);

                // 1、不允许有空字符；
                if(cashbacktype.isEmpty()||rebatetype.isEmpty()||taxpoint.isEmpty()){
                	flag1 = "空字符串，禁止提交";
                    return numberInfo + flag1;
                }
                
                //查询机构是否存在
                String ishaveSql = "SELECT t.* FROM sys_unit t where t.unno = '"+unno+"'";
                List<Map<String,String>> a1 = cashbackTemplateDao.executeSql(ishaveSql);
                if(a1.size()<1) {
                	flag1 = unno + "不存在，请核实";
                	return flag1;
                }
                
                //查询机构是否是中心与一代
                String iszhongxinSql = " SELECT * FROM ppusr.sys_unit u where u.un_lvl in (1,2) and u.unno = '"+unno+"'";
                List<Map<String,String>> a2 = cashbackTemplateDao.executeSql(iszhongxinSql);
                if(a2.size()<1) {
                	flag1 = unno + "不为中心机构/或一代，禁止修改";
                	return flag1;
                }
                
                String key=unno+":"+rebatetype+":"+cashbacktype;
                // 2、同一文件，同一代理机构号不能出现2次及其以上，否则提示“XX代理重复”整批文件无法导入
                if(uniqeMap.containsKey(key)){
                    flag1 = unno + "代理重复提交";
                    return flag1;
                }
                uniqeMap.put(key,"1");
            }
        }
        for (CashbackTemplateBean model : list) {
        	String judgeSql = " SELECT a.* FROM ppusr.bill_cashbacktaxpoint a"
        			+ " where a.unno = '"+model.getUnno()+"'"
        			+ " and a.rebatetype = '"+model.getRebatetype()+"' and a.cashbacktype = '"+model.getCashbacktype()+"'";
        	List<Map<String,Object>> judgeList = cashbackTemplateDao.executeSql2(judgeSql, null);
        	if(judgeList==null||judgeList.size()<1) {
        		//添加
        		String insertSql = " insert into bill_cashbacktaxpoint a"
        				+ " (a.id,a.unno,a.rebatetype,a.cashbacktype,a.taxpoint,a.cdate,a.cby,a.maintaintype)"
        				+ " values"
        				+ " (S_bill_CashbackTaxPoint.Nextval,'"+model.getUnno()+"','"+model.getRebatetype()+"'"
        				+ " ,'"+model.getCashbacktype()+"','"+model.getTaxpoint()+"',sysdate,'"+user.getLoginName()+"','A')";
        		cashbackTemplateDao.executeSqlUpdate(insertSql, null);
        	}else {
        		//修改
        		Object object = judgeList.get(0).get("ID");
        		if(StringUtils.isEmpty(String.valueOf(object))) {
        			 
        		}else {
	        		String updateSql = " update bill_cashbacktaxpoint p set p.taxpoint = '"+model.getTaxpoint()+"'"+
	        				" ,p.lby = '"+user.getLoginName()+"',p.maintaindate = sysdate,p.maintaintype = 'M'"+
	        				" where p.id = '"+String.valueOf(object)+"'";
	        		cashbackTemplateDao.executeSqlUpdate(updateSql, null);
        	    }
        	}
        }
        return null;
	}

	@Override
	public DataGridBean queryCashbackTemplateLog(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		DataGridBean bean = new DataGridBean();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = getCashbackTemplateLog(cashbackTemplateBean,userSession,map);
		String countSql = " select count(1) from ("+sql+")";
		List<Map<String,String>> list = cashbackTemplateDao.queryObjectsBySqlList(sql, map, 
				cashbackTemplateBean.getPage(), cashbackTemplateBean.getRows());
		Integer counts2 = cashbackTemplateDao.querysqlCounts2(countSql, map);
		bean.setRows(list);
		bean.setTotal(counts2);
		return bean;
	}
	
	@Override
	public List<Map<String, Object>> reportCashbackTemplateLog(CashbackTemplateBean cashbackTemplateBean,
			UserBean userBean) {
		Map<String,Object> param=new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = getCashbackTemplateLog(cashbackTemplateBean,userBean,param);
		
		list=cashbackTemplateDao.executeSql2(sql, param);
		return list;
	}
	
	//查询模板记录sql
	private String getCashbackTemplateLog(CashbackTemplateBean cashbackTemplateBean, UserBean userSession,Map<String, Object> map) {
		String sql = " SELECT a.unno,"
				+ " (SELECT u.un_name FROM ppusr.sys_unit u where u.unno = a.unno) unnoname,"
				+ " a.rebatetype,decode(a.cashbacktype,1,'刷卡',2,'押金',3,'花呗分期','其他') cashbacktype,a.cashbackratio,a.startdate,a.enddate"
				+ " FROM ppusr.bill_cashbacktemplate_log a where 1=1 ";
		if(userSession.getUnitLvl()==0) {
		}else if(userSession.getUnitLvl()==1) {
			return null;
		}else {
			sql += " and a.unno in (SELECT u.unno FROM ppusr.sys_unit u where u.upper_unit =:upperunno)";
			map.put("upperunno", userSession.getUnNo());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getUnno())) {
			sql += " and a.unno =:unno";
			map.put("unno", cashbackTemplateBean.getUnno());
		}
		return sql;
	}

	@Override
	public DataGridBean getChildrenUnit(UserBean user, String nameCode) {
		String sql = "SELECT t.UNNO,t.UN_NAME FROM SYS_UNIT  t WHERE 1=1  ";
		DataGridBean dgd = new DataGridBean();
		if(user.getUnitLvl()==0){
			sql+=" and t.un_lvl in(2,3,5,6,7,8) ";
		}else if (user.getUnitLvl()==1){
			return dgd;
		}else {
			sql+=" and (upper_unit='"+user.getUnNo()+"' or unno='"+user.getUnNo()+"')";
		}
		if(nameCode !=null){
			sql += " AND (t.UNNO LIKE '" + nameCode.replaceAll("'", "") + "%' OR t.UN_NAME LIKE '" + nameCode.replaceAll("'", "")+ "%') ";
		}
		sql+=" order by t.UN_NAME ASC";
		List<Map<String,String>> proList = cashbackTemplateDao.executeSql(sql);	
		dgd.setTotal(proList.size());
		dgd.setRows(proList);
		
		return dgd;
	}

	@Override
	public DataGridBean queryCashbackTemplateUnno(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		DataGridBean bean = new DataGridBean();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = getCashbackTemplateUnno(cashbackTemplateBean,userSession,map);
		String countSql = " select count(1) from ("+sql+")";
		List<Map<String,String>> list = cashbackTemplateDao.queryObjectsBySqlList(sql, map, 
				cashbackTemplateBean.getPage(), cashbackTemplateBean.getRows());
		Integer counts2 = cashbackTemplateDao.querysqlCounts2(countSql, map);
		bean.setRows(list);
		bean.setTotal(counts2);
		return bean;
	}
	
	//查询模板设置机构sql
	private String getCashbackTemplateUnno(CashbackTemplateBean cashbackTemplateBean, UserBean userSession,Map<String, Object> map) {
		String sql = " SELECT a.unno,"
				+ " (SELECT u.un_name FROM ppusr.sys_unit u where u.unno = a.unno) unnoname"
				+ " FROM ppusr.bill_cashbacktemplate a where a.maintaintype !='D'";
		if(userSession.getUnitLvl()==0) {
		}else if(userSession.getUnitLvl()==1) {
			return null;
		}else {
			sql += " and a.unno in (SELECT u.unno FROM ppusr.sys_unit u where u.upper_unit =:upperunno)";
			map.put("upperunno", userSession.getUnNo());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getUnno())) {
			sql += " and a.unno=:unno";
			map.put("unno", cashbackTemplateBean.getUnno());
		}
		sql += " group by a.unno";
		return sql;
	}

	@Override
	public List<Map<String, Object>> queryCashbackTemplateForUnno(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = getCashbackTemplateForUnnoSql(cashbackTemplateBean,userSession,map);
		list = cashbackTemplateDao.queryObjectsBySqlList2(sql, map, 
				cashbackTemplateBean.getPage(), cashbackTemplateBean.getRows());
		return list;
	}
	
	private String getCashbackTemplateForUnnoSql(CashbackTemplateBean cashbackTemplateBean, UserBean userSession,Map<String, Object> map) {
		Date format2 = DateTools.getStartMonth(new Date());
    	Date format3 = DateTools.getEndtMonth(new Date());
    	Date format4= DateTools.getUpMonthFirst(new Date());
    	Date format5= DateTools.getUpMonthLast(new Date());
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	String startMonth = format.format(format2);
    	String endtMonth = format.format(format3);
    	String upMonthFirst = format.format(format4);
    	String upMonthLast = format.format(format5);
		String sql = "";
    	if(DateTools.isFirstDay()) {
    		sql += " SELECT * FROM (SELECT c.nid,c.unno,"
    			+ "decode(c.cashbacktype,1,'刷卡',2,'押金',3,'花呗分期','其他') cashbacktype,"
    			+ "c.cashbacktype,case when w.cashbackratio is not null then"
        		+ " w.cashbackratio else c.cashbackratio end as cashbackratio,c.maintaintype,case"
        		+ " when ((SELECT count(1) FROM bill_cashbacktemplate_w w1 where w1.unno = c.unno"
        		+ " and w1.rebatetype = c.rebatetype and w1.cashbacktype = c.cashbacktype"
        		+ " and (w1.cdate between to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') and"
        		+ " to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') + 1)) > 0) then"
        		+ " (SELECT w1.cashbackratio FROM bill_cashbacktemplate_w w1"
        		+ " where w1.unno = c.unno and w1.rebatetype = c.rebatetype and w1.cashbacktype = c.cashbacktype"
        		+ " and (w1.cdate between"
        		+ " to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') and"
        		+ " to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') + 1))"
        		+ " else"
        		+ " (case when w.cashbackratio is not null then w.cashbackratio else c.cashbackratio end) end cashbackrationext"
        		+ " FROM ppusr.bill_cashbacktemplate c"
        		+ " left join ppusr.bill_cashbacktemplate_w w"
        		+ " on c.unno = w.unno and c.rebatetype = w.rebatetype and c.cashbacktype = w.cashbacktype"
        		+ " and (w.cdate between to_date('"+upMonthFirst+"', 'yyyy-mm-dd hh24:mi:ss') and to_date('"+upMonthLast+"', 'yyyy-mm-dd hh24:mi:ss') + 1)) a"
        		+ " where maintaintype != 'D'"
        		+ " and unno = :unno";
    	}else {
    		sql += " SELECT * FROM (SELECT c.nid,c.unno,c.rebatetype,decode(c.cashbacktype,1,'刷卡',2,'押金',3,'花呗分期','其他') cashbacktype,c.cashbackratio,"
    			+ " c.maintaintype,case when w.cashbackratio is null then c.cashbackratio else w.cashbackratio end as cashbackrationext"
    			+ " FROM ppusr.bill_cashbacktemplate c left join ppusr.bill_cashbacktemplate_w w"
    			+ " on c.unno = w.unno and c.rebatetype = w.rebatetype and c.cashbacktype = w.cashbacktype"
    			+ " and (w.cdate between to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') and"
    			+ " to_date('"+endtMonth+"', 'yyyy-mm-dd hh24:mi:ss') + 1)) a"
    			+ " where maintaintype != 'D'"
    			+ " and unno = :unno";
    	}
//		String sql = " SELECT unno,rebatetype,cashbacktype,sum(flag1) cashbackratio,sum(flag2) cashbackrationext" 
//				+ " FROM (SELECT a.unno,a.rebatetype,decode(a.cashbacktype,1,'刷卡',2,'押金',3,'花呗分期','其他') cashbacktype,a.cashbackratio flag1,0 flag2"
//				+ " FROM ppusr.bill_cashbacktemplate a"
//				+ " where a.maintaintype != 'D'"
//				+ " and a.maintaintype != 'P'"
//				+" and a.unno = :unno"
//				+ " union all"
//				+ " SELECT b.unno,b.rebatetype,decode(b.cashbacktype,1,'刷卡',2,'押金',3,'花呗分期','其他') cashbacktype,0 flag1,b.cashbackratio flag2"
//				+ " FROM ppusr.bill_cashbacktemplate_w b"
//				+ " where b.maintaintype != 'D'"
//				+ " and b.maintaintype != 'P'"
//			    + " and b.unno = :unno"
//			    + " and b.cdate >= to_date('"+format2+"', 'yyyy-mm-dd hh24:mi:ss')"
//			    + " and b.cdate <= to_date('"+format3+"', 'yyyy-mm-dd hh24:mi:ss')+1"
//		        + " ) group by unno,rebatetype,cashbacktype";
		map.put("unno", cashbackTemplateBean.getUnno());
		return sql;
	}

	@Override
	public String validateCashbackTemplateNotEmpty(CashbackTemplateBean bean,UserBean userSession,int x) {
		String unnoRebatetype = bean.getUnnoRebatetype();
		String unno = bean.getUnno();
		if(StringUtils.isEmpty(unno)){
			return "机构编号为空";
		}
        JSONArray json = JSONArray.parseArray(unnoRebatetype);
        for(int i=0;i<json.size();i++){
        	String cashbackratio = JSONObject.parseObject(json.getString(i)).getString("cashbackratio");
        	if(StringUtils.isEmpty(cashbackratio)) {
        		return "返现比例本月为空";
        	}
        	String rebatetype = JSONObject.parseObject(json.getString(i)).getString("rebatetype");
        	if(StringUtils.isEmpty(rebatetype)) {
        		return "活动类型为空";
        	}
        	String cashbacktype = JSONObject.parseObject(json.getString(i)).getString("cashbacktype");
        	if(StringUtils.isEmpty(cashbacktype)) {
        		return "返现类型为空";
        	}
        	String cashbackrationext = JSONObject.parseObject(json.getString(i)).getString("cashbackrationext");
        	if(StringUtils.isEmpty(cashbackrationext)) {
        		return "返现比例下月为空";
        	}else {
        		BigDecimal bigDecimal = new BigDecimal(cashbackrationext);
        		if(bigDecimal.intValue()<0||bigDecimal.intValue()>1) {
        			return "比例不合规，请输入0至1间数值";
        		}
        	}
        	String cashbacktype_1 = "";
        	if("刷卡".equals(cashbacktype)) {
        		cashbacktype_1="1";
        	}else if("押金".equals(cashbacktype)){
        		cashbacktype_1="2";
        	}else {
        		cashbacktype_1="3";
        	}
        	//判断添加的机构是否存在且为登录用户的下级并为2代以后的机构
        	String JudgeUnnoSql = " SELECT count(1) FROM ppusr.sys_unit u where 1=1";
        	if(userSession.getUnitLvl()==0) {
        		JudgeUnnoSql += " and u.unno = '"+unno+"' and u.un_lvl > 2";
        	}else {
        		JudgeUnnoSql += " and u.upper_unit = '"+userSession.getUnNo()+"' and u.un_lvl > 2";
        	}
        	
        	Integer integer = cashbackTemplateDao.querysqlCounts2(JudgeUnnoSql, null);
        	if(integer<1) {
        		return unno+"不存在或者不为你的直属下级机构";
        	}
        	
        	if(x==1) {
        		//判断添加的机构是否已存在，存在提示 
        		String sql = " SELECT count(1) FROM ppusr.bill_cashbacktemplate a"
        				+ " where a.unno = '"+unno+"'"
        				+ " and a.cashbacktype = '"+cashbacktype_1+"' and a.rebatetype = '"+rebatetype+"'";
        		Integer integer2 = cashbackTemplateDao.querysqlCounts2(sql, null);
        		if(integer2>0) {
        			return unno+"已存在活动"+rebatetype+cashbacktype+"返现,如要修改请编辑";
        		}
        		
        		//开通二代以后添加时判断上级是否开通
        		String upperSql = " SELECT count(1) FROM ppusr.bill_cashbacktemplate a"
        				+ " where a.unno = (SELECT u.upper_unit FROM ppusr.sys_unit u where u.unno = '"+unno+"')"
        				+ " and a.cashbacktype = '"+cashbacktype_1+"' and a.rebatetype = '"+rebatetype+"'";
        		if(userSession.getUnitLvl()>2) {
        			Integer integer3 = cashbackTemplateDao.querysqlCounts2(upperSql, null);
        			if(integer3<1) {
            			return unno+"上级机构无活动"+rebatetype+cashbacktype+"返现";
            		}
        		}
        	}
        	
        }
        return null;
	}

	@Override
	public void updateCashbackTemplateNext(CashbackTemplateBean bean, UserBean userSession) {
		Date format2 = DateTools.getStartMonth(new Date());
    	Date format3 = DateTools.getEndtMonth(new Date());
    	Date format4= DateTools.getUpMonthFirst(new Date());
    	Date format5= DateTools.getUpMonthLast(new Date());
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	String startMonth = format.format(format2);
    	String endtMonth = format.format(format3);
    	String upMonthFirst = format.format(format4);
    	String upMonthLast = format.format(format5);
		
    	String unnoRebatetype = bean.getUnnoRebatetype();
        JSONArray json = JSONArray.parseArray(unnoRebatetype);
        String unno = bean.getUnno();
        String sql1 = " update ppusr.bill_cashbacktemplate_w w"
        		+ " set w.cashbackratio = :cashbackrationext,w.maintaindate=sysdate ,w.maintaintype= 'M',w.lby=:lby "
        		+ " where w.unno = :unno and w.rebatetype = :rebatetype and w.cashbacktype = :cashbacktype"
        		+ " and (w.maintaindate between to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') and "
        		+ " to_date('"+endtMonth+"', 'yyyy-mm-dd hh24:mi:ss') + 1)";
        
        String sql2 = " SELECT count(1) FROM ppusr.bill_cashbacktemplate_w w"
    			+ " where w.unno = :unno"
    			+ " and w.cashbacktype = :cashbacktype and w.rebatetype = :rebatetype"
    			+ " and (w.maintaindate between to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') and"
    			+ " to_date('"+endtMonth+"', 'yyyy-mm-dd hh24:mi:ss') + 1)";
        String sql3 = " insert into bill_cashbacktemplate_w a"
        		+ " (a.wid,a.unno,a.rebatetype,a.cashbacktype,a.cashbackratio,a.cdate,a.cby,a.maintaintype,a.maintaindate)"
        		+ " values"
        		+ " (S_bill_cashbacktemplate_w.Nextval,:unno,:rebatetype,:cashbacktype,:cashbackrationext,sysdate,:cby,'A',sysdate)";
        String sql4 = " SELECT count(1) FROM ppusr.bill_cashbacktemplate_w w"
    			+ " where w.unno = :unno"
    			+ " and w.cashbacktype = :cashbacktype and w.rebatetype = :rebatetype"
    			+ " and (w.maintaindate between to_date('"+upMonthFirst+"', 'yyyy-mm-dd hh24:mi:ss') and"
    			+ " to_date('"+upMonthLast+"', 'yyyy-mm-dd hh24:mi:ss') + 1)";
        for(int i=0;i<json.size();i++){
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	String cashbackratio = JSONObject.parseObject(json.getString(i)).getString("cashbackratio");
        	String rebatetype = JSONObject.parseObject(json.getString(i)).getString("rebatetype");
        	String cashbacktype = JSONObject.parseObject(json.getString(i)).getString("cashbacktype");
        	String cashbackrationext = JSONObject.parseObject(json.getString(i)).getString("cashbackrationext");
        	if("刷卡".equals(cashbacktype)) {
        		cashbacktype="1";
        	}else if("押金".equals(cashbacktype)){
        		cashbacktype="2";
        	}else {
        		cashbacktype="3";
        	}
        	map.put("unno", unno);
        	map.put("rebatetype", rebatetype);
        	map.put("cashbacktype", cashbacktype);
        	
        	Integer integer1 = cashbackTemplateDao.querysqlCounts2(sql2, map);
        	if(integer1>0) {
        		map.put("cashbackrationext", cashbackrationext);
        		map.put("lby", userSession.getLoginName());
        		cashbackTemplateDao.executeSqlUpdate(sql1, map);
        	}else {
        		map.put("cashbackrationext", cashbackrationext);
        		if(DateTools.isFirstDay()) {
        			Integer integer2 = cashbackTemplateDao.querysqlCounts2(sql4, map);
        			if(integer2>0) {
        				map.put("lby", userSession.getLoginName());
        				cashbackTemplateDao.executeSqlUpdate(sql1, map);
        			}else {
        				cashbackTemplateDao.executeSqlUpdate(sql3, map);
        			}
                }else {
                	map.put("cby", userSession.getLoginName());
                	cashbackTemplateDao.executeSqlUpdate(sql3, map);
                }
        	}
        }
		
	}

	@Override
	public void addCashbackTemplateNext(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    cale.add(Calendar.MONTH, 1);
	    cale.set(Calendar.DAY_OF_MONTH, 1);
	    Date time = cale.getTime();
	    String format2 = format.format(time);
		String unnoRebatetype = cashbackTemplateBean.getUnnoRebatetype();
        JSONArray json = JSONArray.parseArray(unnoRebatetype);
        //实时模板表
        HashMap<String, Object> map = new HashMap<String, Object>();
        //次月模板表
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        //当月模板日志
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        //次月模板日志
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        String nowSql = " insert into bill_cashbacktemplate a"
        		+ " (a.nid,a.unno,a.rebatetype,a.cashbacktype,a.cashbackratio,a.cdate,a.cby,a.maintaintype,a.maintaindate)"
        		+ " values"
        		+ " (S_bill_cashbacktemplate.Nextval,:unno,:rebatetype,:cashbacktype,:cashbackratio,sysdate,:cby,'A',sysdate)";
        String nextSql = " insert into bill_cashbacktemplate_w a"
        		+ " (a.wid,a.unno,a.rebatetype,a.cashbacktype,a.cashbackratio,a.cdate,a.cby,a.maintaintype,a.maintaindate)"
        		+ " values"
        		+ " (S_bill_cashbacktemplate_w.Nextval,:unno,:rebatetype,:cashbacktype,:cashbackrationext,sysdate,:cby,'A',sysdate)";
        String logSql1 = " insert into ppusr.bill_cashbacktemplate_log l"
        		+ " (l.wid,l.unno,l.rebatetype,l.cashbacktype,l.cashbackratio,l.maintainuser,l.maintaintype,l.maintaindate,l.startdate,l.enddate)"
        		+ " values"
        		+ " (S_bill_cashbacktemplate_log.Nextval,:unno,:rebatetype,:cashbacktype,:cashbackratio,:maintainuser,'A',sysdate,sysdate,to_date('"+format2+"','yyyy-mm-dd hh24:mi:ss'))";
       
        String logSql2 = " insert into ppusr.bill_cashbacktemplate_log l"
        		+ " (l.wid,l.unno,l.rebatetype,l.cashbacktype,l.cashbackratio,l.maintainuser,l.maintaintype,l.maintaindate,l.startdate)"
        		+ " values"
        		+ " (S_bill_cashbacktemplate_log.Nextval,:unno,:rebatetype,:cashbacktype,:cashbackratio,:maintainuser,'A',sysdate,sysdate)";
       
        String nextLogSql = " insert into ppusr.bill_cashbacktemplate_log l"
        		+ " (l.wid,l.unno,l.rebatetype,l.cashbacktype,l.cashbackratio,l.maintainuser,l.maintaintype,l.maintaindate,l.startdate)"
        		+ " values"
        		+ " (S_bill_cashbacktemplate_log.Nextval,:unno,:rebatetype,:cashbacktype,:cashbackrationext,:maintainuser,'A',sysdate,to_date('"+format2+"','yyyy-mm-dd hh24:mi:ss'))";
        
        String unno = cashbackTemplateBean.getUnno();
        for(int i=0;i<json.size();i++){
        	String cashbackratio = JSONObject.parseObject(json.getString(i)).getString("cashbackratio");
        	String rebatetype = JSONObject.parseObject(json.getString(i)).getString("rebatetype");
        	String cashbacktype = JSONObject.parseObject(json.getString(i)).getString("cashbacktype");
        	if("刷卡".equals(cashbacktype)) {
        		cashbacktype="1";
        	}else if("押金".equals(cashbacktype)){
        		cashbacktype="2";
        	}else {
        		cashbacktype="3";
        	}
        	
        	String cashbackrationext = JSONObject.parseObject(json.getString(i)).getString("cashbackrationext");
        	map.put("unno", unno);
        	map.put("rebatetype", rebatetype);
        	map.put("cashbacktype", cashbacktype);
        	map.put("cashbackratio", cashbackratio);
        	map.put("cby", userSession.getLoginName());
        	
        	map1.put("unno", unno);
        	map1.put("rebatetype", rebatetype);
        	map1.put("cashbacktype", cashbacktype);
        	map1.put("cashbackrationext", cashbackrationext);
        	map1.put("cby", userSession.getLoginName());
        	
        	map2.put("unno", unno);
        	map2.put("rebatetype", rebatetype);
        	map2.put("cashbacktype", cashbacktype);
        	map2.put("cashbackratio", cashbackratio);
        	map2.put("maintainuser", userSession.getLoginName());
        	
        	map3.put("unno", unno);
        	map3.put("rebatetype", rebatetype);
        	map3.put("cashbacktype", cashbacktype);
        	map3.put("cashbackrationext", cashbackrationext);
        	map3.put("maintainuser", userSession.getLoginName());
        	
        	
        	cashbackTemplateDao.executeSqlUpdate(nowSql, map);
        	if(new BigDecimal(cashbackrationext).compareTo(new BigDecimal(cashbackratio))!=0) {
        		//只有当两个返现比例不一致时，保存修改表,且日志表直接将本月数据赋值结束时间
        		cashbackTemplateDao.executeSqlUpdate(nextSql, map1);
        		cashbackTemplateDao.executeSqlUpdate(logSql1, map2);
        	}else {
        		//本月与下月录入相同时，修改表不保存数据。日志表存储的是没有结束时间的本月数据
        		cashbackTemplateDao.executeSqlUpdate(logSql2,map2);
        	}
        //	cashbackTemplateDao.executeSqlUpdate(nextLogSql, map3);
        	
        }
		
	}

	@Override
	public void deleteCashbackTemplate(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		Date format2 = DateTools.getStartMonth(new Date());
    	Date format3 = DateTools.getEndtMonth(new Date());
    	
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	String startMonth = format.format(format2);
    	String endtMonth = format.format(format3);
		HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        String sql = " update ppusr.bill_cashbacktemplate w"
        		+ " set w.maintaindate=sysdate ,w.maintaintype= 'D',w.lby=:lby "
        		+ " where w.unno = :unno";
        String nextsql = " update ppusr.bill_cashbacktemplate_w w"
        		+ " set w.maintaindate=sysdate ,w.maintaintype= 'D',w.lby=:lby "
        		+ " where w.unno = :unno"
        		+ " and (w.maintaindate between to_date('"+startMonth+"', 'yyyy-mm-dd hh24:mi:ss') and "
        		+ " to_date('"+endtMonth+"', 'yyyy-mm-dd hh24:mi:ss') + 1)";
        String logSql1 = " update ppusr.bill_cashbacktemplate_log l"
        		+ " set l.maintainuser= :maintainuser,l.enddate=sysdate"
        		+ " where l.unno= :unno "
        		+ " and l.enddate is null";
        String logSql2 = " insert into ppusr.bill_cashbacktemplate_log l"
        		+ " (l.wid,l.unno,l.rebatetype,l.cashbacktype,l.cashbackratio,l.maintainuser,l.maintaintype,l.maintaindate,l.startdate)"
        		+ " values"
        		+ " (S_bill_cashbacktemplate_log.Nextval,:unno,:rebatetype,:cashbacktype,:cashbackratio,:maintainuser,'D',sysdate,sysdate)";
        map.put("unno", cashbackTemplateBean.getUnno());
        map.put("lby", userSession.getLoginName());
        map1.put("maintainuser", userSession.getLoginName());
        map1.put("unno", cashbackTemplateBean.getUnno());
        cashbackTemplateDao.executeSqlUpdate(sql, map);
       // cashbackTemplateDao.executeSqlUpdate(nextsql, map);
        cashbackTemplateDao.executeSqlUpdate(logSql1, map1);
    }

	@Override
	public DataGridBean listRebateRate(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select * from bill_PurConfigure where status = 1 and valuestring='rate' and type=3";
		sql += " order by valueinteger asc";
		List<Map<String,Object>> list = cashbackTemplateDao.queryObjectsBySqlListMap2(sql, null);
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		if(list.size()==0) {
		}else{
			for (Map<String, Object> stringMap : list) {
				Map<String,Object> map = new HashMap<String, Object>();
				String ruleInfo = (String)stringMap.get("RULE_INFO");
				JSONObject jsonRule = JSON.parseObject(ruleInfo);
				if(jsonRule!=null && jsonRule.getString("cashBackType")!=null){
					map.put("NAME", stringMap.get("NAME"));
					map.put("VALUEINTEGER", stringMap.get("VALUEINTEGER"));
					list1.add(map);
				}
			}
		}
		dgb.setRows(list1);
		return dgb;
	}

	@Override
	public DataGridBean queryRebatetypeCashbackType(String type) {
		DataGridBean dgb = new DataGridBean();
		String sql  = "select * from bill_PurConfigure where status = 1 and valuestring='rate' and type=3"
				+ " and valueinteger = "+Integer.parseInt(type)+"";
		sql += " order by valueinteger asc";
		List<Map<String,Object>> list = cashbackTemplateDao.queryObjectsBySqlListMap2(sql, null);
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		if(list.size()==0) {
		}else{
			for (Map<String, Object> stringMap : list) {
				String ruleInfo = (String)stringMap.get("RULE_INFO");
				JSONObject jsonRule = JSON.parseObject(ruleInfo);
				if(jsonRule!=null && jsonRule.getString("cashBackType")!=null){
					String CASHBACKTYPE_temp = jsonRule.getString("cashBackType");
					if(CASHBACKTYPE_temp==null) {
					}else {
						if(CASHBACKTYPE_temp.contains("1")) {
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("NAME1", "刷卡");
							map.put("CASHBACKTYPE", "1");
							list1.add(map);
						}
						if(CASHBACKTYPE_temp.contains("2")) {
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("NAME1", "押金");
							map.put("CASHBACKTYPE","2");
							list1.add(map);
						}
						if(CASHBACKTYPE_temp.contains("3")){
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("NAME1", "花呗分期");
							map.put("CASHBACKTYPE", "3");
							list1.add(map);
						}
					}
				}
			}
		}
		dgb.setRows(list1);
		return dgb;
	}

	@Override
	public DataGridBean queryCashbackDetail(CashbackTemplateBean cashbackTemplateBean, UserBean userSession) {
		DataGridBean bean = new DataGridBean();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String sql = getCashbackDetailSql(cashbackTemplateBean,userSession,map);
		String countSql = " select count(1) from ("+sql+")";
		List<Map<String,String>> list = cashbackTemplateDao.queryObjectsBySqlList(sql, map, 
				cashbackTemplateBean.getPage(), cashbackTemplateBean.getRows());
		Integer counts2 = cashbackTemplateDao.querysqlCounts2(countSql, map);
		bean.setRows(list);
		bean.setTotal(counts2);
		return bean;
	}
	
	@Override
	public List<Map<String, Object>> reportCashbackDetail(CashbackTemplateBean cashbackTemplateBean,
			UserBean userBean) {
		Map<String,Object> param=new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = getCashbackDetailSql(cashbackTemplateBean,userBean,param);
		
		list=cashbackTemplateDao.executeSql2(sql, param);
		return list;
	}
	
	private String getCashbackDetailSql(CashbackTemplateBean cashbackTemplateBean, UserBean userSession,Map<String, Object> map) {
		String sql = " SELECT a.upp_unno,"
				+ " a.unno,a.un_name,a.mid,a.sn,a.samt,a.cashback_amt,a.cdate,a.rebatetype,"
				+ " decode(a.cashback_type,1,'刷卡',2,'押金',3,'花呗分期','其他') cashbacktype"
				+ " FROM ppusr.pg_cashback_detail a where 1=1";
		
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getRebatetype())) {
			sql += " and a.rebatetype = :rebatetype";
			map.put("rebatetype", cashbackTemplateBean.getRebatetype());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getCashbacktype())) {
			sql += " and a.cashback_type =:cashbackType";
			map.put("cashbackType", cashbackTemplateBean.getCashbacktype());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getMid())) {
			sql += " and a.mid =:mid";
			map.put("mid", cashbackTemplateBean.getMid());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getSn())) {
			sql += " and a.sn =:sn";
			map.put("sn", cashbackTemplateBean.getSn());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getUnno())) {
			sql += " and a.unno =:unno";
			map.put("unno", cashbackTemplateBean.getUnno());
		}
		if(StringUtils.isNotEmpty(cashbackTemplateBean.getUnnoname())) {
			sql += " and a.un_name =:unnoname";
			map.put("unnoname", cashbackTemplateBean.getUnnoname());
		}
		if(null!=cashbackTemplateBean.getCashDay() && !"".equals(cashbackTemplateBean.getCashDay())
	        		&&null!=cashbackTemplateBean.getCashDay1() && !"".equals(cashbackTemplateBean.getCashDay1())) {
	        sql += " and a.cdate >= :cdate";
	        sql += " and a.cdate <= :cdate1+1";
	        map.put("cdate",cashbackTemplateBean.getCashDay());
	        map.put("cdate1",cashbackTemplateBean.getCashDay1());
	    }else {
	        sql += " and a.cdate >= trunc(sysdate,'mm')";
	    }
		if(userSession.getUnitLvl()==0) {
		}else {
			sql += " and a.unno in (SELECT u.unno FROM ppusr.sys_unit u"
					+ " start with u.unno = '"+userSession.getUnNo()+"' connect by prior u.unno = u.upper_unit)";
		}
		return sql;
	}
}
