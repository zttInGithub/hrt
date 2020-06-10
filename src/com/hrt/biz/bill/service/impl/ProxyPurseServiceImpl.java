package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.hrt.biz.bill.dao.IProxyPurseDao;
import com.hrt.biz.bill.entity.model.PgUnnoAdjtxnModel;
import com.hrt.biz.bill.entity.model.PgUnnoPurseModel;
import com.hrt.biz.bill.entity.pagebean.ProxyPurseBean;
import com.hrt.biz.bill.service.IProxyPurseService;
import com.hrt.frame.entity.pagebean.DataGridBean;

public class ProxyPurseServiceImpl implements IProxyPurseService {
	
	private Log log = LogFactory.getLog(ProxyPurseServiceImpl.class);
	
	private IProxyPurseDao proxyPurseDao;
	
	public IProxyPurseDao getProxyPurseDao() {
		return proxyPurseDao;
	}

	public void setProxyPurseDao(IProxyPurseDao proxyPurseDao) {
		this.proxyPurseDao = proxyPurseDao;
	}
	//对象转换
	public Class<Object> toObj(Class a){
		Class<Object> c=a;
		return c;
	}
	
	@Override
	public DataGridBean queryUnnoAdj(ProxyPurseBean proxyPurseBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> param = new HashMap<String,Object>();
		String sql = "select pua.puaid,pua.unno,ba.UPPER_UNIT,ba.UN_NAME,pua.settleday,pua.feeamt,"+
					" pua.feenote,pua.remarks,pua.status,pua.cdate,pua.cby, "+
				    " (select s.unno from sys_unit s where s.un_lvl = 2 AND rownum = 1 "+
					" start with s.unno = pua.unno"+
				    " connect by s.unno = prior s.upper_unit) firstUnno,"+
					" (select s.unno from sys_unit s where s.un_lvl = 1 AND rownum = 1 "+
					" start with s.unno = pua.unno"+
					" connect by s.unno = prior s.upper_unit) yunying,"+
				    " (select s.un_lvl from sys_unit s where s.unno = pua.unno) unnolvl,"+
				    " nvl(pua.walletType,0) walletType "+
				    " from pg_unno_adjtxn pua, SYS_UNIT ba"+
					" where ba.unno = pua.unno ";
		if(null!=proxyPurseBean.getUNNO() && !"".equals(proxyPurseBean.getUNNO())) {
			param.put("UNNO", proxyPurseBean.getUNNO().trim());
			sql += " and ba.unno = :UNNO";
		}
		if(null!=proxyPurseBean.getPROVINCE() && !"".equals(proxyPurseBean.getPROVINCE())){
			param.put("PROVINCE", proxyPurseBean.getPROVINCE().trim());
			sql += " and ba.UN_NAME=:PROVINCE ";
		}
		if(null!=proxyPurseBean.getREMARKS() && !"".equals(proxyPurseBean.getREMARKS())){
			param.put("REMARKS", proxyPurseBean.getREMARKS().trim());
			sql += " and pua.remarks=:REMARKS ";
		}
		if(null!=proxyPurseBean.getWalletType() && !"".equals(proxyPurseBean.getWalletType())){
			param.put("walletType", proxyPurseBean.getWalletType());
			sql += " and nvl(pua.walletType,0) = :walletType ";
		}
		if(null!=proxyPurseBean.getSETTLEDAY() && !"".equals(proxyPurseBean.getSETTLEDAY())
		   ||null!=proxyPurseBean.getCDATE() && !"".equals(proxyPurseBean.getCDATE())){
			param.put("DAY1", proxyPurseBean.getSETTLEDAY().trim().replaceAll("-", ""));
			param.put("DAY2", proxyPurseBean.getCDATE().trim().replaceAll("-", ""));
			sql += " and pua.settleday between :DAY1 and :DAY2 ";
		}else{
			sql += " and pua.settleday=to_char(sysdate,'yyyyMMdd') ";
		}
		String sqlCount = " select count(1) from ("+sql+")"; 
		List<Map<String, Object>> list=proxyPurseDao.queryObjectsBySqlList2(sql, param,
				proxyPurseBean.getPage(), proxyPurseBean.getRows());
			
		BigDecimal counts = proxyPurseDao.querysqlCounts(sqlCount, param);
		dgb.setTotal(counts.intValue());
		dgb.setRows(list);
		list=null;
		return dgb;
	}

	@Override
	public DataGridBean queryUnnoPurseBalanceGrid(ProxyPurseBean proxyPurseBean){
		StringBuilder sb=new StringBuilder("select t.pupid,t.unno,nvl(t.walletType,0) walletType,cur.un_name,cur.un_lvl,cur.upper_unit,t.curamt,t.balance,t.balancetotal,t.frozenamt," +
				" (select s.unno from sys_unit s where s.un_lvl=1 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yunying," +
				" (select s.unno from sys_unit s where s.un_lvl=2 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yidai" +
				" from pg_unno_purse t,sys_unit cur " +
				" where t.unno=cur.unno ");
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(proxyPurseBean.getUNNO())){
			sb.append(" and t.unno=:unno ");
			map.put("unno",proxyPurseBean.getUNNO());
		}
		if(proxyPurseBean.getWalletType() != null){
			sb.append(" and nvl(t.walletType,0) = :walletType ");
			map.put("walletType",proxyPurseBean.getWalletType());
		}
		String sqlCount="select count(*) from ("+sb.toString()+")";
		Integer count = proxyPurseDao.querysqlCounts2(sqlCount,map);
		List<Map<String, Object>> list = proxyPurseDao.queryObjectsBySqlList2(sb.toString(),map,proxyPurseBean.getPage(),proxyPurseBean.getRows());
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(count.intValue());
		dgb.setRows(list);
		return dgb;
	}

	@Override
	public List<Map<String, Object>> exportUnnoPurseBalanceGrid(ProxyPurseBean proxyPurseBean){
		StringBuilder sb=new StringBuilder("select t.pupid,nvl(t.walletType,0) walletType,t.unno,cur.un_name,cur.un_lvl,cur.upper_unit,t.curamt,t.balance,t.balancetotal,t.frozenamt," +
				" (select s.unno from sys_unit s where s.un_lvl=1 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yunying," +
				" (select s.unno from sys_unit s where s.un_lvl=2 start with s.unno=t.unno connect by s.unno=prior s.upper_unit) yidai" +
				" from pg_unno_purse t,sys_unit cur " +
				" where t.unno=cur.unno ");
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(proxyPurseBean.getUNNO())){
			sb.append(" and t.unno=:unno ");
			map.put("unno",proxyPurseBean.getUNNO());
		}
		if(proxyPurseBean.getWalletType() != null){
			sb.append(" and nvl(t.walletType,0) = :walletType ");
			map.put("walletType",proxyPurseBean.getWalletType());
		}
		List<Map<String, Object>> list = proxyPurseDao.queryObjectsBySqlListMap2(sb.toString(),map);
		return list;
	}

	/**
	 *根据机构号查询机构
	 */
	@Override
	public List<Map<String, Object>> queryUnno(ProxyPurseBean proxyPurseBean) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UNNO", proxyPurseBean.getUNNO());
		String sql = "select b.unno,b.buid from bill_agentunitinfo b where b.unno = :UNNO";
		return proxyPurseDao.executeSql2(sql, map);
	}
	/**
	 *判断机构是否在分润钱包中存在
	 */
	@Override
	public List<Map<String, Object>> queryPurse(String unno,Integer walletType) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("UNNO", unno);
		map.put("walletType", walletType);
		String sql = "select pupid from pg_unno_purse where unno=:UNNO and nvl(walletType,0) = :walletType";
		return proxyPurseDao.executeSql2(sql, map);
	}

	/**
	 *添加分润调整数据
	 */
	@Override
	public boolean addUnnAdj(ProxyPurseBean proxyPurseBean, String loginName, String pupid) {
		boolean flag = false;
		PgUnnoAdjtxnModel adj = new PgUnnoAdjtxnModel();
		adj.setUnno(proxyPurseBean.getUNNO().trim());
		adj.setSettleday(proxyPurseBean.getSETTLEDAY().replaceAll("-", ""));
		adj.setFeenote(proxyPurseBean.getFEENOTE());
		adj.setFeeamt(proxyPurseBean.getFEEAMT());
		adj.setStatus(1);
		adj.setMinfo1("0");
		adj.setCdate(new Date());
		adj.setCby(loginName);
		adj.setWalletType(proxyPurseBean.getWalletType());
		int count = (Integer)proxyPurseDao.saveObject(adj);
		if(count>0){
			//添加成功，调整代理钱包可提现金额(obj(PgUnnoPurseModel.class), );
			PgUnnoPurseModel purse = (PgUnnoPurseModel) proxyPurseDao.
					getObjectByID(toObj(PgUnnoPurseModel.class), Integer.parseInt(pupid));
			purse.setCuramt(purse.getCuramt()+proxyPurseBean.getFEEAMT());
			purse.setBalance(purse.getBalance()+proxyPurseBean.getFEEAMT());
			proxyPurseDao.updateObject(purse);
			flag=true;
		}
		return flag;
	}

	/**
	 *分润调整-判断该批次是否已经上传
	 */
	@Override
	public List<Map<String, String>> queryPsmj(String fileName) {
		String sql="select count(1)nums from pg_unno_adjtxn j where j.remarks='"+fileName+"' ";
		List<Map<String,String>> list=proxyPurseDao.executeSql(sql);
		return list;
	}

	/**
	 *分润调整-批量上传保存
	 * @throws Exception 
	 */
	@Override
	public List<Map<String, Object>> saveUnnoAdj(String xlsfile, String loginName, String filename) throws Exception {
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String str = filename;
		String f = str.substring(0, str.indexOf("."));
		File xlsPathFilename=new File(xlsfile);
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(xlsPathFilename));
		HSSFSheet sheet=workbook.getSheetAt(0);
		for(int i=1;i<sheet.getLastRowNum()+1 ;i++){
			HSSFRow row=sheet.getRow(i);
			HSSFCell cell;
			cell = row.getCell((short)0);
			if(cell == null){
				break;
			}else{
				row.getCell(0).setCellType(cell.CELL_TYPE_STRING);
				row.getCell(1).setCellType(cell.CELL_TYPE_STRING);
				row.getCell(2).setCellType(cell.CELL_TYPE_STRING);
				row.getCell(3).setCellType(cell.CELL_TYPE_STRING);
				String UNNO = row.getCell((short)0).getStringCellValue().trim();
				String FEEAMT = row.getCell((short)1).getStringCellValue().trim();
				String FEENOTE = row.getCell((short)2).getStringCellValue().trim();
				String walletType = row.getCell((short)3).getStringCellValue().trim();
				ProxyPurseBean proxyPurseBean = new ProxyPurseBean();
				proxyPurseBean.setUNNO(UNNO);
				List<Map<String,Object>> unnoList = queryUnno(proxyPurseBean);
				if(unnoList.size()>0){
					List<Map<String,Object>> purseList= queryPurse(UNNO,walletType.equals("是")?1:0);
					if(purseList.size()>0){
						//添加调整表数据
						PgUnnoAdjtxnModel adj = new PgUnnoAdjtxnModel();
						adj.setUnno(UNNO);
						adj.setSettleday(sf.format(new Date()));
						adj.setFeeamt(Double.parseDouble(FEEAMT));
						adj.setFeenote(FEENOTE);
						adj.setRemarks(f);
						adj.setStatus(1);
						adj.setMinfo1("0");
						adj.setCdate(new Date());
						adj.setCby(loginName);
						adj.setWalletType(walletType.equals("返现")?1:0);
						int count=(Integer) proxyPurseDao.saveObject(adj);
						if(count>0){
							Object obj=purseList.get(0).get("PUPID");
							//添加成功，调整代理钱包可提现金额
							PgUnnoPurseModel purse = (PgUnnoPurseModel) proxyPurseDao.getObjectByID(toObj(PgUnnoPurseModel.class), Integer.parseInt(obj.toString()));
							purse.setCuramt(purse.getCuramt()+Double.parseDouble(FEEAMT));
							purse.setBalance(purse.getBalance()+Double.parseDouble(FEEAMT));
							proxyPurseDao.updateObject(purse);
						}
					}else{
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("UNNO", UNNO);
						map.put("FEEAMT", FEEAMT);
						map.put("FEENOTE", FEENOTE);
						map.put("WALLETTYPE", walletType);
						String result = proxyPurseBean.getWalletType() == 1 ?"返现":"分润";
						map.put("INFO", "该机构暂无" + result + "钱包无法调整余额，请核对！");
						list.add(map);
					}
				}else{
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("UNNO", UNNO);
					map.put("FEEAMT", FEEAMT);
					map.put("FEENOTE", FEENOTE);
					map.put("WALLETTYPE", walletType);
					map.put("INFO", "机构编号不存在，请核查！");
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 *分润调整-导出
	 */
	@Override
	public List<Map<String, Object>> reportUnnoAdj(ProxyPurseBean proxyPurseBean) {
		Map<String,Object> param=new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select pua.puaid,pua.unno,ba.UPPER_UNIT,ba.UN_NAME,pua.settleday,pua.feeamt,"+
				" pua.feenote,pua.remarks,pua.status,pua.cdate,pua.cby, "+
				" (select s.unno from sys_unit s where s.un_lvl = 2 AND rownum = 1 "+
				" start with s.unno = pua.unno"+
				" connect by s.unno = prior s.upper_unit) firstUnno,"+
				" (select s.unno from sys_unit s where s.un_lvl = 1 AND rownum = 1 "+
				" start with s.unno = pua.unno"+
				" connect by s.unno = prior s.upper_unit) yunying,"+
				" (select s.un_lvl from sys_unit s where s.unno = pua.unno) unnolvl,nvl(walletType,0) walletType"+
				" from pg_unno_adjtxn pua, SYS_UNIT ba"+
				" where ba.unno = pua.unno ";
		if(null!=proxyPurseBean.getUNNO() && !"".equals(proxyPurseBean.getUNNO())) {
			param.put("UNNO", proxyPurseBean.getUNNO().trim());
			sql += " and ba.unno = :UNNO";
		}
		if(null!=proxyPurseBean.getPROVINCE() && !"".equals(proxyPurseBean.getPROVINCE())){
			param.put("PROVINCE", proxyPurseBean.getPROVINCE().trim());
			sql += " and ba.UN_NAME=:PROVINCE ";
		}
		if(null!=proxyPurseBean.getREMARKS() && !"".equals(proxyPurseBean.getREMARKS())){
			param.put("REMARKS", proxyPurseBean.getREMARKS().trim());
			sql += " and pua.remarks=:REMARKS ";
		}
		if(null!=proxyPurseBean.getWalletType() && !"".equals(proxyPurseBean.getWalletType())){
			param.put("walletType", proxyPurseBean.getWalletType());
			sql += " and nvl(pua.walletType,0) = :walletType ";
		}
		if(null!=proxyPurseBean.getSETTLEDAY() && !"".equals(proxyPurseBean.getSETTLEDAY())
		   ||null!=proxyPurseBean.getCDATE() && !"".equals(proxyPurseBean.getCDATE())){
			param.put("DAY1", proxyPurseBean.getSETTLEDAY().trim().replaceAll("-", ""));
			param.put("DAY2", proxyPurseBean.getCDATE().trim().replaceAll("-", ""));
			sql += " and pua.settleday between :DAY1 and :DAY2 ";
		}else{
			sql += " and pua.settleday=to_char(sysdate,'yyyyMMdd') ";
		}
		list=proxyPurseDao.executeSql2(sql, param);
		return list;
	}

	@Override
	public DataGridBean queryGenerationQuotaStatistics(ProxyPurseBean proxyPurseBean) {
		DataGridBean dgb = new DataGridBean();
		String sql = queryGenerationQuotaStatisticsSql(proxyPurseBean);
		String sqlCount = " select count(1) from ("+sql+")"; 
		List<Map<String, Object>> list=proxyPurseDao.queryObjectsBySqlList2(sql, null,
				proxyPurseBean.getPage(), proxyPurseBean.getRows());
			
		BigDecimal counts = proxyPurseDao.querysqlCounts(sqlCount, null);
		dgb.setTotal(counts.intValue());
		dgb.setRows(list);
		list=null;
		return dgb;
	}

	@Override
	public List<Map<String, Object>> reportGenerationQuotaStatistics(ProxyPurseBean proxyPurseBean) {
		String sql = queryGenerationQuotaStatisticsSql(proxyPurseBean);
		List<Map<String,Object>> list = proxyPurseDao.queryObjectsBySqlObject(sql);
		return list;
	}
	
	public String queryGenerationQuotaStatisticsSql(ProxyPurseBean proxyPurseBean) {
		String sql = " select unno1 FIRSTUNNO,un_name PROVINCE,curamt,balance,(nvl(balance, 0) - nvl(curamt, 0)) FEEAMT"+
				" ,centerUnno,"+
				" (select un_name from sys_unit where unno = centerUnno) centerUnnoName"+
				" from (select sum(curamt) curamt,sum(balance) balance,unno1,"+
				" (select un_name from sys_unit where unno = unno1) un_name,"+
				" (select upper_unit from sys_unit where unno = unno1) centerUnno"+
				" from (select (select UNNO from sys_unit where un_lvl = 2 start with unno = a.unno"+
				" connect by NOCYCLE unno = prior upper_unit) unno1,a.curamt,a.balance"+
				" from pg_unno_mutipurse a, sys_unit s"+
				" where a.unno = s.unno and a.status = 1)"+
				" group by unno1) t1 where 1 = 1";
		if(null!=proxyPurseBean.getUNNO() && !"".equals(proxyPurseBean.getUNNO())) {
			sql += " and t1.unno1 = '"+proxyPurseBean.getUNNO().trim()+"'";
		}
		if(null!=proxyPurseBean.getPROVINCE() && !"".equals(proxyPurseBean.getPROVINCE())){
			sql += " and t1.un_name like '"+proxyPurseBean.getPROVINCE().trim()+"%'";
		}		
		return sql;
	}

	@Override
	public DataGridBean queryPurseTurnoverRebatety(ProxyPurseBean proxyPurseBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> param = new HashMap<String,Object>();
		String sql = getPurseTurnoverRebatetySql(proxyPurseBean,param);
		String sqlCount = " select count(1) from ("+sql+")"; 
		List<Map<String, Object>> list=proxyPurseDao.queryObjectsBySqlList2(sql, param,
				proxyPurseBean.getPage(), proxyPurseBean.getRows());
			
		BigDecimal counts = proxyPurseDao.querysqlCounts(sqlCount, param);
		dgb.setTotal(counts.intValue());
		dgb.setRows(list);
		list=null;
		return dgb;
	}
	
	@Override
	public List<Map<String, Object>> reportPurseTurnoverRebatety(ProxyPurseBean proxyPurseBean) {
		Map<String,Object> param=new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = getPurseTurnoverRebatetySql(proxyPurseBean,param);
		list=proxyPurseDao.executeSql2(sql, param);
		return list;
	}
	
	/**分日分活动钱包流水sql
	 * @param proxyPurseBean
	 * @param param
	 * @return
	 */
	private String getPurseTurnoverRebatetySql(ProxyPurseBean proxyPurseBean,Map<String,Object> param) {
		String sql = " SELECT " + 
				" p.unno," + 
				" (SELECT u.un_name FROM  sys_unit u where u.unno = p.unno) unname," +
				" (SELECT u.un_lvl FROM  sys_unit u where u.unno = p.unno) unlevel," +
				" (SELECT u.upper_unit FROM  sys_unit u where u.unno = p.unno) upperunno," +
				" (SELECT u.unno FROM  sys_unit u where u.un_lvl = 2" +
				" start with u.unno = p.unno" + 
				" connect by prior u.upper_unit = u.unno) yidaiunno," + 
				" (SELECT u.unno FROM  sys_unit u where u.un_lvl = 1" +
				" start with u.unno = p.unno" + 
				" connect by prior u.upper_unit = u.unno)yunyingunno," + 
				" p.minfo1," + 
				" case when nvl(p.walletType,0) != 1 then p.fenrunmda else 0 end fenrunmda," +
				" case when nvl(p.walletType,0) = 1 then p.fenrunmda else 0 end fanxianmda," +
				" case when nvl(p.walletType,0) = 1 then p.adjtxnamt else 0 end koukuanmda," +
				" p.txnday," +
				" nvl(p.walletType,0) walletType" + 
				" FROM  pg_unno_purselog p where 1=1";
		if(null!=proxyPurseBean.getUNNO() && !"".equals(proxyPurseBean.getUNNO())) {
			param.put("UNNO", proxyPurseBean.getUNNO().trim());
			sql += " and p.unno = :UNNO";
		}
		if(null!=proxyPurseBean.getPROVINCE() && !"".equals(proxyPurseBean.getPROVINCE())){
			param.put("PROVINCE", proxyPurseBean.getPROVINCE().trim());
			sql += " and p.minfo1=:PROVINCE ";
		}
		if(null!=proxyPurseBean.getWalletType() && !"".equals(proxyPurseBean.getWalletType())){
			param.put("walletType", proxyPurseBean.getWalletType());
			sql += " and nvl(p.walletType,0) = :walletType ";
		}
		if(null!=proxyPurseBean.getSETTLEDAY() && !"".equals(proxyPurseBean.getSETTLEDAY())
		   ||null!=proxyPurseBean.getCDATE() && !"".equals(proxyPurseBean.getCDATE())){
			param.put("DAY1", proxyPurseBean.getSETTLEDAY().trim().replaceAll("-", ""));
			param.put("DAY2", proxyPurseBean.getCDATE().trim().replaceAll("-", ""));
			sql += " and p.txnday between :DAY1 and :DAY2 ";
		}else{
			sql += " and p.txnday=to_char(sysdate,'yyyyMMdd') ";
		}
		return sql;
	}

	@Override
	public DataGridBean queryPurseTurnoverDay(ProxyPurseBean proxyPurseBean) {
		DataGridBean dgb = new DataGridBean();
		Map<String,Object> param = new HashMap<String,Object>();
		String sql = getPurseTurnoverDaySql(proxyPurseBean,param);
		String sqlCount = " select count(1) from ("+sql+")"; 
		List<Map<String, Object>> list=proxyPurseDao.queryObjectsBySqlList2(sql, param,
				proxyPurseBean.getPage(), proxyPurseBean.getRows());
			
		BigDecimal counts = proxyPurseDao.querysqlCounts(sqlCount, param);
		dgb.setTotal(counts.intValue());
		dgb.setRows(list);
		list=null;
		return dgb;
	}

	@Override
	public List<Map<String, Object>> reportPurseTurnoverDay(ProxyPurseBean proxyPurseBean) {
		Map<String,Object> param=new HashMap<String, Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = getPurseTurnoverDaySql(proxyPurseBean,param);
		list=proxyPurseDao.executeSql2(sql, param);
		return list;
	}
	
	/**分日钱包流水sql
	 * @param proxyPurseBean
	 * @param param
	 * @return
	 */
	private String getPurseTurnoverDaySql(ProxyPurseBean proxyPurseBean,Map<String,Object> param) {
		String sql = " SELECT " + 
				" p.unno," + 
				" (SELECT u.un_name FROM  sys_unit u where u.unno = p.unno) unname," +
				" (SELECT u.un_lvl FROM  sys_unit u where u.unno = p.unno) unlevel," +
				" (SELECT u.upper_unit FROM  sys_unit u where u.unno = p.unno) upperunno," +
				" (SELECT u.unno FROM  sys_unit u where u.un_lvl = 2" +
				" start with u.unno = p.unno" + 
				" connect by prior u.upper_unit = u.unno) yidaiunno," + 
				" (SELECT u.unno FROM  sys_unit u where u.un_lvl = 1" +
				" start with u.unno = p.unno" + 
				" connect by prior u.upper_unit = u.unno)yunyingunno," + 
				" p.beginbalance," + 
				" case when nvl(p.minfo1,0) != '1' then p.fenrunmda else 0 end fenrunmda," +
				" case when nvl(p.minfo1,0) = '1' then p.fenrunmda else 0 end fanxianmda," +
				" case when nvl(p.minfo1,0) = '1' then p.minfo2 else '0' end koukuanmda," +
				" p.adjtxnamt," + 
				" p.cashamt," + 
				" p.taxamt," + 
				" p.cashfee," + 
				" p.endbalance," + 
				" p.txnday," +
				" nvl(p.minfo1,'0') walletType " + 
				" FROM  pg_unno_pursehistry p where 1=1";
		if(null!=proxyPurseBean.getUNNO() && !"".equals(proxyPurseBean.getUNNO())) {
			param.put("UNNO", proxyPurseBean.getUNNO().trim());
			sql += " and p.unno = :UNNO";
		}
		if(null!=proxyPurseBean.getPROVINCE() && !"".equals(proxyPurseBean.getPROVINCE())){
			param.put("PROVINCE", proxyPurseBean.getPROVINCE().trim());
			sql += " and p.minfo1=:PROVINCE ";
		}
		if(null!=proxyPurseBean.getWalletType() && !"".equals(proxyPurseBean.getWalletType())){
			param.put("walletType", proxyPurseBean.getWalletType());
			sql += " and nvl(p.minfo1,0) = :walletType ";
		}
		if(null!=proxyPurseBean.getSETTLEDAY() && !"".equals(proxyPurseBean.getSETTLEDAY())
		   ||null!=proxyPurseBean.getCDATE() && !"".equals(proxyPurseBean.getCDATE())){
			param.put("DAY1", proxyPurseBean.getSETTLEDAY().trim().replaceAll("-", ""));
			param.put("DAY2", proxyPurseBean.getCDATE().trim().replaceAll("-", ""));
			sql += " and p.txnday between :DAY1 and :DAY2 ";
		}else{
			sql += " and p.txnday=to_char(sysdate,'yyyyMMdd') ";
		}
		return sql;
	}
}
