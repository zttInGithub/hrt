package com.hrt.biz.check.service.impl;

import com.hrt.biz.bill.dao.IAgentUnitDao;
import com.hrt.biz.bill.entity.model.AgentUnitModel;
import com.hrt.biz.check.dao.CheckWalletCashSwitchDao;
import com.hrt.biz.check.dao.CheckWalletCashSwitchLogDao;
import com.hrt.biz.check.dao.CheckWalletCashSwitchWDao;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchLogModel;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchModel;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchWModel;
import com.hrt.biz.check.entity.pagebean.CheckWalletCashSwitchBean;
import com.hrt.biz.check.service.CheckUnitProfitMicroService;
import com.hrt.biz.check.service.CheckWalletCashSwitchService;
import com.hrt.frame.dao.sysadmin.IUnitDao;
import com.hrt.frame.entity.model.UnitModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.DateTools;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CheckWalletCashSwitchServiceImpl
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2019/10/22
 * @since 1.8
 **/
public class CheckWalletCashSwitchServiceImpl implements CheckWalletCashSwitchService {
    private static final Log log = LogFactory.getLog(CheckWalletCashSwitchServiceImpl.class);

    private CheckWalletCashSwitchDao checkWalletCashSwitchDao;
    private CheckWalletCashSwitchWDao checkWalletCashSwitchWDao;
    private CheckWalletCashSwitchLogDao checkWalletCashSwitchLogDao;
    private CheckUnitProfitMicroService checkUnitProfitMicroService;
    private IUnitDao unitDao;
    private IAgentUnitDao agentUnitDao;
    

	public IAgentUnitDao getAgentUnitDao() {
		return agentUnitDao;
	}

	public void setAgentUnitDao(IAgentUnitDao agentUnitDao) {
		this.agentUnitDao = agentUnitDao;
	}

	public IUnitDao getUnitDao() {
		return unitDao;
	}

	public void setUnitDao(IUnitDao unitDao) {
		this.unitDao = unitDao;
	}

	public CheckUnitProfitMicroService getCheckUnitProfitMicroService() {
        return checkUnitProfitMicroService;
    }

    public void setCheckUnitProfitMicroService(CheckUnitProfitMicroService checkUnitProfitMicroService) {
        this.checkUnitProfitMicroService = checkUnitProfitMicroService;
    }

    public CheckWalletCashSwitchDao getCheckWalletCashSwitchDao() {
        return checkWalletCashSwitchDao;
    }

    public void setCheckWalletCashSwitchDao(CheckWalletCashSwitchDao checkWalletCashSwitchDao) {
        this.checkWalletCashSwitchDao = checkWalletCashSwitchDao;
    }

    public CheckWalletCashSwitchWDao getCheckWalletCashSwitchWDao() {
        return checkWalletCashSwitchWDao;
    }

    public void setCheckWalletCashSwitchWDao(CheckWalletCashSwitchWDao checkWalletCashSwitchWDao) {
        this.checkWalletCashSwitchWDao = checkWalletCashSwitchWDao;
    }

    public CheckWalletCashSwitchLogDao getCheckWalletCashSwitchLogDao() {
        return checkWalletCashSwitchLogDao;
    }

    public void setCheckWalletCashSwitchLogDao(CheckWalletCashSwitchLogDao checkWalletCashSwitchLogDao) {
        this.checkWalletCashSwitchLogDao = checkWalletCashSwitchLogDao;
    }

    /**
     * 活动是否可用为钱包活动类型
     * @param rebateType
     * @return
     */
    public boolean availableRebateType(String rebateType){
        String sql="select count(1) from bill_purconfigure t where t.type=3 and t.cby='1' and t.valueinteger=:valueinteger";
        Map map = new HashMap();
        map.put("valueinteger",Integer.parseInt(rebateType));
        return checkWalletCashSwitchDao.querysqlCounts2(sql,map)>0;
    }

    /**
     * 可用为钱包的活动类型列表
     * @return
     */
    public DataGridBean availableRebateTypeList(UserBean user) {
        DataGridBean dgb = new DataGridBean();
        String sql="select * from bill_purconfigure t where t.type=3 and t.cby='1' ";
        if(user.getUnitLvl()!=0){
            sql+=" and t.valueinteger in ("+getRebateTypeListByUser(user)+") ";
        }
        List<Map<String,String>> list = checkWalletCashSwitchDao.queryObjectsBySqlListMap(sql, null);
        dgb.setRows(list);
        return dgb;
    }

    public String getRebateTypeListByUser(UserBean user){
        List<Integer> list=new ArrayList<Integer>();
        StringBuilder stringBuilder=new StringBuilder();
        Map params = new HashMap();
        params.put("unno",user.getUnNo());
        if(user.getUnitLvl()<=2){
            String sqlMd="select count(1) from hrt_unno_cost t" +
                    " where t.machine_type = 1 and t.txn_type = 1 and t.txn_detail in (1, 2) and t.status=1 and t.debit_rate is not null and t.unno=:unno";
            Integer count = checkWalletCashSwitchDao.querysqlCounts2(sqlMd,params);
            if(count>0){
                stringBuilder.append(0);
            }
            String sqlAct="select distinct(t.txn_detail) txn_detail from hrt_unno_cost t" +
                    " where t.machine_type = 1 and t.txn_type = 1 and t.txn_detail >=20 and t.status=1 and t.unno=:unno";
            List<Map<String,String>> actList=checkWalletCashSwitchDao.queryObjectsBySqlListMap(sqlAct,params);
            for(Map m:actList){
                if(stringBuilder.toString().length()>0){
                    stringBuilder.append(",");
                }
                stringBuilder.append(Integer.parseInt(m.get("TXN_DETAIL").toString()));
            }
        }else{
            String mposSql="select count(1) from check_unit_profitemplate a,check_micro_profittemplate b" +
                    " where a.aptid=b.aptid and a.status=1 and b.merchanttype in (1,2,3) and a.unno=:unno";
            Integer mdCount = checkWalletCashSwitchDao.querysqlCounts2(mposSql,params);
            if(mdCount>0){
                stringBuilder.append(0);
            }

            String sytSql="select count(1) from check_unit_profitemplate a,check_micro_profittemplate b" +
                    " where a.aptid=b.aptid and a.status=1 and b.merchanttype in (5) and a.unno=:unno";
            Integer sytCount = checkWalletCashSwitchDao.querysqlCounts2(sytSql,params);
            if(sytCount>0){
                if(stringBuilder.toString().length()>0){
                    stringBuilder.append(",");
                }
                stringBuilder.append("20,21");
            }

            String plusSql="select distinct(b.profitrule) profitrule from check_unit_profitemplate a,check_micro_profittemplate b" +
                    " where a.aptid=b.aptid and a.status=1 and b.merchanttype in (6) and a.unno=:unno ";
            List<Map<String,String>> plusList=checkWalletCashSwitchDao.queryObjectsBySqlListMap(plusSql,params);
            for(Map m:plusList){
                if(stringBuilder.toString().length()>0){
                    stringBuilder.append(",");
                }
                stringBuilder.append(Integer.parseInt(m.get("profitrule").toString()));
            }
        }
        return stringBuilder.toString().length()>0?stringBuilder.toString():"-1";
    }

    @Override
    public String validateWalletCashStatusInfo(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean user){
        String errTip=null;
        // 该登录用户是否有该活动类型成本
        String a=validateWalletCashStatus(user.getUnitLvl(),user.getUnNo(),checkWalletCashSwitchBean.getUnno(),
                checkWalletCashSwitchBean.getRebateType(),checkWalletCashSwitchBean.getWalletStatus(),true);
        if(hasWalletCashByUnnoAndRebateType(checkWalletCashSwitchBean)){
            // 非首次设置
            if(user.getUnitLvl()<=2){
                if(StringUtils.isNotEmpty(a)){
                    errTip=a;
                    return errTip;
                }else{
                	if(user.getUnitLvl()<2) {//运营开通一代判断一代老钱包是否开启，一代开通下级判断当前登录一代是否开启老钱包20191127-ztt
                		if(hasBeforePursetypeStatus(checkWalletCashSwitchBean.getUnno())){
                			errTip=checkWalletCashSwitchBean.getUnno()+"机构报单平台老钱包是开通状态，不能开通代理分润差额钱包，若想开通代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
                			return errTip;
                		}
                	}else {
                		if(hasBeforePursetypeStatus(user.getUnNo())){
                			errTip="您之前报单平台老钱包是开通状态，不能开通下级代理分润差额钱包，若想开通下级代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
                			return errTip;
                		}
                	}
                }
            }else{
                if(StringUtils.isNotEmpty(a)){
                    errTip=a;
                    return errTip;
                }else{
                    CheckWalletCashSwitchBean mm=getCheckWalletCashSwitchInfo(1, user.getUnNo(), checkWalletCashSwitchBean.getRebateType());
                    if(!"1".equals(mm.getWalletStatus())){
                        errTip="登录用户活动"+checkWalletCashSwitchBean.getRebateType()+" 分润差额钱包未开通，不能开通该登录用户下级代理分润差额钱包";
                        return errTip;
                    }
                }
            }
            
            //20191211ztt---将validateWalletCashStatus()中判断登陆用户的方法前移到这里【这里是非首次的判断----
            //先需要判定上级机构_w表中是否有开通记录---有通过，没有需要再判断上级机构的实时表中有无开通记录---有，可以开通。
            //这里再没有提示不能开通】
            if(user.getUnitLvl()>=2 && "1".equals(checkWalletCashSwitchBean.getWalletStatus())) {
              // 20191211ztt---判断上级机构w表中是否有记录
              CheckWalletCashSwitchBean next = getCheckWalletCashSwitchInfo(2, user.getUnNo(), checkWalletCashSwitchBean.getRebateType());
              if ("1".equals(next.getWalletStatus())) {
              }else if("0".equals(next.getWalletStatus())){
            	  return "登录用户未开通活动"+checkWalletCashSwitchBean.getRebateType()+"代理分润差额钱包(下月),不允许开通下级代理分润差额钱包";
              }else{
            	  CheckWalletCashSwitchBean now = getCheckWalletCashSwitchInfo(1, user.getUnNo(), checkWalletCashSwitchBean.getRebateType());
            	  if (!"1".equals(now.getWalletStatus())) {
            		  return "登录用户未开通活动"+checkWalletCashSwitchBean.getRebateType()+"代理分润差额钱包(下月),不允许开通下级代理分润差额钱包";
            	  }
              }
          }
            
        }else{
            // 首次设置
            // 登录用户是否为机构、一代
            if(user.getUnitLvl()<=2 && user.getUnitLvl()!=0){
                if (StringUtils.isNotEmpty(a)) {
                    errTip=a;
                    return errTip;
//                    return "登录用户活动XX 无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动XX成本后再操作";
                }else{
                	
                	if(user.getUnitLvl()<2) {//运营开通一代判断一代老钱包是否开启，一代开通下级判断当前登录一代是否开启老钱包20191127-ztt
                		if(hasBeforePursetypeStatus(checkWalletCashSwitchBean.getUnno())){
                			errTip=checkWalletCashSwitchBean.getUnno()+"机构报单平台老钱包是开通状态，不能开通代理分润差额钱包，若想开通代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
                			return errTip;
                		}
                	}else {
                		if(user.getUnitLvl()==0){
                            errTip="该代理为中心机构号，禁止提交";
                            return errTip;
                        }
                		if(hasBeforePursetypeStatus(user.getUnNo())){
                			errTip="您之前报单平台老钱包是开通状态，不能开通下级代理分润差额钱包，若想开通下级代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
                			return errTip;
                		}
                	}
//                    if(hasBeforePursetypeStatus(user.getUnNo())){
//                        errTip="您之前报单平台老钱包是开通状态，不能开通下级代理分润差额钱包，若想开通下级代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
//                        return errTip;
//                    }else{
//                        if(user.getUnitLvl()==0){
//                            errTip="该代理为中心机构号，禁止提交";
//                            return errTip;
//                        }
//                    }
                }
            }else{
                if (StringUtils.isNotEmpty(a)) {
                    errTip=a;
                    return errTip;
//                    return "登录用户活动XX 无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动XX成本后再操作";
                }else{
                    CheckWalletCashSwitchBean mm=getCheckWalletCashSwitchInfo(1, user.getUnNo(), checkWalletCashSwitchBean.getRebateType());
                    if(!"1".equals(mm.getWalletStatus())){
                        errTip="登录用户活动"+checkWalletCashSwitchBean.getRebateType()+" 分润差额钱包未开通，不能开通该登录用户下级代理分润差额钱包";
                        return errTip;
                    }
                }
            }
            
          //20191211---将validateWalletCashStatus()中判断登陆用户的方法前移到这里【这里是首次的判断--二代以后的
            //开通需判定当前登录用户的此活动钱包是否开启】
            if(user.getUnitLvl()>=2 && "1".equals(checkWalletCashSwitchBean.getWalletStatus())) {
                CheckWalletCashSwitchBean now = getCheckWalletCashSwitchInfo(1, user.getUnNo(), checkWalletCashSwitchBean.getRebateType());
                if (!"1".equals(now.getWalletStatus())) {
                    return "登录用户未开通活动"+checkWalletCashSwitchBean.getRebateType()+"代理分润差额钱包,不允许开通下级代理分润差额钱包";
                }
            }
            
        }
        return errTip;
    }

    /**
     * 机构旧分润钱包是否为开
     * @param unno
     * @return
     */
    @Override
    public boolean hasBeforePursetypeStatus(String unno){
        String sql="select count(1) from bill_agentunitinfo t where t.unno=:unno and t.pursetype=1 ";
        Map map = new HashMap();
        map.put("unno",unno);
        int result = checkWalletCashSwitchDao.querysqlCounts2(sql,map);
        return result>0;
    }

    public DataGridBean subUnnoList(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean user) {
        DataGridBean dgb = new DataGridBean();
        String sql = "select unno,un_name from sys_unit where 1=1 ";
        Map map=new HashMap();
        if("110000".equals(user.getUnNo())) {
            sql += " and upper_unit=:unno ";
            map.put("unno",user.getUnNo());
        }else {
            sql += " and upper_unit=:unno ";
            map.put("unno",user.getUnNo());
        }
        if(checkWalletCashSwitchBean.getRebateType()!=null){
            String subUnnoSql=getHasRebateTypeBySubUnno(user,checkWalletCashSwitchBean.getRebateType());
            if(StringUtils.isNotEmpty(subUnnoSql)){
                sql+=" and unno in ("+subUnnoSql+") ";
            }else{
                sql+=" and 1=0 ";
            }
        }
        List<Map<String,String>> list = checkWalletCashSwitchDao.queryObjectsBySqlListMap(sql, map);
        dgb.setRows(list);
        return dgb;
    }

    /**
     * 按活动类型,获取登录的子代机构sql
     * @param user
     * @param rebateType
     * @return
     */
    public String getHasRebateTypeBySubUnno(UserBean user,String rebateType){
        if(user.getUnitLvl()==1){
            if("0".equals(rebateType)){
                String sqlMd="select distinct(b1.unno) from hrt_unno_cost b1,sys_unit b2 where b2.upper_unit='"+user.getUnNo()+"' and b1.unno=b2.unno and b1.machine_type=1 and b1.txn_type=1 and b1.txn_detail in (1,2) and b1.debit_rate is not null";
                List mdList = checkWalletCashSwitchDao.queryObjectsBySqlListMap(sqlMd,null);
                if(mdList.size()>0){
                    return sqlMd;
                }
            }else if(Integer.valueOf(rebateType).compareTo(20)>=0){
                String sqlAct="select distinct(b1.unno) from hrt_unno_cost b1,sys_unit b2 where b2.upper_unit='"+user.getUnNo()+"' and b1.unno=b2.unno and b1.machine_type=1 and b1.txn_type=1 and b1.txn_detail='"+Integer.valueOf(rebateType)+"'";
                List actList = checkWalletCashSwitchDao.queryObjectsBySqlListMap(sqlAct,null);
                if(actList.size()>0){
                    return sqlAct;
                }
            }
        }else if(user.getUnitLvl()>=2){
            if("0".equals(rebateType)){
                String mposSql="select distinct(b1.unno) from check_unit_profitemplate b1,sys_unit b2,check_micro_profittemplate b3 " +
                        " where b2.upper_unit = '"+user.getUnNo()+"' and b1.unno = b2.unno and b1.aptid = b3.aptid and b1.status = 1 and b3.mataintype!='D' and b3.merchanttype in (1,2,3)";
                List mdList = checkWalletCashSwitchDao.queryObjectsBySqlListMap(mposSql,null);
                if(mdList.size()>0){
                    return mposSql;
                }
            }else if(".20.21.".contains("."+rebateType+".")){
                String sytSql="select distinct(b1.unno) from check_unit_profitemplate b1,sys_unit b2,check_micro_profittemplate b3 " +
                        " where b2.upper_unit = '"+user.getUnNo()+"' and b1.unno = b2.unno and b1.aptid = b3.aptid and b1.status = 1 and b3.mataintype!='D' and b3.merchanttype in (5)";
                List sytList = checkWalletCashSwitchDao.queryObjectsBySqlListMap(sytSql,null);
                if(sytList.size()>0){
                    return sytSql;
                }
            }else if(Integer.valueOf(rebateType).compareTo(22)>=0){
                String plusSql="select distinct(b1.unno) from check_unit_profitemplate b1,sys_unit b2,check_micro_profittemplate b3 " +
                        " where b2.upper_unit = '"+user.getUnNo()+"' and b1.unno = b2.unno and b1.aptid = b3.aptid " +
                        " and b1.status = 1 and b3.mataintype!='D' and b3.merchanttype in (6) and b3.profitrule='"+Integer.valueOf(rebateType)+"'";
                List plusList = checkWalletCashSwitchDao.queryObjectsBySqlListMap(plusSql,null);
                if(plusList.size()>0){
                    return plusSql;
                }
            }

        }

        return "";
    }

    /**
     * A机构是否为B机构的下级
     * @param unno A
     * @param upperUnno B
     * @return
     */
    public boolean unnoInUpperUnno(String unno,String upperUnno){
        String sql = "select count(1) from sys_unit where 1=1 and upper_unit=:upper_unit and unno=:unno and un_lvl!=1";
        Map map=new HashMap();
        map.put("upper_unit",upperUnno);
        map.put("unno",unno);
        return checkWalletCashSwitchDao.querysqlCounts2(sql,map)>0;
    }

    @Override
    public String validateWalletCashStatus(Integer unLvl,String unno,String updateUnno,String rebateType,String status,boolean flag){
        if(!availableRebateType(rebateType)){
            return "代理分润差额钱包不可设置该"+rebateType+"活动钱包";
        }
//        if ("0".equals(status) || unLvl<=1){
//            return null;
//        }
        if(unLvl<=2){
        	if(unLvl < 2) {//登录用户unlvl=1,运营中心开通一代钱包需要判断开通一代的用户是否有开通老钱包
        		if(hasBeforePursetypeStatus(updateUnno)){
        			return "机构"+updateUnno+"之前报单平台老钱包是开通状态，不能开通分润差额钱包，若想开通代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
        		}
        	}else {
        		if(hasBeforePursetypeStatus(unno)){
        			return"您之前报单平台老钱包是开通状态，不能开通下级代理分润差额钱包，若想开通下级代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
        		}
        	}
            if("0".equals(rebateType)){
                if(!checkUnitProfitMicroService.hasRebateTypeByYiDaiUnno(1,rebateType,unno)){
                    return "登录用户与开通下级存在活动"+rebateType+"无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动"+rebateType+"成本后再操作";
                }
            }else{
                if(!checkUnitProfitMicroService.hasRebateTypeByYiDaiUnno(2,rebateType,unno)){
                    return "登录用户与开通下级存在活动"+rebateType+"无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动"+rebateType+"成本后再操作";
                }
            }
        }else if(unLvl>2){
            if("0".equals(rebateType)){
                if(!checkUnitProfitMicroService.hasRebateTypeByUnno(1,rebateType,unno)){
                    return "登录用户与开通下级存在活动"+rebateType+"无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动"+rebateType+"成本后再操作";
                }
            }else if(".20.21.".contains("."+rebateType+".")){
                if(!checkUnitProfitMicroService.hasRebateTypeByUnno(5,rebateType,unno)){
                    return "登录用户与开通下级存在活动"+rebateType+"无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动"+rebateType+"成本后再操作";
                }
            }else{
                if(!checkUnitProfitMicroService.hasRebateTypeByUnno(6,rebateType,unno)){
                    return "登录用户与开通下级存在活动"+rebateType+"无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动"+rebateType+"成本后再操作";
                }
            }
        }
        //20191211-ztt修改注释掉（将其判断条件移动到判断首次非首次之后【首次---只需判定实时表也就是此处的第一个条件
        //非首次的先判定此处第二个方法，有上级机构存在开通条件---可以开通下级机构的w表中的记录，没有需要继续判定上级机构的实时表中
        //是不是有上级机构的开通记录---有开通记录---可以开通下级机构的w表中的记录】）
//        if(unLvl>=2 && flag) {
//            CheckWalletCashSwitchBean now = getCheckWalletCashSwitchInfo(1, unno, rebateType);
//            if (!"1".equals(now.getWalletStatus())) {
//                return "登录用户未开通活动"+rebateType+"代理分润差额钱包,不允许开通下级代理分润差额钱包";
//            }
//            // 下月生效被修改为关闭状态
//            CheckWalletCashSwitchBean next = getCheckWalletCashSwitchInfo(2, unno, rebateType);
//            if ("0".equals(next.getWalletStatus())) {
//                return "登录用户未开通活动"+rebateType+"代理分润差额钱包(下月),不允许开通下级代理分润差额钱包";
//            }
//        }
        return null;
    }

    @Override
    public String saveImportWalletCashSwitchXls(String xlsfile, String name, UserBean user) throws InvocationTargetException, IllegalAccessException {
        StringBuilder errTip=new StringBuilder();
        Map uniqeMap = new HashMap();
        int sumCount = 0;
        File filename = new File(xlsfile);
        HSSFWorkbook workbook = null;
        Map<String,String> uniqueUnno = new HashMap<String,String>();
        List<CheckWalletCashSwitchModel> list = new ArrayList<CheckWalletCashSwitchModel>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(filename));
        } catch (IOException e) {
            log.info("导入批量成本文件异常:"+e.getMessage());
        }
        HSSFSheet sheet = workbook.getSheetAt(0);
        sumCount = sheet.getPhysicalNumberOfRows()-1;
        String numberInfo="";
        for(int i = 1; i <= sumCount; i++){
            numberInfo="第"+(i+1)+"行,";
            CheckWalletCashSwitchModel model = new CheckWalletCashSwitchModel();
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell((short)0);
            if(cell == null || cell.toString().trim().equals("")){
                break;
            }else{
                // 代理机构号
                row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
                String unno = row.getCell((short)0).getStringCellValue().trim();
                model.setUnno(unno);

                // 活动类型
                row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
                String rebateType = row.getCell((short)1).getStringCellValue().trim();
                model.setRebateType(rebateType);

                // 分润差额钱包开关
                row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
                String walletStatus=row.getCell((short)2).getStringCellValue().trim();
                if(StringUtils.isNotEmpty(walletStatus) && "开".equals(walletStatus)) {
                    model.setWalletStatus("1");
                }else{
                    model.setWalletStatus("0");
                }
                list.add(model);

                // 校验
                if(!unnoInUpperUnno(unno,user.getUnNo())){
                    errTip.append("非当前机构的下级，禁止提交");
                    break;
                }

                String key=unno+"|"+rebateType;
                // 1.同一代理同一活动类型，只能上传一条记录，若上传多条，提示“”“XX代理-活动类型XX重复上传”，整批失败；
                if(uniqeMap.containsKey(key)){
                    errTip.append(unno).append("代理-活动类型").append(rebateType).append("重复上传");
                    break;
                }
                uniqeMap.put(key,"1");
                CheckWalletCashSwitchBean kk =new CheckWalletCashSwitchBean();
                kk.setRebateType(rebateType);
                kk.setUnno(unno);
                kk.setWalletStatus(walletStatus);
                String errMsg = validateWalletCashStatusInfo(kk,user);
                if(StringUtils.isNotEmpty(errMsg)){
                    errTip.append(errMsg);
                    break;
                }

                // 2.若上传代理+活动类型对应上级代理没有相应活动类型，提示“登录用户活动XX 无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动XX成本后再操作”，整批失败；
                String tip1=validateWalletCashStatus(user.getUnitLvl(), user.getUnNo(),unno, rebateType, walletStatus,false);
                if(StringUtils.isNotEmpty(tip1)) {
                    errTip.append("登录用户活动").append(rebateType).append(" 无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动").append(rebateType).append("成本后再操作");
                    break;
                }
                String tip2=validateWalletCashStatus(user.getUnitLvl()+1, unno,null,rebateType,walletStatus,false);
                if(StringUtils.isNotEmpty(tip2)){
                    errTip.append("设置机构").append(unno).append("活动").append(rebateType).append(" 无成本，不能开通下级代理分润差额钱包，请联系上级代理设置活动").append(rebateType).append("成本后再操作");
                    break;
                }

                // 3.若B代理（二代及以后）上级代理A活动类型X本月钱包状态为“关”，则上传B代理+活动类型X 设置钱包状态为“开”，则提示“登录用户活动XX 分润差额钱包未开通，不能开通该登录用户下级代理分润差额钱包”，整批失败；
                //若B代理（一代），只要登录用户存在活动XX成本，就可以开通B代理活动XX分润钱包，不用判断上级代理钱包开关情况；
                //若B代理（机构），不允许提交B代理任何活动类型钱包开通变更申请，若提用户提交了，则提示“该代理为中心机构号，禁止提交”
//                if("1".equals(walletStatus) && user.getUnitLvl()<=1){
//                    CheckWalletCashSwitchBean now = getCheckWalletCashSwitchInfo(1,user.getUnNo(),rebateType);
//                    if("0".equals(now.getWalletStatus()) || now.getWalletStatus()==null){
//                        errTip.append("“登录用户活动").append(rebateType).append("分润差额钱包未开通，不能开通该登录用户下级代理分润差额钱包");
//                        break;
//                    }
//                }
            }
        }
        if(errTip.toString().length()>0){
            return numberInfo+errTip.toString();
        }

        //4.代理是首次设置当日生效，非首日设置下月生效
        for (CheckWalletCashSwitchModel model : list) {
            CheckWalletCashSwitchBean t=new CheckWalletCashSwitchBean();
            BeanUtils.copyProperties(model,t);
            if(hasWalletCashByUnnoAndRebateType(t)){
                CheckWalletCashSwitchWModel wModel = hasWalletCashWByUnnoAndRebateType(t);
                if(wModel!=null){
                    wModel.setCreateDate(new Date());
                    wModel.setWalletStatus(model.getWalletStatus());
                    wModel.setRemark1(user.getUserID()+"");
                    checkWalletCashSwitchWDao.saveOrUpdateObject(wModel);
                }else{
                    CheckWalletCashSwitchWModel newModel=new CheckWalletCashSwitchWModel();
                    BeanUtils.copyProperties(model,newModel);
                    newModel.setCreateDate(new Date());
                    newModel.setRemark1(user.getUserID()+"");
                    checkWalletCashSwitchWDao.saveObject(newModel);
                }
            }else{
                t.setCreateDate(new Date());
                t.setRemark1(user.getUserID()+"");
                saveWalletCash(t);
            }
        }
        return null;
    }

    public CheckWalletCashSwitchBean getCheckWalletCashSwitchInfo(int type,String unno,String rebateType){
        CheckWalletCashSwitchBean result = new CheckWalletCashSwitchBean();
        Map map=new HashMap();
        map.put("unno",unno);
        map.put("rebateType",rebateType);
        if (type==1){
            String sql="select * from check_walletCashSwitch t where t.unno=:unno and t.rebatetype=:rebateType";
            List<CheckWalletCashSwitchModel> list=checkWalletCashSwitchDao.queryObjectsBySqlList(sql,map,CheckWalletCashSwitchModel.class);
            if(list.size()>0){
                BeanUtils.copyProperties(list.get(0),result);
            }
        }else if(type==2){
            String sql="select * from check_walletCashSwitch_w t where t.unno=:unno and t.rebatetype=:rebateType and t.createdate>=trunc(sysdate,'mm')";
            List<CheckWalletCashSwitchWModel> list=checkWalletCashSwitchWDao.queryObjectsBySqlList(sql,map,CheckWalletCashSwitchWModel.class);
            if(list.size()>0){
                BeanUtils.copyProperties(list.get(0),result);
            }
        }else if(type==3){

        }
        return result;
    }

    @Override
    public boolean updateWalletCashWStatus(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean){
        boolean flag=false;
        if(StringUtils.isEmpty(checkWalletCashSwitchBean.getUnno()) || StringUtils.isEmpty(checkWalletCashSwitchBean.getWalletStatus())){
            log.error("updateWalletCashWStatus 修改信息参数不全");
            return false;
        }
        String sql = "select t.* from  check_walletcashswitch_w t where t.unno=:unno and t.rebatetype=:rebatetype and t.createdate>=trunc(sysdate,'mm')";
        Map param=new HashMap();
        param.put("unno",checkWalletCashSwitchBean.getUnno());
        param.put("rebatetype",checkWalletCashSwitchBean.getRebateType());
        List<CheckWalletCashSwitchWModel> list = checkWalletCashSwitchWDao.queryObjectsBySqlList(sql,param,CheckWalletCashSwitchWModel.class);
        if(list.size()==1){
            CheckWalletCashSwitchWModel t=list.get(0);
            t.setWalletStatus(checkWalletCashSwitchBean.getWalletStatus());
            checkWalletCashSwitchWDao.saveOrUpdateObject(t);
            flag=true;
        }else{
            CheckWalletCashSwitchWModel newModel = new CheckWalletCashSwitchWModel();
            BeanUtils.copyProperties(checkWalletCashSwitchBean,newModel);
            newModel.setCreateDate(new Date());
            checkWalletCashSwitchWDao.saveObject(newModel);
            flag=true;
        }
        return flag;
    }

    @Override
    public DataGridBean findAllWalletCashLog(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean){
        String sql = "select a.unno,b.un_name,a.rebatetype,a.createdate,a.enddate,a.walletstatus,b.un_lvl " +
                "  from check_walletCashSwitch_log a, sys_unit b where a.unno = b.unno ";
        Map map=new HashMap();
        if("110000".equals(userBean.getUnNo())) {
        }else if(userBean.getUseLvl()==0) {
            sql += " and a.unno in (select unno from sys_unit where upper_unit=:unno1 or unno = :unno1) ";
            map.put("unno1",userBean.getUnNo());
        }

        // 机构号精确查询
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getUnno())) {
            sql += " and a.unno = :unno ";
            map.put("unno",checkWalletCashSwitchBean.getUnno());
        }

        // 机构名称精确查询
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getRemark1())) {
            sql += " and b.un_name = :remark1 ";
            map.put("remark1",checkWalletCashSwitchBean.getRemark1());
        }

        // 活动类型
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getRebateType()) && !"-1".equals(checkWalletCashSwitchBean.getRebateType())) {
            sql += " and a.rebatetype = :rebateType ";
            map.put("rebateType",checkWalletCashSwitchBean.getRebateType());
        }

        // 钱包状态
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getWalletStatus())) {
            sql += " and a.walletstatus = :walletstatus ";
            map.put("walletstatus",checkWalletCashSwitchBean.getWalletStatus());
        }
        List<Map<String, Object>> list = checkWalletCashSwitchLogDao.queryObjectsBySqlList2(sql,map,checkWalletCashSwitchBean.getPage(),checkWalletCashSwitchBean.getRows());
        DataGridBean dataGridBean = new DataGridBean();
        dataGridBean.setTotal(checkWalletCashSwitchLogDao.querysqlCounts2("select count(1) from ("+sql+")",map));
        dataGridBean.setRows(list);
        return dataGridBean;
    }

    @Override
    public DataGridBean findAllWalletCash(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean){
        String sql = "select a.unno,b.un_name,a.rebatetype,a.createdate,a.walletstatus," +
                "nvl((select k.walletstatus from check_walletCashSwitch_w k where k.unno=a.unno and k.rebatetype=a.rebatetype and k.createdate>trunc(sysdate,'mm')),2) walletstatus1,nvl((case when to_char(sysdate,'dd')='01' then (" +
                " select cw.walletstatus from check_walletCashSwitch_w cw where cw.unno=a.unno and cw.rebatetype=a.rebatetype " +
                " and cw.createdate>=trunc(sysdate-2,'mm') and cw.createdate<trunc(sysdate,'mm')) else '2' end),2) walletstatus2," +
                " b.un_lvl " +
                "  from check_walletCashSwitch a, sys_unit b where a.unno = b.unno ";
        Map map=new HashMap();
        if("110000".equals(userBean.getUnNo())) {
        }else if(userBean.getUnitLvl()>=1) {
            // or unno = :unno1
            sql += " and a.unno in (select unno from sys_unit where upper_unit=:unno1) ";
            map.put("unno1",userBean.getUnNo());
        }

        // 机构号精确查询
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getUnno())) {
            sql += " and a.unno = :unno ";
            map.put("unno",checkWalletCashSwitchBean.getUnno());
        }

        // 机构名称精确查询
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getRemark1())) {
            sql += " and b.un_name = :remark1 ";
            map.put("remark1",checkWalletCashSwitchBean.getRemark1());
        }

        // 活动类型
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getRebateType()) && !"-1".equals(checkWalletCashSwitchBean.getRebateType())) {
            sql += " and a.rebatetype = :rebateType ";
            map.put("rebateType",checkWalletCashSwitchBean.getRebateType());
        }

        // 钱包状态
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getWalletStatus())) {
            sql += " and a.walletstatus = :walletstatus ";
            map.put("walletstatus",checkWalletCashSwitchBean.getWalletStatus());
        }

        List<Map<String, Object>> list = checkWalletCashSwitchDao.queryObjectsBySqlList2(sql,map,checkWalletCashSwitchBean.getPage(),checkWalletCashSwitchBean.getRows());
        DataGridBean dataGridBean = new DataGridBean();
        dataGridBean.setTotal(checkWalletCashSwitchDao.querysqlCounts2("select count(1) from ("+sql+")",map));
        dataGridBean.setRows(list);
        return dataGridBean;
    }
    @Override
    public DataGridBean findAllWalletCashJS(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean){
    	String sql = "select a.unno,b.un_name,a.rebatetype,a.createdate,a.walletstatus," +
                "nvl((select k.walletstatus from check_walletCashSwitch_w k where k.unno=a.unno and k.rebatetype=a.rebatetype and k.createdate>trunc(sysdate,'mm')),2) walletstatus1,nvl((case when to_char(sysdate,'dd')='01' then (" +
                " select cw.walletstatus from check_walletCashSwitch_w cw where cw.unno=a.unno and cw.rebatetype=a.rebatetype " +
                " and cw.createdate>=trunc(sysdate-2,'mm') and cw.createdate<trunc(sysdate,'mm')) else '2' end),2) walletstatus2," +
                " b.un_lvl " +
                "  from check_walletCashSwitch a, sys_unit b where a.unno = b.unno ";
        Map map=new HashMap();
        if("110000".equals(userBean.getUnNo())) {
        }else if(userBean.getUseLvl()==0) {
            sql += " and a.unno in (select unno from sys_unit start with unno=:unno1 connect by prior unno = upper_unit) ";
            map.put("unno1",userBean.getUnNo());
        }

        // 机构号精确查询
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getUnno())) {
            sql += " and a.unno = :unno ";
            map.put("unno",checkWalletCashSwitchBean.getUnno());
        }

        // 活动类型
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getRebateType()) && !"-1".equals(checkWalletCashSwitchBean.getRebateType())) {
            sql += " and a.rebatetype = :rebateType ";
            map.put("rebateType",checkWalletCashSwitchBean.getRebateType());
        }

        // 钱包状态
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getWalletStatus())) {
            sql += " and a.walletstatus = :walletstatus ";
            map.put("walletstatus",checkWalletCashSwitchBean.getWalletStatus());
        }
        List<Map<String, Object>> list = checkWalletCashSwitchDao.queryObjectsBySqlList2(sql,map,checkWalletCashSwitchBean.getPage(),checkWalletCashSwitchBean.getRows());
        DataGridBean dataGridBean = new DataGridBean();
        dataGridBean.setTotal(checkWalletCashSwitchDao.querysqlCounts2("select count(1) from ("+sql+")",map));
        dataGridBean.setRows(list);
        return dataGridBean;
    }

    @Override
    public DataGridBean findAllWalletCashW(CheckWalletCashSwitchBean checkWalletCashSwitchBean){
        String hql="from CheckWalletCashSwitchWModel ";
        List<CheckWalletCashSwitchWModel> list = checkWalletCashSwitchWDao.queryObjectsByHqlList(hql,null,checkWalletCashSwitchBean.getPage(),checkWalletCashSwitchBean.getRows());
        DataGridBean dataGridBean = new DataGridBean();
        dataGridBean.setTotal(checkWalletCashSwitchWDao.queryCounts("select count(*) "+hql,null));
        dataGridBean.setRows(list);
        return dataGridBean;
    }

    @Override
    public CheckWalletCashSwitchModel saveWalletCash(CheckWalletCashSwitchBean checkWalletCashSwitchBean) throws InvocationTargetException, IllegalAccessException {
        CheckWalletCashSwitchModel newModel = new CheckWalletCashSwitchModel();
        CheckWalletCashSwitchLogModel newLogModel = new CheckWalletCashSwitchLogModel();
        BeanUtils.copyProperties(checkWalletCashSwitchBean,newModel);
        BeanUtils.copyProperties(checkWalletCashSwitchBean,newLogModel);
        Date date=new Date();
        newModel.setCreateDate(date);
        newModel.setRemark1(checkWalletCashSwitchBean.getRemark1());
        checkWalletCashSwitchDao.saveObject(newModel);
        newLogModel.setCreateDate(date);
        newLogModel.setRemark1(checkWalletCashSwitchBean.getRemark1());
        checkWalletCashSwitchLogDao.saveObject(newLogModel);
        return newModel;
    }

    public boolean hasWalletCashByUnnoAndRebateType(CheckWalletCashSwitchBean checkWalletCashSwitchBean){
        String hql="from CheckWalletCashSwitchModel t where t.unno=:unno and t.rebateType=:rebateType";
        Map map=new HashMap();
        map.put("unno",checkWalletCashSwitchBean.getUnno());
        map.put("rebateType",checkWalletCashSwitchBean.getRebateType());
        return checkWalletCashSwitchDao.queryObjectsByHqlList(hql,map).size()>0?true:false;
    }

    @Override
    public void updateWalletCashW(CheckWalletCashSwitchBean checkWalletCashSwitchBean){
        String sql="select * from check_walletCashSwitch_w t where t.unno=:unno and t.rebatetype=:rebateType and t.createdate>=trunc(sysdate,'mm')";
        Map map=new HashMap();
        map.put("unno",checkWalletCashSwitchBean.getUnno());
        map.put("rebateType",checkWalletCashSwitchBean.getRebateType());
        List<CheckWalletCashSwitchWModel> list=checkWalletCashSwitchWDao.queryObjectsBySqlList(sql,map,CheckWalletCashSwitchWModel.class);
        if(list.size()>0){
            CheckWalletCashSwitchWModel t=list.get(0);
            t.setRemark1(checkWalletCashSwitchBean.getRemark1());
            t.setWalletStatus(checkWalletCashSwitchBean.getWalletStatus());
            checkWalletCashSwitchWDao.saveOrUpdateObject(t);
        }else{
            CheckWalletCashSwitchWModel wModel = new CheckWalletCashSwitchWModel();
            wModel.setCreateDate(new Date());
            wModel.setUnno(checkWalletCashSwitchBean.getUnno());
            wModel.setRebateType(checkWalletCashSwitchBean.getRebateType());
            wModel.setRemark1(checkWalletCashSwitchBean.getRemark1());
            wModel.setWalletStatus(checkWalletCashSwitchBean.getWalletStatus());
            checkWalletCashSwitchWDao.saveObject(wModel);
        }
    }

    public CheckWalletCashSwitchWModel hasWalletCashWByUnnoAndRebateType(CheckWalletCashSwitchBean checkWalletCashSwitchBean){
        String sql="select * from check_walletCashSwitch_w t where t.unno=:unno and t.rebatetype=:rebateType and t.createdate>=trunc(sysdate,'mm')";
        Map map=new HashMap();
        map.put("unno",checkWalletCashSwitchBean.getUnno());
        map.put("rebateType",checkWalletCashSwitchBean.getRebateType());
        List<CheckWalletCashSwitchWModel> list=checkWalletCashSwitchWDao.queryObjectsBySqlList(sql,map,CheckWalletCashSwitchWModel.class);
        return list.size()>0?list.get(0):null;
    }

    @Override
    public CheckWalletCashSwitchWModel saveWalletCashW(CheckWalletCashSwitchBean checkWalletCashSwitchBean) throws InvocationTargetException, IllegalAccessException {
        CheckWalletCashSwitchWModel newModel = new CheckWalletCashSwitchWModel();
        BeanUtils.copyProperties(checkWalletCashSwitchBean,newModel);
        newModel.setCreateDate(new Date());
//        newModel.setEndDate(new Date());
        checkWalletCashSwitchWDao.saveObject(newModel);
        return newModel;
    }

    @Override
    public CheckWalletCashSwitchLogModel saveWalletCashLog(CheckWalletCashSwitchBean checkWalletCashSwitchBean) throws InvocationTargetException, IllegalAccessException {
        CheckWalletCashSwitchLogModel newModel = new CheckWalletCashSwitchLogModel();
        BeanUtils.copyProperties(checkWalletCashSwitchBean,newModel);
        newModel.setCreateDate(new Date());
        newModel.setEndDate(new Date());
        checkWalletCashSwitchLogDao.saveObject(newModel);
        return newModel;
    }
    
	@Override
	public String saveImportBatchWalletCashSwitchJS(String xlsfile, String name, UserBean user) {
		Map resutMap = new HashMap();
		int sumCount = 0;
		int errCount = 0;
		File filename = new File(xlsfile);
		HSSFWorkbook workbook = null;
		Map<String,String> uniqueUnno = new HashMap<String,String>();
		List<CheckWalletCashSwitchModel> list = new ArrayList<CheckWalletCashSwitchModel>();
        try {
            workbook = new HSSFWorkbook(new FileInputStream(filename));
        } catch (IOException e) {
            log.info("导入批量成本文件异常:"+e.getMessage());
        }
        HSSFSheet sheet = workbook.getSheetAt(0);
        sumCount = sheet.getPhysicalNumberOfRows()-1;
        for(int i = 1; i <= sumCount; i++){
        	CheckWalletCashSwitchModel model = new CheckWalletCashSwitchModel();
            HSSFRow row = sheet.getRow(i);
            HSSFCell cell = row.getCell((short)0);
            if(cell == null || cell.toString().trim().equals("")){
                break;
            }else{
                // 相关机构号
                row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
                String unno = row.getCell((short)0).getStringCellValue().trim();
                model.setUnno(unno);

                // 活动类型(例如:20)
                HSSFCell cell1= row.getCell((short)1);
                if(cell1 != null && !cell1.toString().trim().equals("")){
                    row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
                    String rebateType = row.getCell((short)1).getStringCellValue().trim();
                    model.setRebateType(rebateType);
                }else {
                	return "钱包变更操作活动不能为空，请检查！";
                }
                
                // 操作类型
                HSSFCell cell2= row.getCell((short)2);
                if(cell2!=null && !cell2.getStringCellValue().isEmpty()) {
					row.getCell((short) 2).setCellType(Cell.CELL_TYPE_STRING);
					String typeFile = row.getCell((short)2).getStringCellValue().trim();
					model.setWalletStatus(typeFile);
                }else {
                	return "钱包变更操作类型不能为空，请检查！";
                }
                
                //对操作的机构进行校验是否符合规则
                String message = checkWalletWashIsSure(model, user);
                if(!message.isEmpty()) {
                	return message;
                }
                
                model.setCreateDate(new Date());
                model.setRemark1(user.getLoginName());
                list.add(model);
            }
        }
        if(DateTools.isFirstDay()) {
        	saveOrUpdateWalletCshSwitchJS(list);//校验通过当月1号执行操作
        }else {
        	saveOrUpdateWalletCshSwitchJS2(list);//校验通过非当月1号执行操作
        }
		return "";
	}
	
	public String checkWalletWashIsSure(CheckWalletCashSwitchModel model,UserBean user) {
		Integer productType1 = null;//查验一代机构是否包含某活动时使用
		Integer productType2 = null;//查验二代及二代以下机构是否包含某活动时使用
		//String activeTran = null;//查验钱包实时表中是否存在作为机构活动使用
		
		if(model.getWalletStatus().equals("开")) {
			model.setWalletStatus("1");
		} else if(model.getWalletStatus().equals("关")){
			model.setWalletStatus("0");
		} else {
			return "操作类型输入不合法，请输入'开'或者'关'";
		}
		
        UnitModel userNnno = unitDao.queryObjectByHql("from UnitModel where unno = ?", new Object[] {user.getUnNo()});
		//对登录操作机构进行判定（只能结算部门进行操作）
		if(userNnno == null || userNnno.getUnLvl() != 0 || userNnno.getUnAttr() != 1){
			return "非运营结算部门没有此权限";
		}
		
		//活动校验---mpos钱包活动录入只能为0，其他认为不合法
		if("20".compareTo(model.getRebateType())>0 && !"0".equals(model.getRebateType())) {
			return "活动录入不合法，如果是mpos请录入0";
		}
		
		//活动校验---判断是否是能够开通钱包开关的活动
		if(!availableRebateType(model.getRebateType())) {
			return "此活动尚未开通钱包开关功能，请校验！";
		}
		
        UnitModel queryUnitModel = unitDao.queryObjectByHql("from UnitModel where unno = ?", new Object[] {model.getUnno()});
        //判断钱包修改机构是否存在（并在添加钱包时，区别一代与二代以后的区别）
        if(queryUnitModel == null) {
        	return "您输入的机构"+model.getUnno()+"不存在,请核验！";
        }else if(false){//判断修改的是否是中心机构号（先不添加此限制，之后需要可以添加）
        	return "提示：该代理为中心机构号，禁止提交";
        }else if(queryUnitModel.getUnLvl() == 2){//判断修改的机构是否是一代，一代开启条件（机构分润钱包未开启，机构下有相应的活动），关闭没有限制
        	AgentUnitModel agentUnitModel = agentUnitDao.queryObjectByHql("from AgentUnitModel where unno = ?", new Object[] {model.getUnno()});
        	if("1".equals(model.getWalletStatus())) {
        		if(agentUnitModel.getPurseType() != null && agentUnitModel.getPurseType() == 1) {
        			return "提示：您之前报单平台老钱包是开通状态，不能开通下级代理分润差额钱包，若想" + 
        					"开通下级代理分润钱包，请联系维护销售关闭原报单平台老钱包提现功能";
        		}else{
        			if("20".compareTo(model.getRebateType())>0) {
                    	productType1 = 1;
                    }else{
                    	productType1 = 2;
                    }
        			boolean hasRebateTypeByYiDaiUnno = checkUnitProfitMicroService.hasRebateTypeByYiDaiUnno(productType1,model.getRebateType(),model.getUnno());
        			if(!hasRebateTypeByYiDaiUnno) {
        				return "操作的机构"+model.getUnno()+"活动"+model.getRebateType()+"无成本，"
        						+ "不能开通下级代理分润差额钱包，请联系上级代理设置活动"+model.getRebateType()+"成本后再操作";
        			}
        		}
        	}
        }else if(queryUnitModel.getParent() 
        		!= null && queryUnitModel.getParent().getUnNo() != null) {//二代一下的开启限制（上级机构开启钱包功能，并且自身有相应的活动模板）
        	if("20".compareTo(model.getRebateType())>0) {
            	productType2 = 1;
            }else if(".20.21.".contains(model.getRebateType())) {
            	productType2 = 5;
            }else {
            	productType2 = 6;
            }
        	
        	String isWalletCashSwitchParentUnnosql = "select count(1) from check_walletcashswitch t where 1=1"+
        									" and t.unno = '"+queryUnitModel.getParent().getUnNo()+"'"+ 
        									" and t.rebatetype = '"+model.getRebateType()+"' and t.walletstatus = '1'";
        	Integer integer = checkWalletCashSwitchDao.querysqlCounts2(isWalletCashSwitchParentUnnosql, null);
        	//开通时判断二代后此上级机构是否存在该活动钱包
        	if(integer < 1) {
        		return "您操作的机构"+model.getUnno()+"的上级机构此活动钱包功能未开启，不能开启此钱包，请核验！";
        	}
            boolean rebateTypeIsNull = checkUnitProfitMicroService.hasRebateTypeByUnno(productType2, model.getRebateType(), model.getUnno());
            //开通时判断二代后此机构是否存在该活动
            if(!rebateTypeIsNull) {
            	return "您操作的机构"+model.getUnno()+"的机构无此活动类型，不能开启钱包，请核验！";
            }
        }
        
        //操作时判断此钱包是否已开通
        CheckWalletCashSwitchModel switchModel = checkWalletCashSwitchDao.queryObjectByHql("from CheckWalletCashSwitchModel where unno = ? and rebateType = ?", new Object[] {model.getUnno(),model.getRebateType()});
        if(switchModel == null) {//未查询到数据，不论是开通还是关闭功能
        	if("0".equals(model.getWalletStatus())) {
        		return "您操作的机构"+model.getUnno()+"无此活动钱包功能,不能执行关闭操作,请核验！";
        	}
        }else {//钱包实时表中有数据，需要判定操作是否一致
        	if("1".equals(switchModel.getWalletStatus()) && "1".equals(model.getWalletStatus())) {
        		return "您操作的机构"+model.getUnno()+"此钱包功能已开启，请不要重复开启操作！";
        	}else if(!"1".equals(switchModel.getWalletStatus()) && "0".equals(model.getWalletStatus())) {
        		return "您操作的机构"+model.getUnno()+"此钱包功能已关闭，请不要重复关闭操作！";
        	}
        }
        return "";
	}
	
	//执行数据库操作(1号使用)
	public void saveOrUpdateWalletCshSwitchJS(List<CheckWalletCashSwitchModel> list) {
		for(CheckWalletCashSwitchModel hrt:list){
			CheckWalletCashSwitchLogModel logModel = new CheckWalletCashSwitchLogModel();
			BeanUtils.copyProperties(hrt, logModel);

			if("0".equals(hrt.getWalletStatus())) {//说明执行的是关闭操作
				//次月表删除上月添加数据
				//实时表修改为关闭状态
				//日志表添加每个机构的关闭记录(这里需要查出来w表中操作了哪些机构与实时表中操作了哪些机构的综合)
				//日志表修改每个机构的之前的开启记录（添加一个结束时间）
				String unnoSql = " select distinct unno from ("+
						" select l.unno from check_walletcashswitch_w l"+
        				" where l.unno in (select s.unno from sys_unit s start with s.unno = '"+hrt.getUnno()+"'"+
        				" connect by prior s.unno  = s.upper_unit)"+
        				" and l.rebatetype = '"+hrt.getRebateType()+"'"+
        				" and l.createdate <= trunc(sysdate,'mm')"+
        				" and l.createdate >= add_months(trunc(sysdate,'mm'),-1)"+
        				" union all"+
        				" select a.unno from check_walletcashswitch a"+
        				" where a.unno in (select s.unno from sys_unit s start with s.unno = '"+hrt.getUnno()+"'"+
        				" connect by prior s.unno  = s.upper_unit)"+
        				" and a.rebatetype = '"+hrt.getRebateType()+"')";
        		List<Map<String, String>> unnoList = checkWalletCashSwitchDao.queryObjectsBySqlList(unnoSql, null, null, null);
				
				
				String sql2JS = " delete from check_walletcashswitch_w a"+
        				" where a.unno in (select t.unno from sys_unit t"+
        				" start with t.unno = '"+hrt.getUnno()+"'"+
        				" connect by prior t.unno  = t.upper_unit) and a.rebatetype = '"+hrt.getRebateType()+"'"+
        				" and a.createdate <= trunc(sysdate,'mm')"+
        				" and a.createdate >= add_months(trunc(sysdate,'mm'),-1)";
        		checkWalletCashSwitchWDao.executeSqlUpdate(sql2JS, null);//删除w表此机构含子代数据
				
        		String sql2JSnew = " update check_walletcashswitch a set a.walletstatus = '0',a.createdate = sysdate ,a.remark1 = '"+hrt.getRemark1()+"'"+
        				" where a.unno in (select t.unno from sys_unit t"+
        				" start with t.unno = '"+hrt.getUnno()+"'"+
        				" connect by prior t.unno  = t.upper_unit) and a.rebatetype = '"+hrt.getRebateType()+"'";
        		checkWalletCashSwitchDao.executeSqlUpdate(sql2JSnew, null);//修改实时此机构含子代数据均关闭
        		
        		for (Map<String, String> unno : unnoList) {
        			String sql2LogAdd = "insert into check_walletcashswitch_log (wid,unno,createdate,walletstatus,rebatetype,remark1)"+
        					" values (S_check_walletCashSwitch_Log.Nextval,"+
        					" '"+unno.get("UNNO")+"',sysdate,'0','"+hrt.getRebateType()+"','"+hrt.getRemark1()+"')";
        			checkWalletCashSwitchLogDao.executeUpdate(sql2LogAdd);//插入一条关闭信息的日志
        			
        			String sql2LogUpdate = " update check_walletcashswitch_log a set a.enddate = sysdate, a.remark1 = '"+hrt.getRemark1()+"'"+
        					" where a.unno = '"+unno.get("UNNO")+"'"+
        					" and a.rebatetype = '"+hrt.getRebateType()+"'"+
        					" and a.enddate is null"+
        					" and a.walletstatus = '1'";
        			checkWalletCashSwitchLogDao.executeSqlUpdate(sql2LogUpdate, null);//修改此机构活动，结束时间为空的日志均添加当前时间
				}
        		
        		
        		
			}else {//说明执行的是开启操作
				//
				String sqlfor1 = "select count(1) from check_walletcashswitch_w w where 1=1"+
						" and w.unno = '"+hrt.getUnno()+"'and w.rebatetype = '"+hrt.getRebateType()+"'"+
						" and w.createdate <= trunc(sysdate,'mm')"+
						" and w.createdate >= add_months(trunc(sysdate,'mm'),-1)";
				Integer isSqlfor1 = checkWalletCashSwitchWDao.querysqlCounts2(sqlfor1, null);
				
				Integer isUpdate = null;
				if(isSqlfor1 > 0) {//w有记录说明上月修改w表中的了
					String sql1JS = " update check_walletcashswitch_w a set a.walletstatus = '1',a.createdate = sysdate ,a.remark1 = '"+hrt.getRemark1()+"'"+
	        				" where a.unno = '"+hrt.getUnno()+"'and a.rebatetype = '"+hrt.getRebateType()+"'"+
	        				" and a.createdate <= trunc(sysdate,'mm')"+
							" and a.createdate >= add_months(trunc(sysdate,'mm'),-1)";
	        		isUpdate = checkWalletCashSwitchWDao.executeSqlUpdate(sql1JS, null);
				}else {//没有,就将实时表修改
					String sql1JS = " update check_walletcashswitch a set a.walletstatus = '1',a.createdate = sysdate ,a.remark1 = '"+hrt.getRemark1()+"'"+
	        				" where a.unno = '"+hrt.getUnno()+"'and a.rebatetype = '"+hrt.getRebateType()+"'";
	        		isUpdate = checkWalletCashSwitchDao.executeSqlUpdate(sql1JS, null);
				}
				
        		if(isUpdate < 1) {//未执行上面的更新操作，说明是新增,保存到实时表中
        			checkWalletCashSwitchDao.saveObject(hrt);
        		}
        		checkWalletCashSwitchLogDao.saveObject(logModel);//保存到日志表中一个无结束日期的记录
			}
			
		}
	}
	//执行数据库操作(使用)
	public void saveOrUpdateWalletCshSwitchJS2(List<CheckWalletCashSwitchModel> list) {
		for(CheckWalletCashSwitchModel hrt:list){
			
			CheckWalletCashSwitchLogModel logModel = new CheckWalletCashSwitchLogModel();
			BeanUtils.copyProperties(hrt, logModel);
			
			if("0".equals(hrt.getWalletStatus())) {//说明执行的是关闭操作
			//关闭操作----有(实时表存在一条开启的记录才能够进行关闭,说明日志表存在一条开启的记录)一种
			//       		 	----实时表中(修改此条记录状态为关闭)
			//			     	----日志表中(修改添加此条记录的结束日期,并且新增一条关闭记录)
				
        		String unnoSql = " select l.unno from check_walletcashswitch l"+
        				" where l.unno in (select s.unno from sys_unit s start with s.unno = '"+hrt.getUnno()+"'"+
        				" connect by prior s.unno  = s.upper_unit)"+
        				" and l.rebatetype = '"+hrt.getRebateType()+"'";
        		List<Map<String, String>> unnoList = checkWalletCashSwitchDao.queryObjectsBySqlList(unnoSql, null, null, null);
        		
        		String sql2JS = " update check_walletcashswitch a set a.walletstatus = '0',a.createdate = sysdate ,a.remark1 = '"+hrt.getRemark1()+"'"+
        				" where a.unno in (select t.unno from sys_unit t"+
        				" start with t.unno = '"+hrt.getUnno()+"'"+
        				" connect by prior t.unno  = t.upper_unit) and a.rebatetype = '"+hrt.getRebateType()+"'";
        		checkWalletCashSwitchDao.executeSqlUpdate(sql2JS, null);//修改实时
        		
        		
        		for (Map<String, String> unno : unnoList) {
        			String sql2LogAdd = "insert into check_walletcashswitch_log (wid,unno,createdate,walletstatus,rebatetype,remark1)"+
        					" values (S_check_walletCashSwitch_Log.Nextval,"+
        					" '"+unno.get("UNNO")+"',sysdate,'0','"+hrt.getRebateType()+"','"+hrt.getRemark1()+"')";
        			checkWalletCashSwitchLogDao.executeUpdate(sql2LogAdd);//插入一条关闭日志
        			
        			
        			String sql2LogUpdate = " update check_walletcashswitch_log a set a.enddate = sysdate, a.remark1 = '"+hrt.getRemark1()+"'"+
        					" where a.unno = '"+unno.get("UNNO")+"'"+
        					" and a.rebatetype = '"+hrt.getRebateType()+"'"+
        					" and a.enddate is null"+
        					" and a.walletstatus = '1'";
        			checkWalletCashSwitchLogDao.executeSqlUpdate(sql2LogUpdate, null);//修改日志
				}
        		
			}else {//说明执行的是开启操作
			//开启操作----有(关闭再开启/无记录新增开启)两种
			//					----实时表中(修改状态操作/新增操作)
			//					----日志表中(新增一条记录/新增一条记录【均无结束日期录入】)
				String sql1JS = " update check_walletcashswitch a set a.walletstatus = '1',a.createdate = sysdate ,a.remark1 = '"+hrt.getRemark1()+"'"+
        				" where a.unno = '"+hrt.getUnno()+"'and a.rebatetype = '"+hrt.getRebateType()+"'"+
        				" and a.walletstatus = '0'";
        		Integer isUpdate = checkWalletCashSwitchDao.executeSqlUpdate(sql1JS, null);
        		if(isUpdate < 1) {//未执行上面的更新操作，说明是新增
        			checkWalletCashSwitchDao.saveObject(hrt);
        		}
        		checkWalletCashSwitchLogDao.saveObject(logModel);//保存到日志表中一个无结束日期的记录
			}
			
		}
	}

	@Override
	public List<Map<String, Object>> exportCheckWalletCashSwitchLog(CheckWalletCashSwitchBean checkWalletCashSwitchBean,
			UserBean userSession) {
		String sql = "select a.unno,b.un_name,a.rebatetype,a.createdate,a.enddate,a.walletstatus,b.un_lvl " +
                "  from check_walletCashSwitch_log a, sys_unit b where a.unno = b.unno ";
        Map map=new HashMap();
        if("110000".equals(userSession.getUnNo())) {
        }else if(userSession.getUseLvl()==0) {
            sql += " and a.unno in (select unno from sys_unit where upper_unit=:unno1 or unno = :unno1) ";
            map.put("unno1",userSession.getUnNo());
        }

        // 机构号精确查询
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getUnno())) {
            sql += " and a.unno = :unno ";
            map.put("unno",checkWalletCashSwitchBean.getUnno());
        }

        // 机构名称精确查询
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getRemark1())) {
            sql += " and b.un_name = :remark1 ";
            map.put("remark1",checkWalletCashSwitchBean.getRemark1());
        }

        // 活动类型
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getRebateType()) && !"-1".equals(checkWalletCashSwitchBean.getRebateType())) {
            sql += " and a.rebatetype = :rebateType ";
            map.put("rebateType",checkWalletCashSwitchBean.getRebateType());
        }

        // 钱包状态
        if (StringUtils.isNotEmpty(checkWalletCashSwitchBean.getWalletStatus())) {
            sql += " and a.walletstatus = :walletstatus ";
            map.put("walletstatus",checkWalletCashSwitchBean.getWalletStatus());
        }
        List<Map<String, Object>> lst = checkWalletCashSwitchLogDao.executeSql2(sql,map);
		return formatToData(userSession, lst);
	}
	
	private List<Map<String, Object>> formatToData(UserBean userSession,List<Map<String, Object>> lst){
		List list = new ArrayList();
		for (Map<String, Object> map : lst) {
			if(map.get("ENDDATE")==null) {
				map.put("ENDDATE", "至今");
			}
			if(userSession.getUnitLvl().equals(map.get("UN_LVL"))) {
				map.put("UN_LVL", "本级");
			}else {
				map.put("UN_LVL", "下级");
			}
			if("0".equals(map.get("WALLETSTATUS"))) {
				map.put("WALLETSTATUS", "关");
			}else {
				map.put("WALLETSTATUS", "开");
			}
			list.add(map);
		}
		return list;
	}
}
