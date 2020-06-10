package com.hrt.biz.check.service.impl;

import com.hrt.biz.check.dao.CheckCashbackDayDao;
import com.hrt.biz.check.entity.pagebean.CheckCashbackDayBean;
import com.hrt.biz.check.service.CheckCashbackDayService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckCashbackDayServiceImpl implements CheckCashbackDayService {

    private CheckCashbackDayDao checkCashbackDayDao;

    public CheckCashbackDayDao getCheckCashbackDayDao() {
        return checkCashbackDayDao;
    }

    public void setCheckCashbackDayDao(CheckCashbackDayDao checkCashbackDayDao) {
        this.checkCashbackDayDao = checkCashbackDayDao;
    }

    @Override
    public DataGridBean queryCheckCashbackDayInfo(CheckCashbackDayBean checkCashbackDayBean, UserBean userBean) {
        String sql = "select a.* from check_cashback_day a where 1=1 ";

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

        if(null!=checkCashbackDayBean.getRebateType() && !"".equals(checkCashbackDayBean.getRebateType())){
            sql+=" and a.rebatetype = :rebatetype";
            map.put("rebatetype",Integer.parseInt(checkCashbackDayBean.getRebateType()+""));
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getSn())){
            sql+=" and a.sn = :sn";
            map.put("sn",checkCashbackDayBean.getSn());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getUpperUnit())){
            sql+=" and a.upper_unit = :upper_unit";
            map.put("upper_unit",checkCashbackDayBean.getUpperUnit());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getMid())){
            sql+=" and a.mid = :mid";
            map.put("mid",checkCashbackDayBean.getMid());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getUnno())){
            sql+=" and a.unno = :unno";
            map.put("unno",checkCashbackDayBean.getUnno());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getUpper1Unit())){
            sql+=" and a.upper1_unit = :upper1_unit";
            map.put("upper1_unit",checkCashbackDayBean.getUpper1Unit());
        }
        // 交易日  可自由选择某一日，代表交易月1日至选择日累计期间段
//        if(StringUtils.isNotEmpty(checkCashbackDayBean.getTxnMonth())){
//            String txnMonth = checkCashbackDayBean.getTxnMonth().replaceAll("-","");
//            sql+=" and a.txnmonth between :txnmonth1 and :txnmonth2 ";
//            map.put("txnmonth1",txnMonth.substring(0,6)+"01");
//            map.put("txnmonth2",txnMonth.substring(0,8));
//        }
        if(null!=checkCashbackDayBean.getLmDate()){
//            sql+=" and a.lmdate>=trunc(:lmDate,'mm') and trunc(a.lmdate,'dd')<=trunc(:lmDate,'dd')";
            sql+=" and a.lmdate>=trunc(:lmDate,'mm') and a.lmdate<trunc(:lmDate)+1 ";
            map.put("lmDate",checkCashbackDayBean.getLmDate());
        }else{
//            sql+=" and trunc(a.lmdate,'dd')=trunc(:lmDate,'dd')";
            sql+=" and a.lmdate>=trunc(sysdate,'dd') ";
//            map.put("lmDate",new Date());
        }
        // 当日代表出售月 可自由选择某一日，代表选择日所在月
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getKeyMonth())){
            sql+=" and substr(a.keymonth,0,6) = :keymonth ";
            map.put("keymonth",checkCashbackDayBean.getKeyMonth().replaceAll("-","").substring(0,6));
        }
        String sqlCount = "select count(1) from ("+sql+")";
        List<Map<String,Object>> list = checkCashbackDayDao.queryObjectsBySqlList2(sql, map, checkCashbackDayBean.getPage(), checkCashbackDayBean.getRows());
        BigDecimal counts = checkCashbackDayDao.querysqlCounts(sqlCount, map);
        DataGridBean dgb = new DataGridBean();
        dgb.setRows(list);
        dgb.setTotal(counts.longValue());
        return dgb;
    }

    @Override
    public List<Map<String, Object>> export10171(CheckCashbackDayBean checkCashbackDayBean, UserBean userBean) {
        String sql = "select a.* from check_cashback_day a where 1=1 ";

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

        if(null!=checkCashbackDayBean.getRebateType() && !"".equals(checkCashbackDayBean.getRebateType())){
            sql+=" and a.rebatetype = :rebatetype";
            map.put("rebatetype",Integer.parseInt(checkCashbackDayBean.getRebateType()+""));
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getSn())){
            sql+=" and a.sn = :sn";
            map.put("sn",checkCashbackDayBean.getSn());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getUpperUnit())){
            sql+=" and a.upper_unit = :upper_unit";
            map.put("upper_unit",checkCashbackDayBean.getUpperUnit());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getMid())){
            sql+=" and a.mid = :mid";
            map.put("mid",checkCashbackDayBean.getMid());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getUnno())){
            sql+=" and a.unno = :unno";
            map.put("unno",checkCashbackDayBean.getUnno());
        }
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getUpper1Unit())){
            sql+=" and a.upper1_unit = :upper1_unit";
            map.put("upper1_unit",checkCashbackDayBean.getUpper1Unit());
        }
        // 交易日  可自由选择某一日，代表交易月1日至选择日累计期间段
//        if(StringUtils.isNotEmpty(checkCashbackDayBean.getTxnMonth())){
//            String txnMonth = checkCashbackDayBean.getTxnMonth().replaceAll("-","");
//            sql+=" and a.txnmonth between :txnmonth1 and :txnmonth2 ";
//            map.put("txnmonth1",txnMonth.substring(0,6)+"01");
//            map.put("txnmonth2",txnMonth.substring(0,8));
//        }
        if(null!=checkCashbackDayBean.getLmDate()){
//            sql+=" and trunc(a.lmdate,'dd')=trunc(:lmDate,'dd')";
            sql+=" and a.lmdate>=trunc(:lmDate,'mm') and a.lmdate<trunc(:lmDate)+1 ";
            map.put("lmDate",checkCashbackDayBean.getLmDate());
        }else{
//            sql+=" and trunc(a.lmdate,'dd')=trunc(:lmDate,'dd')";
            sql+=" and a.lmdate>=trunc(sysdate,'dd') ";
//            map.put("lmDate",new Date());
        }
        // 当日代表出售月 可自由选择某一日，代表选择日所在月
        if(StringUtils.isNotEmpty(checkCashbackDayBean.getKeyMonth())){
            sql+=" and substr(a.keymonth,0,6) = :keymonth ";
            map.put("keymonth",checkCashbackDayBean.getKeyMonth().replaceAll("-","").substring(0,6));
        }
        List<Map<String,Object>> list = checkCashbackDayDao.executeSql2(sql, map);
        return list;
    }
}
