package com.hrt.biz.check.action;

import com.hrt.biz.check.entity.pagebean.CheckCashbackDayBean;
import com.hrt.biz.check.service.CheckCashbackDayService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckCashbackDayAction extends BaseAction implements ModelDriven<CheckCashbackDayBean> {
    private static final Log log = LogFactory.getLog(CheckCashbackDayAction.class);

    private CheckCashbackDayService checkCashbackDayService;

    private CheckCashbackDayBean checkCashbackDayBean = new CheckCashbackDayBean();

    public CheckCashbackDayService getCheckCashbackDayService() {
        return checkCashbackDayService;
    }

    public void setCheckCashbackDayService(CheckCashbackDayService checkCashbackDayService) {
        this.checkCashbackDayService = checkCashbackDayService;
    }

    public CheckCashbackDayBean getCheckCashbackDayBean() {
        return checkCashbackDayBean;
    }

    public void setCheckCashbackDayBean(CheckCashbackDayBean checkCashbackDayBean) {
        this.checkCashbackDayBean = checkCashbackDayBean;
    }

    @Override
    public CheckCashbackDayBean getModel() {
        return checkCashbackDayBean;
    }

    /**
     * 查询日返现记录
     */
    public void queryCheckDeductionListInfo10171(){
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user=(UserBean)userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb=checkCashbackDayService.queryCheckCashbackDayInfo(checkCashbackDayBean,user);
        } catch (Exception e) {
            log.info(e);
        }
        super.writeJson(dgb);
    }

    /**
     * 导出日返现记录
     */
    public void export10171(){
        JsonBean json = new JsonBean();
        List<Map<String, Object>> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"一代机构","一代机构名称","二代机构","二代机构名称","最终机构","最终机构名称","归属分公司","商户编号",
                "SN号", "型号","出售日期","出售月","入网日期","入网月","返利金额1",
                "返利日期1", "返利金额2","返利日期2","返利金额3","返利日期3","交易金额","交易笔数","活动类型"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = checkCashbackDayService.export10171(checkCashbackDayBean,(UserBean)userSession);
                if (list!=null&&list.size()>0) {
                    for (Map<String, Object>map  : list) {
                        String rowCoents[] = {
                                map.get("UPPER1_UNIT")==null?"":map.get("UPPER1_UNIT").toString(),
                                map.get("UPPER1_NAME")==null?"":map.get("UPPER1_NAME").toString(),
                                map.get("UPPER2_UNIT")==null?"":map.get("UPPER2_UNIT").toString(),
                                map.get("UPPER2_NAME")==null?"":map.get("UPPER2_NAME").toString(),
                                map.get("UNNO")==null?"":map.get("UNNO").toString(),
                                map.get("UNNO_NAME")==null?"":map.get("UNNO_NAME").toString(),
                                map.get("UPPER_UNIT")==null?"":map.get("UPPER_UNIT").toString(),
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("SN")==null?"":map.get("SN").toString(),
                                map.get("SN_TYPE")==null?"":map.get("SN_TYPE").toString(),
                                map.get("KEYCONFIRMDATE")==null?"":map.get("KEYCONFIRMDATE").toString(),
                                map.get("KEYMONTH")==null?"":map.get("KEYMONTH").toString(),
                                map.get("USEDCONFIRMDATE")==null?"":map.get("USEDCONFIRMDATE").toString(),
                                map.get("USEMONTH")==null?"":map.get("USEMONTH").toString(),
                                map.get("REBATE1_AMT")==null?"":map.get("REBATE1_AMT").toString(),
                                map.get("REBATE1_MONTH")==null?"":map.get("REBATE1_MONTH").toString(),
                                map.get("REBATE2_AMT")==null?"":map.get("REBATE2_AMT").toString(),
                                map.get("REBATE2_MONTH")==null?"":map.get("REBATE2_MONTH").toString(),
                                map.get("REBATE3_AMT")==null?"":map.get("REBATE3_AMT").toString(),
                                map.get("REBATE3_MONTH")==null?"":map.get("REBATE3_MONTH").toString(),
                                map.get("TXNAMOUNT")==null?"":map.get("TXNAMOUNT").toString(),
                                map.get("TXNCOUNT")==null?"":map.get("TXNCOUNT").toString(),
                                map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "日返现记录报表.csv";
//                String sheetName = "日返现记录报表";
//                String [] cellFormatType = {"t","t","t","t","t","t","t","t","t"};
//                JxlOutExcelUtil.writeExcel(cotents, titles.length, getResponse(), sheetName, excelName+".xls",
//                        null);
                JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
                json.setSuccess(true);
                json.setMsg("日返现记录报表导出成功");
            }
        } catch (Exception e) {
            log.error("日返现记录报表导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("日返现记录报表");
            super.writeJson(json);
        }
    }
}
