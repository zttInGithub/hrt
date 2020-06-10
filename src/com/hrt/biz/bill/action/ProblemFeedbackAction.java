package com.hrt.biz.bill.action;

import com.hrt.biz.bill.entity.pagebean.ProblemFeedbackBean;
import com.hrt.biz.bill.service.IProblemFeedbackService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**************************
 * @description: ProblemFeedbackAction
 * @author: xuegangliu
 * @date: 2019/3/28 9:37
 ***************************/
public class ProblemFeedbackAction extends BaseAction implements ModelDriven<ProblemFeedbackBean> {
    private static final Log log = LogFactory.getLog(ProblemFeedbackAction.class);

    private IProblemFeedbackService problemFeedbackService;
    private ProblemFeedbackBean problemFeedbackBean = new ProblemFeedbackBean();

    /**
     * 查询收银台公众号反馈明细表
     */
    public void listSYTDataGrid(){
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
            } else {
                // @author:xuegangliu-20190328 反馈报表展示
                dgb = problemFeedbackService.listProblemFeedback(problemFeedbackBean, ((UserBean)userSession));
            }
        } catch (Exception e) {
            log.error("分页查询收银台公众号反馈明细表信息异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 导出收银台公众号反馈明细表
     */
    public void exportSYTData(){
        JsonBean json = new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
                json.setSessionExpire(true);
            } else {
                // @author:xuegangliu-20190328 反馈报表导出
                List<Map<String, Object>> list = problemFeedbackService.exportProblemFeedback(problemFeedbackBean, ((UserBean)userSession));
                //商户编号	商户名称	归属机构	运营中心	交易金额	交易时间	商户选择失败原因
                List<String[]> excelList = new ArrayList<String[]>();
                String title [] = {"商户编号", "商户名称", "归属机构","归属机构名称", "运营中心", "运营中心名称","交易金额","交易时间","反馈日期","商户选择失败原因"};
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                excelList.add(title);
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = list.get(i);
                    String []rowContents ={
                            map.get("MID")==null?"":map.get("MID").toString(),
                            map.get("RNAME")==null?"":map.get("RNAME").toString(),
                            map.get("UNNO")==null?"":map.get("UNNO").toString(),
                            map.get("UN_NAME")==null?"":map.get("UN_NAME").toString(),
                            map.get("YUNYING")==null?"":map.get("YUNYING").toString(),
                            map.get("YUNYINGNAME")==null?"":map.get("YUNYINGNAME").toString(),
                            map.get("AMOUNT")==null?"":map.get("AMOUNT").toString(),
                            map.get("TRANTIME")==null?"":map.get("TRANTIME").toString(),
                            map.get("CREATETIME")==null?"":df.format((Date)map.get("CREATETIME")),
                            map.get("REMARK")==null?"":map.get("REMARK").toString(),
                    };
                    excelList.add(rowContents);
                }
                String excelName = "反馈明细表资料.csv";
                JxlOutExcelUtil.writeCSVFile(excelList, title.length, getResponse(), excelName);
                json.setSuccess(true);
                json.setMsg("导出反馈明细表Excel成功");
            }
        } catch (Exception e) {
            log.error("导出反馈明细表Excel：" + e);
        }
    }


    @Override
    public ProblemFeedbackBean getModel() {
        return problemFeedbackBean;
    }
    public IProblemFeedbackService getProblemFeedbackService() {
        return problemFeedbackService;
    }
    public void setProblemFeedbackService(IProblemFeedbackService problemFeedbackService) {
        this.problemFeedbackService = problemFeedbackService;
    }
}
