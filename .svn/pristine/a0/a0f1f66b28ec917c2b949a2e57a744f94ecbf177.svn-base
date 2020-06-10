package com.hrt.biz.check.action;

import com.hrt.biz.check.entity.pagebean.PgCashbackSpecialBean;
import com.hrt.biz.check.service.PgCashbackSpecialService;
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

public class PgCashbackSpecialAction extends BaseAction implements ModelDriven<PgCashbackSpecialBean> {

	private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(PgCashbackSpecialAction.class);

    private PgCashbackSpecialBean pgCashbackSpecialBean =new PgCashbackSpecialBean();

    private PgCashbackSpecialService pgCashbackSpecialService;


    public PgCashbackSpecialService getPgCashbackSpecialService() {
		return pgCashbackSpecialService;
	}

	public void setPgCashbackSpecialService(PgCashbackSpecialService pgCashbackSpecialService) {
		this.pgCashbackSpecialService = pgCashbackSpecialService;
	}

	@Override
    public PgCashbackSpecialBean getModel() {
        return pgCashbackSpecialBean;
    }

    /**
     * 查询--定制活动返现数据报表
     */
	public void queryCustomizeActivityCashbackData(){
		Object userSession = super.getRequest().getSession().getAttribute("user");
		UserBean user=(UserBean)userSession;
		DataGridBean dgb = new DataGridBean();
		try {
		    dgb=pgCashbackSpecialService.queryCustomizeActivityCashbackData(pgCashbackSpecialBean,user);
		} catch (Exception e) {
		    log.info(e);
		}
		super.writeJson(dgb);
	}

    /**
     * 导出--定制活动返现数据报表
     */
    public void exportCustomizeActivityCashbackData(){
        JsonBean json = new JsonBean();
        List<Map<String, Object>> list = null;
        List<String[]>cotents = new ArrayList<String[]>();
        String titles[] = {"一代机构","一代机构名称","中心机构","中心机构名称","最终机构","最终机构名称","商户编号","商户名称",
                "手机号", "入网日期","交易金额","返利类型","返利金额","返利日期","返利次数"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if(userSession!=null){
                list = pgCashbackSpecialService.exportCustomizeActivityCashbackData(pgCashbackSpecialBean,(UserBean)userSession);
                if (list!=null&&list.size()>0) {
                    for (Map<String, Object>map  : list) {
                        String rowCoents[] = {
                                map.get("YIDAI")==null?"":map.get("YIDAI").toString(),
                                map.get("YIDAINAME")==null?"":map.get("YIDAINAME").toString(),
                                map.get("ZHONGXIN")==null?"":map.get("ZHONGXIN").toString(),
                                map.get("ZHONGXINNAME")==null?"":map.get("ZHONGXINNAME").toString(),
                                map.get("ZUIZHONG")==null?"":map.get("ZUIZHONG").toString(),
                                map.get("ZUIZHONGNAME")==null?"":map.get("ZUIZHONGNAME").toString(),
                                map.get("MID")==null?"":map.get("MID").toString(),
                                map.get("RNAME")==null?"":map.get("RNAME").toString(),
                                map.get("CONTACTPHONE")==null?"":map.get("CONTACTPHONE").toString(),
                                map.get("JOINCONFIRMDATE")==null?"":map.get("JOINCONFIRMDATE").toString(),
                                map.get("SUMMONEY")==null?"":map.get("SUMMONEY").toString(),
                                map.get("REBATETYPE")==null?"":map.get("REBATETYPE").toString(),
                                map.get("RETURNMONEY")==null?"":map.get("RETURNMONEY").toString(),
                                map.get("RETURNDAY")==null?"":map.get("RETURNDAY").toString(),
                                map.get("RETURNTIME")==null?"":map.get("RETURNTIME").toString(),
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "定制活动返现数据报表.csv";
                JxlOutExcelUtil.writeCSVFile(cotents, titles.length, getResponse(), excelName);
                json.setSuccess(true);
                json.setMsg("定制活动返现数据报表导出成功");
            }
        } catch (Exception e) {
            log.error("定制活动返现数据报表导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("定制活动返现数据报表导出");
            super.writeJson(json);
        }
    }
    
    /**
     * 查询--定制活动返现数据报表-活动类型列表
     */
	public void queryCustomizeActivityCashbackData_selectRebateType(){
		DataGridBean dgb = new DataGridBean();
		try {
		    dgb=pgCashbackSpecialService.queryCustomizeActivityCashbackData_selectRebateType();
		} catch (Exception e) {
		    log.info(e);
		}
		super.writeJson(dgb);
	}
	
}
