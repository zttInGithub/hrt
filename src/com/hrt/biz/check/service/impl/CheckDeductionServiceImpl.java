package com.hrt.biz.check.service.impl;

import com.hrt.biz.check.dao.CheckDeductionDao;
import com.hrt.biz.check.entity.pagebean.CheckDeductionBean;
import com.hrt.biz.check.service.CheckDeductionService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class CheckDeductionServiceImpl implements CheckDeductionService {

    private CheckDeductionDao checkDeductionDao;

    public CheckDeductionDao getCheckDeductionDao() {
        return checkDeductionDao;
    }

    public void setCheckDeductionDao(CheckDeductionDao checkDeductionDao) {
        this.checkDeductionDao = checkDeductionDao;
    }

    @Override
    public DataGridBean queryCheckDeductionInfo(CheckDeductionBean checkDeductionBean, UserBean user) {
        String sql = "select a.cdid,a.yidai,a.yidainame,a.erdai,a.erdainame,a.unno,a.unnoname,\n" +
                "       a.yunying,a.mid,a.sn,a.sntype,a.useddate,a.keyconfirmdate,a.outdate,\n" +
                "       a.deduction,a.rebatetype,a.maintaindate\n" +
                " from check_deduction a where 1=1 ";

        Map map = new HashMap<String,String>();
        if("110000".equals(user.getUnNo())) {//总公司权限
        }else if(user.getUseLvl()==2||user.getUseLvl()==1){//业务人员
            //业务员名称可能重复，加UNNO限制
            sql += " and a.mid in (select mid from bill_merchantinfo where busid = (select busid from " +
                    " bill_agentsalesinfo where user_id="+user.getUserID()+" and maintaintype!='D') and unno = '"
                    +user.getUnNo()+"') ";
        }else if(user.getUseLvl()==3) {//商户
            sql += " and a.mid=:mid1";
            map.put("mid1", user.getLoginName());
        }else if(user.getUseLvl()==0) {//代理商管理员
            sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
            map.put("unno1", user.getUnNo());
        }

        if(StringUtils.isNotEmpty(checkDeductionBean.getRebateType())){
            sql+=" and a.rebatetype = :rebatetype";
            map.put("rebatetype",checkDeductionBean.getRebateType());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getSn())){
            sql+=" and a.sn = :sn";
            map.put("sn",checkDeductionBean.getSn());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getYunYing())){
            sql+=" and a.yunying = :yunying";
            map.put("yunying",checkDeductionBean.getYunYing());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getMid())){
            sql+=" and a.mid = :mid";
            map.put("mid",checkDeductionBean.getMid());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getUnno())){
            sql+=" and a.unno = :unno";
            map.put("unno",checkDeductionBean.getUnno());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getYiDai())){
            sql+=" and a.yidai = :yidai";
            map.put("yidai",checkDeductionBean.getYiDai());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getErDai())){
        	sql+=" and a.erdai = :erdai";
        	map.put("erdai",checkDeductionBean.getErDai());
        }
        // 当日代表当月
        if(null!=checkDeductionBean.getOutDate() && !"".equals(checkDeductionBean.getOutDate())){
//            sql+=" and trunc(a.outdate,'mm') = trunc(:outdate,'mm')";
            sql+=" and a.outdate >= trunc(:outdate,'mm') and a.outdate < trunc(add_months(:outdate,1),'mm') ";
            map.put("outdate",(Date)checkDeductionBean.getOutDate());
        }
        // 当日代表当月
//        if(null!=checkDeductionBean.getMaintainDate() && !"".equals(checkDeductionBean.getMaintainDate())){
////            sql+=" and trunc(a.maintaindate,'mm') = trunc(:maintainDate,'mm')";
//            sql+=" and a.maintaindate >= trunc(:maintainDate,'mm') and a.maintaindate < trunc(add_months(:maintainDate,1),'mm') ";
//            map.put("maintainDate",(Date)checkDeductionBean.getMaintainDate());
//        }else{
////            sql+=" and trunc(a.maintaindate,'mm') = trunc(:maintainDate,'mm')";
//            sql+=" and a.maintaindate >= trunc(sysdate,'mm') ";
////            map.put("maintainDate",new Date());
//        }
        
        //扣款日期
        if(null!=checkDeductionBean.getMaintainDate() && !"".equals(checkDeductionBean.getMaintainDate())
        		&&null!=checkDeductionBean.getMaintainDate1() && !"".equals(checkDeductionBean.getMaintainDate1())) {
        	sql += " and a.maintaindate >= :maintainDate";
        	sql += " and a.maintaindate <= :maintainDate1";
        	map.put("maintainDate",checkDeductionBean.getMaintainDate());
        	map.put("maintainDate1",checkDeductionBean.getMaintainDate1());
        }else {
        	sql += " and a.maintaindate >= trunc(sysdate,'mm')";
        }
        // 当日代表当月
        if(null!=checkDeductionBean.getKeyConfirmDate() && !"".equals(checkDeductionBean.getKeyConfirmDate())){
//            sql+=" and trunc(a.keyconfirmdate,'mm') = trunc(:keyconfirmdate,'mm')";
            sql+=" and a.keyconfirmdate >= trunc(:keyconfirmdate,'mm') and a.keyconfirmdate < trunc(add_months(:keyconfirmdate,1),'mm') ";
            map.put("keyconfirmdate",(Date)checkDeductionBean.getKeyConfirmDate());
        }
        String sqlCount = "select count(1) from ("+sql+")";
        List<Map<String,Object>> list = checkDeductionDao.queryObjectsBySqlList2(sql, map, checkDeductionBean.getPage(), checkDeductionBean.getRows());
        BigDecimal counts = checkDeductionDao.querysqlCounts(sqlCount, map);
        DataGridBean dgb = new DataGridBean();
        dgb.setRows(list);
        dgb.setTotal(counts.longValue());
        return dgb;
    }

    @Override
    public List<Map<String, Object>> export10170(CheckDeductionBean checkDeductionBean, UserBean userBean) {
        String sql = "select a.cdid,a.yidai,a.yidainame,a.erdai,a.erdainame,a.unno,a.unnoname,\n" +
                "       a.yunying,a.mid,a.sn,a.sntype,a.useddate,a.keyconfirmdate,a.outdate,\n" +
                "       a.deduction,a.rebatetype,a.maintaindate\n" +
                " from check_deduction a where 1=1 ";

        Map map = new HashMap<String,String>();
        if("110000".equals(userBean.getUnNo())) {//总公司权限
        }else if(userBean.getUseLvl()==2||userBean.getUseLvl()==1){//业务人员
            //业务员名称可能重复，加UNNO限制
            sql += " and a.mid in (select mid from bill_merchantinfo where busid = (select busid from " +
                    " bill_agentsalesinfo where user_id="+userBean.getUserID()+" and maintaintype!='D') and unno = '"
                    +userBean.getUnNo()+"') ";
        }else if(userBean.getUseLvl()==3) {//商户
            sql += " and a.mid=:mid1";
            map.put("mid1", userBean.getLoginName());
        }else if(userBean.getUseLvl()==0) {//代理商管理员
            sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
            map.put("unno1", userBean.getUnNo());
        }

        if(StringUtils.isNotEmpty(checkDeductionBean.getRebateType())){
            sql+=" and a.rebatetype = :rebatetype";
            map.put("rebatetype",checkDeductionBean.getRebateType());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getSn())){
            sql+=" and a.sn = :sn";
            map.put("sn",checkDeductionBean.getSn());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getYunYing())){
            sql+=" and a.yunying = :yunying";
            map.put("yunying",checkDeductionBean.getYunYing());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getMid())){
            sql+=" and a.mid = :mid";
            map.put("mid",checkDeductionBean.getMid());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getUnno())){
            sql+=" and a.unno = :unno";
            map.put("unno",checkDeductionBean.getUnno());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getYiDai())){
            sql+=" and a.yidai = :yidai";
            map.put("yidai",checkDeductionBean.getYiDai());
        }
        if(StringUtils.isNotEmpty(checkDeductionBean.getErDai())){
        	sql+=" and a.erdai = :erdai";
        	map.put("erdai",checkDeductionBean.getErDai());
        }
        if(null!=checkDeductionBean.getOutDate() && !"".equals(checkDeductionBean.getOutDate())){
//            sql+=" and trunc(a.outdate,'mm') = trunc(:outdate,'mm')";
            sql+=" and a.outdate >= trunc(:outdate,'mm') and a.outdate < trunc(add_months(:outdate,1),'mm') ";
            map.put("outdate",(Date)checkDeductionBean.getOutDate());
        }
        // 当日代表当月
//        if(null!=checkDeductionBean.getMaintainDate() && !"".equals(checkDeductionBean.getMaintainDate())){
////            sql+=" and trunc(a.maintaindate,'mm') = trunc(:maintainDate,'mm')";
//            sql+=" and a.maintaindate >= trunc(:maintainDate,'mm') and a.maintaindate < trunc(add_months(:maintainDate,1),'mm') ";
//            map.put("maintainDate",(Date)checkDeductionBean.getMaintainDate());
//        }else{
////            sql+=" and trunc(a.maintaindate,'mm') = trunc(:maintainDate,'mm')";
//            sql+=" and a.maintaindate >= trunc(sysdate,'mm') ";
////            map.put("maintainDate",new Date());
//        }
      //扣款日期
        if(null!=checkDeductionBean.getMaintainDate() && !"".equals(checkDeductionBean.getMaintainDate())
        		&&null!=checkDeductionBean.getMaintainDate1() && !"".equals(checkDeductionBean.getMaintainDate1())) {
        	sql += " and a.maintaindate >= :maintainDate";
        	sql += " and a.maintaindate <= :maintainDate1";
        	map.put("maintainDate",checkDeductionBean.getMaintainDate());
        	map.put("maintainDate1",checkDeductionBean.getMaintainDate1());
        }else {
        	sql += " and a.maintaindate >= trunc(sysdate,'mm')";
        }
        // 当日代表当月
        if(null!=checkDeductionBean.getKeyConfirmDate() && !"".equals(checkDeductionBean.getKeyConfirmDate())){
//            sql+=" and trunc(a.keyconfirmdate,'mm') = trunc(:keyconfirmdate,'mm')";
            sql+=" and a.keyconfirmdate >= trunc(:keyconfirmdate,'mm') and a.keyconfirmdate < trunc(add_months(:keyconfirmdate,1),'mm') ";
            map.put("keyconfirmdate",(Date)checkDeductionBean.getKeyConfirmDate());
        }
        List<Map<String,Object>> list = checkDeductionDao.executeSql2(sql, map);
        return list;
    }

	@Override
	public DataGridBean queryCheckDeductionListInfo10170_selectRebateType() {
		String sql = "select valuestring,name from bill_purconfigure a where type = 10 and status = 1";
		List<Map<String,Object>> list = checkDeductionDao.queryObjectsBySqlObject(sql);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("VALUESTRING", "");
        map.put("NAME", "ALL");
		list.add(0, map);;
		DataGridBean dgb = new DataGridBean();
        dgb.setRows(list);
        dgb.setTotal(list.size());
        return dgb;
	}
    
    

}
