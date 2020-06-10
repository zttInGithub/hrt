package com.hrt.biz.bill.entity.model;

import java.io.Serializable;
import java.util.Date;

/**************************
 * @description: ProblemFeedbackModel 问题反馈表
 * @author: xuegangliu
 * @date: 2019/3/14 13:14
 ***************************/
public class ProblemFeedbackModel implements Serializable {
    private Integer probId; // 主键
    private String orderId; // 订单id
    private String mid; // 商户id
    private String trantype; // 交易类型
    private String amount; // 交易金额
    private String trantime; // 交易时间
    private Integer msg; // 反馈信息 1.提示商户收款存在异常 2.提示谨防刷单、投资等 3.无法选择花呗/信用卡支付 4.提示不支持信用卡，请选择储蓄卡
                                 // 5.提示交易异常 6.提示URL未注册 7.密码输入错误/操作失误 8.不想支付
    private String remark; // 反馈其它内容
    private Date createTime; // 创建时间

    public ProblemFeedbackModel() {
    }

    public Integer getProbId() {
        return probId;
    }

    public void setProbId(Integer probId) {
        this.probId = probId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTrantime() {
        return trantime;
    }

    public void setTrantime(String trantime) {
        this.trantime = trantime;
    }

    public Integer getMsg() {
        return msg;
    }

    public void setMsg(Integer msg) {
        this.msg = msg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
