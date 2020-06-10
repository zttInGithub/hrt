package com.hrt.biz.bill.service.impl;

import com.hrt.biz.bill.dao.IProblemFeedbackDao;
import com.hrt.biz.bill.entity.model.ProblemFeedbackModel;
import com.hrt.biz.bill.entity.pagebean.ProblemFeedbackBean;
import com.hrt.biz.bill.service.IProblemFeedbackService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**************************
 * @description: ProblemFeedbackServiceImpl
 * @author: xuegangliu
 * @date: 2019/3/14 13:33
 ***************************/
public class ProblemFeedbackServiceImpl implements IProblemFeedbackService {

    private IProblemFeedbackDao problemFeedbackDao;


    @Override
    public Object saveProblemFeedback(ProblemFeedbackModel problemFeedbackModel) {
        return problemFeedbackDao.saveObject(problemFeedbackModel);
    }

    @Override
    public List<ProblemFeedbackModel> getProblemFeedbackByOrderId(String orderId){
        return problemFeedbackDao.queryObjectsByHqlList("from ProblemFeedbackModel where orderId=?",
                new Object[] {orderId}
                );
    }

    @Override
    public DataGridBean listProblemFeedback(ProblemFeedbackBean problemFeedbackBean, UserBean user) {
        DataGridBean dgb = new DataGridBean();
        String sql = "SELECT S.*, " +
                "       (SELECT SU.UN_NAME FROM  SYS_UNIT SU WHERE SU.UNNO=S.UNNO ) UN_NAME, " +
                "       (SELECT SU.UNNO FROM  SYS_UNIT SU WHERE SU.UN_LVL=1 START WITH SU.UNNO=S.UNNO CONNECT BY SU.UNNO=PRIOR SU.UPPER_UNIT) YUNYING, " +
                "       (SELECT SU.UN_NAME FROM  SYS_UNIT SU WHERE SU.UN_LVL=1 START WITH SU.UNNO=S.UNNO CONNECT BY SU.UNNO=PRIOR SU.UPPER_UNIT) YUNYINGNAME " +
                " FROM (" +
                "  SELECT PF.MID,PF.TRANTYPE,PF.AMOUNT,PF.TRANTIME,PF.CREATETIME,PF.PROBID, PF.REMARK,PF.MSG,"+
                "         (SELECT M.RNAME FROM  BILL_MERCHANTINFO M WHERE M.MID=PF.MID) RNAME," +
                "         (SELECT M.UNNO FROM  BILL_MERCHANTINFO M WHERE M.MID=PF.MID) UNNO " +
                "   FROM  BILL_PROBLEM_FEEDBACK PF " +
                ") S WHERE 1=1 ";

        // 1、筛选条件：商户编号、反馈日期 
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(problemFeedbackBean.getMid())){
            sql += " AND TRIM(S.MID) = TRIM(:mid) ";
            map.put("mid", problemFeedbackBean.getMid());
        }
        if(problemFeedbackBean.getCreateTime()!=null && !"".equals(problemFeedbackBean.getCreateTime())){
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            sql += " AND TO_CHAR(S.CREATETIME,'YYYYMMDD') = :createTime ";
            map.put("createTime", df.format(problemFeedbackBean.getCreateTime()));
        }
        String sqlCount = "SELECT COUNT(1) FROM ("+sql+")";
        sql += " ORDER BY S.CREATETIME DESC ";
        List<Map<String, Object>> list = problemFeedbackDao.queryObjectsBySqlList2(sql, map, problemFeedbackBean.getPage(), problemFeedbackBean.getRows());

        for(Map<String, Object> s:list){
            s.put("REMARK",getProbleInfo(null==s.get("MSG")?"":s.get("MSG").toString()+"",null==s.get("REMARK")?"":s.get("REMARK")+""));
        }
        Integer counts = problemFeedbackDao.querysqlCounts2(sqlCount, map);
        dgb.setRows(list);
        dgb.setTotal(counts);
        return dgb;
    }

    public String getProbleInfo(String type,String info){
        Map map = new HashMap();
//        反馈信息 1.提示商户收款存在异常 2.提示谨防刷单、投资等 3.无法选择花呗/信用卡支付 4.提示不支持信用卡，请选择储蓄卡 5.提示交易异常 6.提示URL未注册 7.密码输入错误/操作失误 8.不想支付
        map.put("1","提示商户收款存在异常");
        map.put("2","提示谨防刷单、投资等");
        map.put("3","无法选择花呗/信用卡支付");
        map.put("4","提示不支持信用卡，请选择储蓄卡");
        map.put("5","提示交易异常");
        map.put("6","提示URL未注册");
        map.put("7","密码输入错误/操作失误");
        map.put("8","不想支付");
        if(StringUtils.isNotEmpty(type) && null!=map.get(type)){
            if(StringUtils.isNotEmpty(info)) {
                return map.get(type) + "|" + info;
            }else{
                return map.get(type)+"";
            }
        }else{
            return info;
        }
    }

    @Override
    public List<Map<String, Object>> exportProblemFeedback(ProblemFeedbackBean problemFeedbackBean, UserBean user) {
        String sql = "SELECT S.*, " +
                "       (SELECT SU.UN_NAME FROM  SYS_UNIT SU WHERE SU.UNNO=S.UNNO ) UN_NAME, " +
                "       (SELECT SU.UNNO FROM  SYS_UNIT SU WHERE SU.UN_LVL=1 START WITH SU.UNNO=S.UNNO CONNECT BY SU.UNNO=PRIOR SU.UPPER_UNIT) YUNYING, " +
                "       (SELECT SU.UN_NAME FROM  SYS_UNIT SU WHERE SU.UN_LVL=1 START WITH SU.UNNO=S.UNNO CONNECT BY SU.UNNO=PRIOR SU.UPPER_UNIT) YUNYINGNAME " +
                " FROM (" +
                "  SELECT PF.MID,PF.TRANTYPE,PF.AMOUNT,PF.TRANTIME,PF.CREATETIME,PF.PROBID, PF.REMARK,PF.MSG,"+
                "         (SELECT M.RNAME FROM  BILL_MERCHANTINFO M WHERE M.MID=PF.MID) RNAME," +
                "         (SELECT M.UNNO FROM  BILL_MERCHANTINFO M WHERE M.MID=PF.MID) UNNO " +
                "   FROM  BILL_PROBLEM_FEEDBACK PF " +
                ") S WHERE 1=1 ";

        // 1、筛选条件：商户编号、反馈日期
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotEmpty(problemFeedbackBean.getMid())){
            sql += " AND TRIM(S.MID) = TRIM(:mid) ";
            map.put("mid", problemFeedbackBean.getMid());
        }
        if(problemFeedbackBean.getCreateTime()!=null && !"".equals(problemFeedbackBean.getCreateTime())){
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            sql += " AND TO_CHAR(S.CREATETIME,'YYYYMMDD') = :createTime ";
            map.put("createTime", df.format(problemFeedbackBean.getCreateTime()));
        }
        sql += " ORDER BY S.CREATETIME DESC ";
        List<Map<String, Object>> list = problemFeedbackDao.queryObjectsBySqlListMap2(sql, map);
        for(Map<String, Object> s:list){
            s.put("REMARK",getProbleInfo(null==s.get("MSG")?"":s.get("MSG").toString()+"",null==s.get("REMARK")?"":s.get("REMARK")+""));
        }
        return list;
    }

    public IProblemFeedbackDao getProblemFeedbackDao() {
        return problemFeedbackDao;
    }
    public void setProblemFeedbackDao(IProblemFeedbackDao problemFeedbackDao) {
        this.problemFeedbackDao = problemFeedbackDao;
    }
}
