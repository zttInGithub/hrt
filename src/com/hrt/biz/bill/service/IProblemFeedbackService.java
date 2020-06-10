package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.model.ProblemFeedbackModel;
import com.hrt.biz.bill.entity.pagebean.ProblemFeedbackBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import java.util.List;
import java.util.Map;

/**************************
 * @description: IProblemFeedbackService
 * @author: xuegangliu
 * @date: 2019/3/14 13:33
 ***************************/
public interface IProblemFeedbackService {

    /**
     * 保存反馈信息
     * @param problemFeedbackModel
     * @return
     */
    Object saveProblemFeedback(ProblemFeedbackModel problemFeedbackModel);

    /**
     * 反馈是否存在
     * @param orderId
     * @return
     */
    List<ProblemFeedbackModel> getProblemFeedbackByOrderId(String orderId);

    /**
     * 收银台公众号反馈明细表
     * @param problemFeedbackBean
     * @param user
     * @return
     */
    DataGridBean listProblemFeedback(ProblemFeedbackBean problemFeedbackBean, UserBean user);

    /**
     * 收银台公众号反馈明细表导出
     * @param problemFeedbackBean
     * @param user
     * @return
     */
    List<Map<String, Object>> exportProblemFeedback(ProblemFeedbackBean problemFeedbackBean, UserBean user);
}
