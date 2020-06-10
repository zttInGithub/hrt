package com.hrt.biz.bill.action;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hrt.util.ExcelServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.hrt.biz.bill.entity.pagebean.TerminalInfoBean;
import com.hrt.biz.bill.service.ITerminalInfoService;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.constant.Constant;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.JxlOutExcelUtil;
import com.hrt.util.UsePioOutExcel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 项目名称：HrtApp
 * 类名称：TerminalInfoAction
 * 类描述： 终端管理
 * 创建人：李超
 * 创建时间：2014-4-21 下午9:21:04
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class TerminalInfoAction extends BaseAction implements ModelDriven<TerminalInfoBean> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String termID;
    private String btids;
    private String unNO;
    private File upload;

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUnNO() {
        return unNO;
    }

    public void setUnNO(String unNO) {
        this.unNO = unNO;
    }

    public String getBtids() {
        return btids;
    }

    public void setBtids(String btids) {
        this.btids = btids;
    }

    private Log log = LogFactory.getLog(TerminalInfoAction.class);

    private TerminalInfoBean bean = new TerminalInfoBean();

    private ITerminalInfoService terminalInfoService;


    public String getTermID() {
        return termID;
    }

    public void setTermID(String termID) {
        this.termID = termID;
    }

    @Override
    public TerminalInfoBean getModel() {
        return bean;
    }

    public ITerminalInfoService getTerminalInfoService() {
        return terminalInfoService;
    }

    public void setTerminalInfoService(ITerminalInfoService terminalInfoService) {
        this.terminalInfoService = terminalInfoService;
    }

    /**
     * 修改
     */
    public void updateTerminalInfoUnno() {
        String status1 = super.getRequest().getParameter("status2");
        String bid1 = super.getRequest().getParameter("bids");
        bean.setBtID(Integer.parseInt(bid1));
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try {
                terminalInfoService.updateTerminalInfo(status1, bid1);
                json.setSuccess(true);
                json.setMsg("修改信息成功");
            } catch (Exception e) {
                log.error("修改信息异常" + e);
                json.setMsg("修改失败");
            }
        }

        super.writeJson(json);
    }

    /**
     * 得到代理商的简码
     */
    public void getUnitGodes() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        String unLvl = getRequest().getParameter("unLvl");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = terminalInfoService.getUnitGodes(unLvl, user);
        } catch (Exception e) {
            log.error("查询代理商简码异常：" + e);
        }
        super.writeJson(list);
    }

    /**
     * 得到代理商的简码(模糊查询)
     */
    public void getUnitGodesByQ() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        String unLvl = getRequest().getParameter("unLvl");
        String nameCode = super.getRequest().getParameter("q");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = terminalInfoService.getUnitGodesByQ(nameCode, unLvl, user);
        } catch (Exception e) {
            log.error("查询代理商简码(模糊查询)异常：" + e);
        }
        super.writeJson(list);
    }

    /**
     * 得到代理商的简码(运营中心&分公司一代&自营一代)
     */
    public void getUnitGodes3() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        String unLvl = getRequest().getParameter("unLvl");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = terminalInfoService.getUnitGodes3(unLvl, user);
        } catch (Exception e) {
            log.error("查询代理商简码异常：" + e);
        }
        super.writeJson(list);
    }

    /**
     * 得到代理商的简码
     */
    public void getUnitGodes2() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        String unLvl = getRequest().getParameter("unLvl");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = terminalInfoService.getUnitGodes2(unLvl, user);
        } catch (Exception e) {
            log.error("查询代理商简码异常：" + e);
        }
        super.writeJson(list);
    }

    /**
     * 生成终端号
     */
    public void createInfo() {
        String params = getRequest().getParameter("params");
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            //锁
            Integer result = terminalInfoService.updateM35SnFlag(1);
            if (result == 0) {
                return;
            }
            try {
                Integer success_num = terminalInfoService.addInfo(bean, (UserBean) userSession, params);
                if (success_num == 0) {
                    json.setSuccess(true);
                    json.setMsg("生成终端号成功");
                } else {
                    json.setSuccess(false);
                    json.setMsg("传统终端号已满，成功生成" + success_num + "个终端号");
                }
            } catch (Exception e) {
                log.error("生成终端号异常：" + e);
                json.setMsg("生成终端号失败");
            }
            //开锁
            terminalInfoService.updateM35SnFlag(2);
        }
        super.writeJson(json);
    }

    /**
     * 终端号合成确认查询
     */
    public void findConfirm() {
        DataGridBean dgb = new DataGridBean();
        if (bean.getUnNO() != null && !"".equals(bean.getUnNO())) {
            try {
                dgb = terminalInfoService.findConfirm(bean);
            } catch (Exception e) {
                log.error("终端号合成确认查询信息异常：" + e);
            }
        }
        super.writeJson(dgb);
    }

    /**
     * 终端号合成确认导出
     */
    public void exportConfirm() {
        //		String ids=getRequest().getParameter("ids");
        HttpServletResponse response = getResponse();
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try {
                HSSFWorkbook wb = terminalInfoService.exportConfirm(bean.getUnNO());
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + new String("终端号导出".getBytes("GBK"), "ISO-8859-1") + ".xls");
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
                json.setSuccess(true);
                json.setMsg("导出终端号Excel成功");
            } catch (Exception e) {
                log.error("导出终端号Excel异常：" + e);
                json.setMsg("导出终端号Excel失败");
            }
        }
        //super.writeJson(json);
    }

    /**
     * 终端号合成确认修改
     */
    public void editConfirm() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try {
                terminalInfoService.editConfirm(bean.getUnNO());
                json.setSuccess(true);
                json.setMsg("确认修改终端号成功");
            } catch (Exception e) {
                log.error("确认修改终端号异常：" + e);
                json.setMsg("确认修改终端号失败");
            }
        }
        super.writeJson(json);
    }

    /**
     * 终端号分配查询
     */
    public void findAllot() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.findAllot(bean, user);
        } catch (Exception e) {
            log.error("终端号分配查询信息异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 终端号分配修改
     */
    public void editAllot() {
        String params = getRequest().getParameter("btids");
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try {
                terminalInfoService.editAllot(params);
                json.setSuccess(true);
                json.setMsg("分配修改终端号成功");
            } catch (Exception e) {
                log.error("分配修改终端号异常：" + e);
                json.setMsg("分配修改终端号失败");
            }
        }
        super.writeJson(json);
    }

    /**
     * 批量修改终端号费率
     */
    public void editTerminalRateInfo() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        JsonBean json = new JsonBean();

        //20191219-ztt添加自定义政策费率保存
        String specialFlag = super.getRequest().getParameter("specialFlag");
        if ("1".equals(specialFlag)) {
            if (btids == null || "".equals(btids)) {
                json.setSuccess(false);
                json.setMsg("请正确填写信息");
                super.writeJson(json);
                return;
            }

            //判断是否存在已使用终端
            boolean specialFlag1 = terminalInfoService.queryTerminalStatusInfo(bean, btids, user);
            if (specialFlag1) {
                json.setSuccess(false);
                json.setMsg("存在已使用的终端");
                super.writeJson(json);
                return;
            }

            //特殊机构限制操作
            if (btids != null && !"".equals(btids)) {
                boolean flag1 = terminalInfoService.queryTerminalLimitUnno(Constant.LIMIT_UNNO, btids);
                if (flag1) {
                    json.setSuccess(false);
                    json.setMsg("暂不允许调整费率，敬请谅解！");
                    super.writeJson(json);
                    return;
                }
            }

            //判断修改终端是否属于特殊费率配置活动与中心
            String specialTerFlag = terminalInfoService.judgeIsSpecialRateTer(bean, btids, user);
            if (specialTerFlag != null) {
                json.setSuccess(false);
                json.setMsg(specialTerFlag);
                super.writeJson(json);
                return;
            }

            //执行修改操作
            String updateInfo = terminalInfoService.editSpecialRateTerminalRateInfo(bean, btids, user);
            if ("修改成功".equals(updateInfo)) {
                json.setSuccess(true);
                json.setMsg("修改费率成功");
                super.writeJson(json);
                return;
            }
            json.setSuccess(false);
            json.setMsg("修改费率失败");
            super.writeJson(json);
            return;
        }

        try {
            //特殊机构限制操作
            if (btids != null && !"".equals(btids)) {
                boolean flag1 = terminalInfoService.queryTerminalLimitUnno(Constant.LIMIT_UNNO, btids);
                if (flag1) {
                    json.setSuccess(false);
                    json.setMsg("暂不允许调整费率，敬请谅解！");
                    super.writeJson(json);
                    return;
                }
            }
            //if(bean.getRate()!=null&&!"".equals(bean.getRate())&&bean.getSecondRate()!=null&&!"".equals(bean.getSecondRate())&&btids!=null&&!"".equals(btids)){
            // @author:lxg-20190613 b11023单独修改费率0.52-0.72
            //			if(("110000".equals(user.getUnNo()) || bean.getKeyContext()!=null&&!"".equals(bean.getKeyContext()))
            if ((Constant.YIMIFU_UNNO.equals(user.getUnNo()) || bean.getKeyContext() != null && !"".equals(bean.getKeyContext())
                    //2019/10/16添加
                    || bean.getTermIDStart() != null || !"".equals(bean.getTermIDStart()) ||
                    bean.getTermIDEnd() != null || !"".equals(bean.getTermIDEnd()))
                    && btids != null && !"".equals(btids)) {
                //是否存在已使用的终端
                boolean flag1 = terminalInfoService.queryTerminalStatusInfo(bean, btids, user);
                if (flag1) {
                    json.setSuccess(false);
                    json.setMsg("存在已使用的终端");
                } else {
                    //判断需要排除  包括某些特定活动的终端不允许调整
                    boolean flag2 = terminalInfoService.queryTerminalRebateTypeInfoPlus(bean, btids, user);
                    if (flag2) {
                        json.setSuccess(false);
                        json.setMsg("暂不允许调整费率，敬请谅解！");
                    } else {
                        if (StringUtils.isEmpty(bean.getKeyContext())) {
                            BigDecimal r1 = new BigDecimal(bean.getTermIDStart() + "");
                            String errMsg = checkb11023Rate(r1, Integer.parseInt(bean.getTermIDEnd()), user.getUnNo());
                            if (StringUtils.isNotEmpty(errMsg)) {
                                json.setSuccess(false);
                                json.setMsg(errMsg);
                                super.writeJson(json);
                                return;
                            }
                            String rate = r1.divide(new BigDecimal(100)) + "";
                            String poundage = bean.getTermIDEnd();
                            //添加拼接亿米付机构标志在位置3处（普通机构这个标识的是代表活动0或1）
                            //2019/10/16  @author:YQ  添加判断机构号是否是b11023
                            if (Constant.YIMIFU_UNNO.equals(user.getUnNo())) {
                                bean.setKeyContext(rate + "-" + poundage + "-b11023");
                            } else {
                                bean.setKeyContext(rate + "-" + poundage + "-0");
                            }
                        }
                        String flag = terminalInfoService.editTerminalRateInfo(bean, btids, user);
                        if (flag.contains("不匹配")) {
                            json.setSuccess(false);
                            json.setMsg("设备类型与费率模板不匹配，请核实！");
                        } else {
                            json.setSuccess(true);
                            json.setMsg("修改成功");
                        }
                    }
                }
            } else {
                json.setSuccess(false);
                json.setMsg("请正确填写信息");
            }
        } catch (Exception e) {
            log.error("终端号使用查询信息异常：" + e);
            json.setSuccess(false);
            json.setMsg("修改失败");
        }
        super.writeJson(json);
    }

    /**
     * b11023机构费率单独校验,2019/11/13 YQ新增极速版校验
     *
     * @param rate 费率
     * @param m    手续费
     * @return
     */
    private String checkb11023Rate(BigDecimal rate, Integer m, String unno) {
        // b11023修改费率限制0.52-0.6
        if (unno.equals("b11023")) {
            if (new BigDecimal("0.52").compareTo(rate) > 0 || new BigDecimal("0.6").compareTo(rate) < 0) {
                return "请修改费率为(0.52-0.6)";
            }
        } else {//极速版校验
            if (new BigDecimal("0.50").compareTo(rate) > 0 || new BigDecimal("0.6").compareTo(rate) < 0) {
                return "请修改费率为(0.5-0.6)";
            }

        }
        // b11023修改手续费限制0-3
        if (m < 0 || m > 3) {
            return "请修改手续费为(0-3)";
        }
        return null;
    }

    /**
     * 批量修改终端号费率(收银台)
     */
    public void editTerminalRateInfo2() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        JsonBean json = new JsonBean();

        //新增自定义费率修改（扫码1000上下、花呗）-ztt20200318
        String specialFlag = super.getRequest().getParameter("specialFlag");
        if ("1".equals(specialFlag)) {
            if (btids == null || "".equals(btids)) {
                json.setSuccess(false);
                json.setMsg("请正确填写信息");
                super.writeJson(json);
                return;
            }

            //判断是否存在已使用终端
            boolean specialFlag1 = terminalInfoService.queryTerminalStatusInfo(bean, btids, user);
            if (specialFlag1) {
                json.setSuccess(false);
                json.setMsg("存在已使用的终端");
                super.writeJson(json);
                return;
            }
            //判断是否存在不允许修改的活动设备
//            boolean flag2 = terminalInfoService.queryTerminalRebateTypeInfo(bean, btids, user);
//            if (flag2) {
//                json.setSuccess(false);
//                json.setMsg("暂不允许调整费率，敬请谅解！");
//                super.writeJson(json);
//                return;
//            }
            //判断修改终端是否属于特殊费率配置活动与中心
            String specialTerFlag = terminalInfoService.judgeIsSpecialRateTer(bean, btids, user);
            if (specialTerFlag != null) {
                json.setSuccess(false);
                json.setMsg(specialTerFlag);
                super.writeJson(json);
                return;
            }

            //执行修改操作
            String updateInfo = terminalInfoService.editSpecialRateTerminalRateInfo(bean, btids, user);
            if ("修改成功".equals(updateInfo)) {
                json.setSuccess(true);
                json.setMsg("修改费率成功");
                super.writeJson(json);
                return;
            }
            json.setSuccess(false);
            json.setMsg("修改费率失败");
            super.writeJson(json);
            return;
        }


        try {
            if (bean.getKeyContext() != null && !"".equals(bean.getKeyContext()) && btids != null && !"".equals(btids)) {
                //是否存在已使用的终端
                boolean flag1 = terminalInfoService.queryTerminalStatusInfo(bean, btids, user);
                if (flag1) {
                    json.setSuccess(false);
                    json.setMsg("存在已使用的终端");
                } else {
                    boolean flag2 = terminalInfoService.queryTerminalRebateTypeInfo(bean, btids, user);
                    if (flag2) {
                        json.setSuccess(false);
                        json.setMsg("暂不允许调整费率，敬请谅解！");
                    } else {
                        boolean flag = terminalInfoService.editTerminalRateInfo2(bean, btids, user);
                        if (flag) {
                            json.setSuccess(true);
                            json.setMsg("修改成功");
                        } else {
                            json.setSuccess(false);
                            json.setMsg("修改失败");
                        }
                    }
                }
            } else {
                json.setSuccess(false);
                json.setMsg("请正确填写信息");
            }
        } catch (Exception e) {
            log.error("终端号使用查询信息异常：" + e);
            json.setSuccess(false);
            json.setMsg("修改失败");
        }
        super.writeJson(json);
    }

    /**
     * 查询rate/secondrate
     */
    public void queryTerminalRateInfo() {
        DataGridBean dgd = new DataGridBean();
        try {
            String type = super.getRequest().getParameter("type");
            dgd = terminalInfoService.queryTerminalRateInfo(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * 查询rate/secondrate(排除活动90)
     */
    public void queryTerminalRatebyActiveInfo() {
        DataGridBean dgd = new DataGridBean();
        try {
            String type = super.getRequest().getParameter("type");
            String active = "1";
            dgd = terminalInfoService.queryTerminalRatebyActiveInfo(type, active);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * 查询rate/secondrate(将活动minfo2也查出来，minfo2代表活动分类1是90活动)
     */
    public void queryTerminalRateIncludeActiveInfo() {
        DataGridBean dgd = new DataGridBean();
        try {
            String type = super.getRequest().getParameter("type");
            dgd = terminalInfoService.queryTerminalRateIncludeActiveInfo(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * 终端号使用查询
     */
    public void findUse() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.findUse(bean, user);
        } catch (Exception e) {
            log.error("终端号使用查询信息异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 终端号费率调整
     */
    public void findUseUpdate() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            if (user.getUnitLvl() != 0) {
                bean.setUnNO(user.getUnNo());
            }
            if (bean.getType() != null || !"".equals(bean.getType()))
                dgb = terminalInfoService.findUse1(bean, user);
        } catch (Exception e) {
            log.error("终端号使用查询信息异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 终端号费率调整(收银台)
     */
    public void findUseUpdate2() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            if (user.getUnitLvl() != 0) {
                bean.setUnNO(user.getUnNo());
            }
            dgb = terminalInfoService.findUse2(bean, user);
        } catch (Exception e) {
            log.error("终端号使用查询信息异常：" + e);
        }
        super.writeJson(dgb);
    }

    public void searchTerminalInfo() {
        DataGridBean dgd = new DataGridBean();
        try {
            String mid = super.getRequest().getParameter("mid");
            dgd = terminalInfoService.searchTerminalInfo(mid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * 传统终端号查询
     */
    public void searchTerminalInfoUnno() {
        DataGridBean dgd = new DataGridBean();
        String unno = super.getRequest().getParameter("unno");
        try {
            dgd = terminalInfoService.searchTerminalInfoUnno(unno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * m35终端号查询
     */
    public void searchM35TerminalInfoUnno() {
        DataGridBean dgd = new DataGridBean();
        String unno = super.getRequest().getParameter("unno");
        String sn = super.getRequest().getParameter("sn");
        String mid = super.getRequest().getParameter("mid");
        try {
            dgd = terminalInfoService.searchM35TerminalInfoUnno(unno, sn, mid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * （北分交行）终端号查询
     */
    public void searchTerminalInfoUnno2() {
        DataGridBean dgd = new DataGridBean();
        String unno = super.getRequest().getParameter("unno");
        try {
            dgd = terminalInfoService.searchTerminalInfoUnno2(unno);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * 查询全部
     */
    public void listTerminalInfoUnno() {
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.queryAgentSales(bean);
        } catch (Exception e) {
            log.error("分页查询业务员信息异常：" + e);
        }

        super.writeJson(dgb);
    }


    /**
     * 导入M35--->SN号以及机构
     */
    public void addM35SnInfo() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) {
            json.setSessionExpire(true);
        }
        //锁
        Integer result = terminalInfoService.updateM35SnFlag(1);
        if (result == 0) {
            return;
        }
        //获取上传的商户类型
        String merchantType = ServletActionContext.getRequest().getParameter("merchantType");
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("importHrtFile");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload");
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
        String xlsfile = folderPath + "/" + fileName;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                List<Map<String, String>> list = null;
                if (merchantType.equals("1")) {
                    //微商户设备导入
                    list = terminalInfoService.saveM35SnInfo(xlsfile, fileName, user);
                } else if (merchantType.equals("2")) {
                    //智能设备导入
                    list = terminalInfoService.saveMerSnInfo(xlsfile, fileName, user);
                } else {
                    //分销系统微商户设备导入
                    list = terminalInfoService.saveAgentSnInfo(xlsfile, fileName, user);
                }
                if (list.size() > 0) {
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "机构号", "备注"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {
                                order.get("sn"),
                                order.get("unno"),
                                order.get("remark")
                        };
                        excellist.add(rowContents);
                    }

                    String[] cellFormatType = {"t", "t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 3, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                } else {
                    json.setSuccess(true);
                    json.setMsg("记录更新成功!");
                    super.writeJson(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("文件更新失败!");
            super.writeJson(json);
        }
        //开锁
        terminalInfoService.updateM35SnFlag(2);
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 导入M35--->进销存（区间导入,代理单--退货）
     */
    public void updateM35SnInfoPUR2() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10506Name");
        String ORDERID1 = fileName.split("-")[0];
        String ORDERID = ServletActionContext.getRequest().getParameter("ORDERID");
        String ORDERMETHOD = ServletActionContext.getRequest().getParameter("ORDERMETHOD");
        String ORDERTYPE = ServletActionContext.getRequest().getParameter("ORDERTYPE");
        String UNNO = ServletActionContext.getRequest().getParameter("UNNO");
        String PDID = ServletActionContext.getRequest().getParameter("PDID");
        String MACHINEMODEL = ServletActionContext.getRequest().getParameter("MACHINEMODEL");
        String snType = ServletActionContext.getRequest().getParameter("SNTYPE");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10506");
        if (ORDERID == null || "".equals(ORDERID) || PDID == null) return;
        if (!ORDERID.equals(ORDERID1)) {
            json.setSuccess(false);
            json.setMsg("文件订单号不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10506");
        String xlsfile = folderPath + "/" + fileName;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                List<Map<String, String>> list = null;
                list = terminalInfoService.updateM35SnInfoPur2(PDID, xlsfile, fileName, ORDERID, MACHINEMODEL, snType, user, ORDERMETHOD, ORDERTYPE, UNNO);
                if (list != null && list.size() > 0) {
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "备注"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {order.get("sn"), order.get("unno"), order.get("remark")};
                        excellist.add(rowContents);
                    }
                    String[] cellFormatType = {"t", "t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 3, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                } else {
                    json.setSuccess(true);
                    json.setMsg("记录更新成功!");
                    super.writeJson(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("文件更新失败!");
            super.writeJson(json);
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 导入M35--->进销存--->(代理单--退货)
     */
    public void updateM35SnInfoPUR3() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10505Name");
        String ORDERID1 = fileName.split("-")[0];
        String ORDERID = ServletActionContext.getRequest().getParameter("ORDERID");
        String ORDERMETHOD = ServletActionContext.getRequest().getParameter("ORDERMETHOD");
        String ORDERTYPE = ServletActionContext.getRequest().getParameter("ORDERTYPE");
        String UNNO = ServletActionContext.getRequest().getParameter("UNNO");
        String PDID = ServletActionContext.getRequest().getParameter("PDID");
        String MACHINEMODEL = ServletActionContext.getRequest().getParameter("MACHINEMODEL");
        String snType = ServletActionContext.getRequest().getParameter("SNTYPE");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10505");
        if (ORDERID == null || "".equals(ORDERID) || PDID == null) return;
        if (!ORDERID.equals(ORDERID1)) {
            json.setSuccess(false);
            json.setMsg("文件订单号不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10505");
        String xlsfile = folderPath + "/" + fileName;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                List<Map<String, String>> list = null;
                list = terminalInfoService.updateM35SnInfoPur3(PDID, xlsfile, fileName, ORDERID, MACHINEMODEL, snType, user, ORDERMETHOD, ORDERTYPE, UNNO);
                if (list != null && list.size() > 0) {
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "备注"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {order.get("sn"), order.get("unno"), order.get("remark")};
                        excellist.add(rowContents);
                    }
                    String[] cellFormatType = {"t", "t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 3, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                } else {
                    json.setSuccess(true);
                    json.setMsg("记录更新成功!");
                    super.writeJson(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("文件更新失败!");
            super.writeJson(json);
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 导入M35--->进销存(厂商单)-明细入库模板
     */
    public void dowloadM35SnInfoPURDetailTemp(){
        try {
            ExcelServiceImpl.download(getResponse(),"sn_detail_tmp.xls","订单号xx-sn明细导入.xls");
        } catch (Exception e) {
            log.info("批量解绑导入模板下载失败:"+e);
        }
    }

    /**
     * 导入M35--->进销存(厂商单)
     */
    public void addM35SnInfoPUR() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10432Name");
        String ORDERID1 = fileName.split("-")[0];
        String ORDERID = ServletActionContext.getRequest().getParameter("ORDERID");
        String ORDERMETHOD = ServletActionContext.getRequest().getParameter("ORDERMETHOD");
        String ORDERTYPE = ServletActionContext.getRequest().getParameter("ORDERTYPE");
        String PDID = ServletActionContext.getRequest().getParameter("PDID");
        String MACHINEMODEL = ServletActionContext.getRequest().getParameter("MACHINEMODEL");
        String snType = ServletActionContext.getRequest().getParameter("SNTYPE");
        String storage = ServletActionContext.getRequest().getParameter("storage").trim();
        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10432");
        if (ORDERID == null || "".equals(ORDERID) || PDID == null) return;
        if (!ORDERID.equals(ORDERID1)) {
            json.setSuccess(false);
            json.setMsg("文件订单号不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10432");
        String xlsfile = folderPath + "/" + fileName;
        Integer result = terminalInfoService.updateM35SnFlag(1);
        if (result == 0) return;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                List<Map<String, String>> list = null;
                list = terminalInfoService.saveM35SnInfoPur(PDID, xlsfile, fileName, ORDERID, MACHINEMODEL, snType, user, storage, ORDERMETHOD, ORDERTYPE);
                if (list != null && list.size() > 0) {
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "备注"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {order.get("sn"), order.get("remark")};
                        excellist.add(rowContents);
                    }
                    String[] cellFormatType = {"t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 2, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                } else {
                    json.setSuccess(true);
                    json.setMsg("记录更新成功!");
                    super.writeJson(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("文件更新失败!");
            super.writeJson(json);
        }
        //开锁
        terminalInfoService.updateM35SnFlag(2);
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 导入M35--->进销存（区间导入,厂商单）
     */
    public void addM35SnInfoPUR2() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10436Name");
        String ORDERID1 = fileName.split("-")[0];
        String ORDERID = ServletActionContext.getRequest().getParameter("ORDERID");
        String ORDERMETHOD = ServletActionContext.getRequest().getParameter("ORDERMETHOD");
        String ORDERTYPE = ServletActionContext.getRequest().getParameter("ORDERTYPE");
        String PDID = ServletActionContext.getRequest().getParameter("PDID");
        String MACHINEMODEL = ServletActionContext.getRequest().getParameter("MACHINEMODEL");
        String snType = ServletActionContext.getRequest().getParameter("SNTYPE");
        String storage = ServletActionContext.getRequest().getParameter("storage").trim();
        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10436");
        if (ORDERID == null || "".equals(ORDERID) || PDID == null) return;
        if (!ORDERID.equals(ORDERID1)) {
            json.setSuccess(false);
            json.setMsg("文件订单号不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10436");
        String xlsfile = folderPath + "/" + fileName;
        Integer result = terminalInfoService.updateM35SnFlag(1);
        if (result == 0) return;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                List<Map<String, String>> list = null;
                list = terminalInfoService.saveM35SnInfoPur2(PDID, xlsfile, fileName, ORDERID, MACHINEMODEL, snType, user, storage, ORDERMETHOD, ORDERTYPE);
                if (list != null && list.size() > 0) {
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "备注"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {order.get("sn"), order.get("remark")};
                        excellist.add(rowContents);
                    }
                    String[] cellFormatType = {"t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 2, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                } else {
                    json.setSuccess(true);
                    json.setMsg("记录更新成功!");
                    super.writeJson(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("文件更新失败!");
            super.writeJson(json);
        }
        //开锁
        terminalInfoService.updateM35SnFlag(2);
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 库存调拨--->进销存
     */
    public void updateM35SnInfoPUR() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10499Name");
        String out = fileName.split("-")[0];
        String in = fileName.split("-")[1];
        String psid = ServletActionContext.getRequest().getParameter("psid");
        String outStorage = ServletActionContext.getRequest().getParameter("outStorage");
        String inStorage = ServletActionContext.getRequest().getParameter("inStorage");
        String storageMachineModel = ServletActionContext.getRequest().getParameter("storageMachineModel");
        String storageMachineNum = ServletActionContext.getRequest().getParameter("storageMachineNum");
        String loadStorageNum = ServletActionContext.getRequest().getParameter("loadStorageNum");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10499");
        if (storageMachineNum == null || "".equals(storageMachineNum) || loadStorageNum == null || "".equals(loadStorageNum))
            return;
        if ((!outStorage.equals(out)) || (!inStorage.equals(in))) {
            json.setSuccess(false);
            json.setMsg("库存调拨出入库不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10499");
        String xlsfile = folderPath + "/" + fileName;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                List<Map<String, String>> list = null;

                list = terminalInfoService.updateM35SnInfoPur(xlsfile, fileName, user, psid, outStorage, inStorage, storageMachineModel, Integer.parseInt(storageMachineNum), Integer.parseInt(loadStorageNum));

                if (list != null && list.size() > 0) {
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "备注"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {order.get("sn"), order.get("remark")};
                        excellist.add(rowContents);
                    }
                    String[] cellFormatType = {"t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 2, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                } else {
                    json.setSuccess(true);
                    json.setMsg("库存调拨成功!");
                    super.writeJson(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("库存调拨失败!");
            super.writeJson(json);
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 借样单--->进销存
     */
    public void updateM35ForLend() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10492Name");
        String storageID = fileName.split("-")[0];
        String storageID2 = ServletActionContext.getRequest().getParameter("storageID");
        String storageUnno = ServletActionContext.getRequest().getParameter("storageUnno");
        String storageMachineModel = ServletActionContext.getRequest().getParameter("storageMachineModel");
        String psid = ServletActionContext.getRequest().getParameter("psid");
        String loadStorageNum = ServletActionContext.getRequest().getParameter("loadStorageNum");
        String storageMachineNum = ServletActionContext.getRequest().getParameter("storageMachineNum");

        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10492");

        if (!storageID2.equals(storageID)) {
            json.setSuccess(false);
            json.setMsg("借样单号不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10492");
        String xlsfile = folderPath + "/" + fileName;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                Integer i = terminalInfoService.updateM35ForLend(xlsfile, fileName, user, storageUnno, storageMachineModel, loadStorageNum, storageMachineNum, psid, storageID);
                //借出数量大于0，则新建借样单
                if (i > 0) {
                    json.setSuccess(true);
                    json.setMsg("借出设备成功");
                } else if (i == -1) {
                    json.setSuccess(false);
                    json.setMsg("借出设备失败,导入数量大于待借出数量!");
                } else if (i == 0) {
                    json.setSuccess(false);
                    json.setMsg("借出设备失败,导入SN不存在!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("新增借样单失败!");
            super.writeJson(json);
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
        super.writeJson(json);
    }

    /**
     * 盘盈盘亏--->进销存
     */
    public void updateM35ForInventory() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10488Name");
        String storageID = fileName.split("-")[0];
        String storageType = ServletActionContext.getRequest().getParameter("storageType");
        String storageMachineModel = ServletActionContext.getRequest().getParameter("storageMachineModel");
        String storageID2 = ServletActionContext.getRequest().getParameter("storageID");
        String psid = ServletActionContext.getRequest().getParameter("psid");
        String loadStorageNum = ServletActionContext.getRequest().getParameter("loadStorageNum");
        String storageMachineNum = ServletActionContext.getRequest().getParameter("storageMachineNum");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10488");

        if (!storageID2.equals(storageID)) {
            json.setSuccess(false);
            json.setMsg("盘盈盘亏单号不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10488");
        String xlsfile = folderPath + "/" + fileName;
        Integer result = terminalInfoService.updateM35SnFlag(1);
        if (result == 0) return;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                Integer i = 0;
                if ("2".equals(storageType)) {
                    //盘盈
                    i = terminalInfoService.updateM35ForAdd(xlsfile, fileName, user, storageMachineModel, loadStorageNum, storageMachineNum, psid);
                } else if ("3".equals(storageType)) {
                    //盘亏
                    i = terminalInfoService.updateM35ForRed(xlsfile, fileName, user, storageMachineModel, loadStorageNum, storageMachineNum, psid);
                }
                //盘盈盘亏数量大于0
                if (i > 0) {
                    json.setSuccess(true);
                    json.setMsg("盘盈盘亏成功");
                } else if (i == -1) {
                    json.setSuccess(false);
                    json.setMsg("盘盈盘亏失败,导入数量大于待盘盈盘亏数量!");
                } else if (i == 0) {
                    json.setSuccess(false);
                    json.setMsg("盘盈盘亏失败,导入信息有误!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("盘盈盘亏失败!");
            super.writeJson(json);
        }
        //开锁
        terminalInfoService.updateM35SnFlag(2);
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
        super.writeJson(json);
    }

    /**
     * 厂商单退货
     */
    public void updateM35ForReturn() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) json.setSessionExpire(true);
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("file10488Name");
        String orderID = fileName.split("-")[0];
        String pdlid = ServletActionContext.getRequest().getParameter("PDLID");
        String machineModel = ServletActionContext.getRequest().getParameter("MACHINEMODEL");
        String orderID2 = ServletActionContext.getRequest().getParameter("ORDERID");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload_10488");

        if (!orderID2.equals(orderID)) {
            json.setSuccess(false);
            json.setMsg("采购单号不一致!");
            super.writeJson(json);
            return;
        }
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload_10488");
        String xlsfile = folderPath + "/" + fileName;
        try {
            if (xlsfile.length() > 0 && xlsfile != null) {
                Integer i = 0;
                i = terminalInfoService.updateM35ForReturn(xlsfile, fileName, user, pdlid, machineModel);
                //盘盈盘亏数量大于0
                if (i > 0) {
                    json.setSuccess(true);
                    json.setMsg("退货成功");
                } else if (i == -1) {
                    json.setSuccess(false);
                    json.setMsg("退货失败,导入数量大于待盘盈盘亏数量!");
                } else if (i == 0) {
                    json.setSuccess(false);
                    json.setMsg("退货失败,导入信息有误!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
            json.setMsg("退货失败!");
            super.writeJson(json);
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
        super.writeJson(json);
    }

    /**
     * 代理商终端导出查询
     */
    public void findExportList() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        if (bean.getUnNO() != null && !"".endsWith(bean.getUnNO())) {
            try {
                dgb = terminalInfoService.queryExportList(bean, user);
            } catch (Exception e) {
                log.error("查询终端信息异常：" + e);
            }
        }
        super.writeJson(dgb);
    }

    /**
     * 代理商终端导出
     */
    public void emportTidInfo() {
        HttpServletResponse response = getResponse();
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            if (bean.getUnNO() != null && !"".endsWith(bean.getUnNO())) {
                try {
                    HSSFWorkbook wb = terminalInfoService.exportTidInfo(bean);
                    //terminalInfoService.updateExportTidInfo(bean);
                    response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                    response.setHeader("Content-Disposition", "attachment; filename=" + new String("终端号导出".getBytes("GBK"), "ISO-8859-1") + ".xls");
                    OutputStream ouputStream = response.getOutputStream();
                    wb.write(ouputStream);
                    ouputStream.flush();
                    ouputStream.close();
                    json.setSuccess(true);
                    json.setMsg("导出终端号Excel成功");
                } catch (Exception e) {
                    log.error("导出终端号Excel异常：" + e);
                    json.setMsg("导出终端号Excel失败");
                }
            }
        }
    }

    /**
     * 终端分配查询
     */
    public void searchTerminalInfo2() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.searchTerminalInfo2(bean, user);
        } catch (Exception e) {
            log.error("终端查询异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 终端分配
     */
    public void gaveTerminal() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        try {
            String errMsg = terminalInfoService.gaveTerminalInfo(bean, user);
            if (StringUtils.isNotEmpty(errMsg)) {
                json.setMsg(errMsg);
                json.setSuccess(false);
            } else {
                json.setMsg("分配成功！");
                json.setSuccess(true);
            }
        } catch (Exception e) {
            log.error("分配异常：" + e);
            json.setMsg("分配异常！");
            json.setSuccess(false);
        }
        super.writeJson(json);
    }


    /*
     * searchBackTerminalInfo
     */
    public void searchBackTerminalInfo() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.searchBackTerminalListInfo(bean, user);
        } catch (Exception e) {
            log.error("终端查询异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * SN回拨
     */
    public void backTerminal() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        try {
            Integer i = 0;
            synchronized (terminalInfoService) {
                i = terminalInfoService.updateBackTerminalInfo(bean, user);
            }
            if (i == 1) {
                json.setMsg("回拨成功！");
                json.setSuccess(true);
            } else {
                json.setMsg("回拨失败，请确认SN/TID是否存在！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("回拨异常：" + e);
            json.setMsg("回拨异常！");
            json.setSuccess(false);
        }
        super.writeJson(json);
    }

    /**
     * 查询出可删除的SN终端
     */
    public void findNoUseSnList() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.findNoUseSnData(bean, user);
        } catch (Exception e) {
            log.error("查询出可删除的SN终端异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 删除SN终端
     */
    public void deleteSnTerminalInfo() {
        JsonBean json = new JsonBean();
        try {
            terminalInfoService.deleteSnTerminalInfo(bean);
            json.setSuccess(true);
            json.setMsg("删除成功！");
        } catch (Exception e) {
            log.error("删除SN终端异常：" + e);
            json.setSuccess(false);
            json.setMsg("删除异常！");
        }
        super.writeJson(json);
    }

    /**
     * 微商户SN号导入审核(查询status=3的)查询
     */
    public void checkAddSn() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.checkAddSn(bean, user);
        } catch (Exception e) {
            log.error("微商户SN号导入审核查询异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 微商户SN号审核通过
     */
    public void throughAllSn() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        JsonBean json = new JsonBean();
        try {
            if (btids != null && !"".equals(btids)) {
                terminalInfoService.throughAllSn(btids, user);
                json.setSuccess(true);
                json.setMsg("审核成功！");
            }
        } catch (Exception e) {
            log.error("微商户SN号审核通过异常：" + e);
            json.setSuccess(false);
            json.setMsg("审核异常！");
        }
        super.writeJson(json);
    }

    /**
     * 微商户SN号审核退回
     */
    public void rejectAllSn() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        JsonBean json = new JsonBean();
        try {
            if (btids != null && !"".equals(btids)) {
                terminalInfoService.rejectAllSn(btids, user);
                json.setSuccess(true);
                json.setMsg("退回成功！");
            }
        } catch (Exception e) {
            log.error("微商户SN号审核退回异常：" + e);
            json.setSuccess(false);
            json.setMsg("退回异常！");
        }
        super.writeJson(json);
    }

    /**
     * 微商户SN号导入审核(查询status=3的)查询
     */
    public void queryUpdateAddSn() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.queryUpdateAddSn(bean, user);
        } catch (Exception e) {
            log.error("微商户SN号导入审核查询异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 微商户修改状态
     */
    public void updateAddSn() {
        JsonBean json = new JsonBean();
        try {
            terminalInfoService.updateAddSn(bean);
            json.setSuccess(true);
            json.setMsg("修改成功！");
        } catch (Exception e) {
            log.error("微商户修改状态异常：" + e);
            json.setSuccess(false);
            json.setMsg("修改异常！");
        }
        super.writeJson(json);
    }

    /**
     * 微商户SN号审核通过2
     */
    public void throughSn() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        JsonBean json = new JsonBean();
        try {
            terminalInfoService.throughSn(bean, user);
            json.setSuccess(true);
            json.setMsg("审核成功！");
        } catch (Exception e) {
            log.error("微商户SN号审核通过异常：" + e);
            json.setSuccess(false);
            json.setMsg("审核异常！");
        }
        super.writeJson(json);
    }

    /**
     * 微商户SN号审核退回2
     */
    public void rejectSn() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        JsonBean json = new JsonBean();
        try {
            terminalInfoService.rejectSn(bean, user);
            json.setSuccess(true);
            json.setMsg("退回成功！");
        } catch (Exception e) {
            log.error("微商户SN号审核退回异常：" + e);
            json.setSuccess(false);
            json.setMsg("退回异常！");
        }
        super.writeJson(json);
    }

    /**
     * 进销存系统出库
     *
     * @author tl
     */
    @SuppressWarnings("all")
    public void updateTerminalInfoJXC_chuku() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) {
            json.setSessionExpire(true);
        }
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("equipmentFile10433");
        String orderID = ServletActionContext.getRequest().getParameter("ORDERID");
        String pdlid = ServletActionContext.getRequest().getParameter("PDLID");
        String NEW_UNNO = ServletActionContext.getRequest().getParameter("DELIVERUNNO");
        String NEW_REBATETYPE = ServletActionContext.getRequest().getParameter("REBATETYPE");
        double NEW_RATE = Double.parseDouble(ServletActionContext.getRequest().getParameter("RATE"));
        double SCANRATE = Double.parseDouble(ServletActionContext.getRequest().getParameter("SCANRATE"));
        double SECONRATE = Double.parseDouble(ServletActionContext.getRequest().getParameter("SECONDRATE"));
        Double SCANRATEUP=null;
        if(StringUtils.isNotEmpty(ServletActionContext.getRequest().getParameter("SCANRATEUP"))) {
            SCANRATEUP = Double.parseDouble(ServletActionContext.getRequest().getParameter("SCANRATEUP"));
        }
        Double HUABEIRATE=null;
        if(StringUtils.isNotEmpty(ServletActionContext.getRequest().getParameter("HUABEIRATE"))){
            HUABEIRATE = Double.parseDouble(ServletActionContext.getRequest().getParameter("HUABEIRATE"));
        }
        String DEPOSITAMT=null;
        if(StringUtils.isNotEmpty(ServletActionContext.getRequest().getParameter("DEPOSITAMT"))){
            DEPOSITAMT = ServletActionContext.getRequest().getParameter("DEPOSITAMT");
        }
        String basePath = ServletActionContext.getRequest().getRealPath("/upload");
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
        String xlsfile = folderPath + "/" + fileName;
        //接受返回的List
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            System.out.println(destFile.length());
            if (xlsfile.length() > 0 && xlsfile != null) {
                list = terminalInfoService.updateTerminalInfoJXC_chuku(xlsfile, user, fileName, orderID, pdlid, NEW_UNNO, NEW_REBATETYPE, NEW_RATE, SCANRATE, SECONRATE,SCANRATEUP,HUABEIRATE,DEPOSITAMT);
                if (list.size() == 0) {
                    json.setSuccess(true);
                    json.setMsg("文件导入成功");
                    super.writeJson(json);
                } else {
                    //导出持久化失败的字段的excel（SN,原UNNO,原返利类型(REBATETYPE)）
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "原UNNO", "错误通知"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {
                                order.get("sn"),
                                order.get("old_unno"),
                                order.get("text")
                        };
                        excellist.add(rowContents);
                    }
                    String[] cellFormatType = {"t", "t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 3, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("文件为空文件！");
                super.writeJson(json);
            }
        } catch (Exception e) {
            log.error(e);
            json.setSuccess(false);
            json.setMsg("文件导入失败！");
            super.writeJson(json);
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 未使用设备转移
     *
     * @author dx
     */
    @SuppressWarnings("all")
    public void updateTerminalInfoUET() {
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        if (user == null) {
            json.setSessionExpire(true);
        }
        //把文件保存到服务器目录下
        String fileName = ServletActionContext.getRequest().getParameter("unusedEquipmentFile");
        String basePath = ServletActionContext.getRequest().getRealPath("/upload");
        File dir = new File(basePath);
        dir.mkdir();
        // 使用UUID做为文件名，以解决重名的问题
        String path = basePath + "/" + fileName;
        File destFile = new File(path);
        upload.renameTo(destFile);
        String folderPath = ServletActionContext.getRequest().getRealPath("/upload");
        String xlsfile = folderPath + "/" + fileName;
        //接受返回的List
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            System.out.println(destFile.length());
            if (xlsfile.length() > 0 && xlsfile != null) {
                list = terminalInfoService.updateTerminalInfoUET(xlsfile, user, fileName);
                if (list.size() == 0) {
                    json.setSuccess(true);
                    json.setMsg("文件导入成功");
                    super.writeJson(json);
                } else {
                    //导出持久化失败的字段的excel（SN,原UNNO,原返利类型(REBATETYPE)）
                    String excelName = "导入失败记录";
                    String[] title = {"SN", "原UNNO", "原返利类型", "错误通知"};
                    List excellist = new ArrayList();
                    excellist.add(title);
                    for (int rowId = 0; rowId < list.size(); rowId++) {
                        Map<String, String> order = list.get(rowId);
                        String[] rowContents = {
                                order.get("sn"),
                                order.get("old_unno"),
                                order.get("old_rebatetype"),
                                order.get("text")
                        };
                        excellist.add(rowContents);
                    }
                    String[] cellFormatType = {"t", "t", "t", "t"};
                    UsePioOutExcel.writeExcel(excellist, 4, super.getResponse(), excelName, excelName + ".xls", cellFormatType);
                }
            } else {
                json.setSuccess(false);
                json.setMsg("文件为空文件！");
                super.writeJson(json);
            }
        } catch (Exception e) {
            log.error(e);
            json.setSuccess(false);
            json.setMsg("文件导入失败！");
            super.writeJson(json);
        }
        ExcelServiceImpl.deleteUploadFile(folderPath,path);
    }

    /**
     * 进销存的机型库存
     */
    public void listTerminalNumByMachineModel() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        DataGridBean dgb = new DataGridBean();
        if (userSession == null) {
        } else {
            try {
                dgb = terminalInfoService.listTerminalNumByMachineModel(bean);
            } catch (Exception e) {
                log.error("查询进销存机型库存异常：" + e);
            }
        }
        super.writeJson(dgb);
    }

    /**
     * 查询bill_merterminalinfo
     */
    public void findMerterminalInfo() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        DataGridBean dgb = new DataGridBean();
        if (userSession == null) {
        } else {
            try {
                dgb = terminalInfoService.findMerterminalInfo(bean, (UserBean) userSession);
            } catch (Exception e) {
                log.error("查询merterminalinfo信息异常：" + e);
            }
        }
        super.writeJson(dgb);
    }

    /**
     * 新增bill_merterminalinfo
     */
    public void addMerterminalInfo() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        JsonBean json = new JsonBean();
        if (userSession == null) {
        } else {
            try {
                Integer i = terminalInfoService.addMerterminalInfo(bean, (UserBean) userSession);
                if (i == 1) {
                    json.setSuccess(true);
                    json.setMsg("添加成功");
                } else {
                    json.setSuccess(false);
                    json.setMsg("已存在SN");
                }
            } catch (Exception e) {
                log.error("添加merterminalinfo信息异常：" + e);
                json.setSuccess(false);
                json.setMsg("添加失败");
            }
        }
        super.writeJson(json);
    }

    /**
     * 库房报表
     */
    public void queryTerminalStorage() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        DataGridBean dgb = new DataGridBean();
        if (userSession == null) {
        } else {
            try {
                dgb = terminalInfoService.queryTerminalStorage(bean, (UserBean) userSession);
            } catch (Exception e) {
                log.error("查询库房报表信息异常：" + e);
            }
        }
        super.writeJson(dgb);
    }

    /**
     * 库房报表导出
     */
    public void exportTerminalStorage() {
        HttpServletResponse response = getResponse();
        JsonBean json = new JsonBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        //session失效
        if (userSession == null) {
            json.setSessionExpire(true);
        } else {
            try {
                HSSFWorkbook wb = terminalInfoService.exportTerminalStorage(bean);
                response.setContentType("application/vnd.ms-excel;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + new String("库存信息".getBytes("GBK"), "ISO-8859-1") + ".xls");
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
                json.setSuccess(true);
                json.setMsg("库房报表导出Excel成功");
            } catch (Exception e) {
                log.error("库房报表导出Excel异常：" + e);
                json.setMsg("库房报表导出Excel失败");
            }
        }
    }

    /**
     * 活动配置查询（注册商户活动类型统计报表）
     */
    public void getListActivities() {
        DataGridBean dgb = new DataGridBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            //session失效
            if (userSession == null) {
            } else {
                dgb = terminalInfoService.getListActivities();
            }
        } catch (Exception e) {
            log.error("查询活动类型异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 注册商户活动类型统计报表---查询展示
     */
    public void queryRegisteredMerchantActivities() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        UserBean user = (UserBean) userSession;
        DataGridBean dgb = new DataGridBean();
        try {
            dgb = terminalInfoService.queryRegisteredMerchantActivities(bean, user);
        } catch (Exception e) {
            log.error("注册商户活动类型统计报表异常：" + e);
        }
        super.writeJson(dgb);
    }

    /**
     * 注册商户活动类型统计报表---导出
     */
    public void exportRegisteredMerchantActivities() {
        JsonBean json = new JsonBean();
        List<Map<String, Object>> list = null;
        List<String[]> cotents = new ArrayList<String[]>();
        String titles[] = {"活动类型", "运营中心机构编号", "运营中心名称", "数量", "类别"};
        cotents.add(titles);
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession != null) {
                list = terminalInfoService.exportRegisteredMerchantActivities(bean, (UserBean) userSession);
                if (list != null && list.size() > 0) {
                    for (Map<String, Object> map : list) {
                        String rowCoents[] = {
                                map.get("REBATETYPE") == null ? "" : map.get("REBATETYPE").toString(),
                                map.get("FIRTUNNO") == null ? "" : map.get("FIRTUNNO").toString(),
                                map.get("FIRSTUNNONAME") == null ? "" : map.get("FIRSTUNNONAME").toString(),
                                map.get("TOTALCOUNT") == null ? "" : map.get("TOTALCOUNT").toString(),
                                map.get("COUNTTYPE") == null ? "" : map.get("COUNTTYPE").toString(),
                        };
                        cotents.add(rowCoents);
                    }
                }
                String excelName = "注册商户活动类型统计资料";
                String sheetName = "注册商户活动类型统计资料";
                String[] cellFormatType = {"t", "t", "t", "t", "t"};
                JxlOutExcelUtil.writeExcel(cotents, 5, getResponse(), sheetName, excelName + ".xls", cellFormatType);
                json.setSuccess(true);
                json.setMsg("注册商户活动类型统计资料导出成功");
            }
        } catch (Exception e) {
            log.error("注册商户活动类型统计资料导出异常：" + e);
            json.setSuccess(false);
            json.setMsg("注册商户活动类型统计资料导出失败");
            super.writeJson(json);
        }
    }


    /**
     * 查询极速版费率
     */
    public void queryTerminalRateFeiLv() {
        DataGridBean dgd = new DataGridBean();
        try {
            dgd = terminalInfoService.queryTerminalRateFeiLv();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * 查询极速版手续费
     */
    public void queryTerminalRateSXUFei() {
        DataGridBean dgd = new DataGridBean();
        try {
            String type1 = super.getRequest().getParameter("rebateType");
            String type = Double.parseDouble(type1) / 10000 + "";
            dgd = terminalInfoService.queryTerminalRateSXUFei(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.writeJson(dgd);
    }

    /**
     * 判断是否为自定义政策终端机构
     */
    public void judgeTerminalActivtyInfo() {
        JsonBean json = new JsonBean();
        try {
            Object userSession = super.getRequest().getSession().getAttribute("user");
            if (userSession == null) {
                json.setSessionExpire(true);
            } else {
                String btids = super.getRequest().getParameter("btids");
                //20200311添加限制活动
                String isActivety = super.getRequest().getParameter("isActivety");

                UserBean userUnno = (UserBean) userSession;
                //判断是不是自定义政策的登录用户
                String specialRateConfiuge = terminalInfoService.judgeIsSpecialRateUnno(isActivety, btids, userUnno);
                //String specialRateConfiuge = "121000"+",93";
                if (specialRateConfiuge == null) {
                    json.setSuccess(false);
                    json.setMsg("非自定义机构");
                } else {
                    json.setSuccess(true);
                    json.setMsg(specialRateConfiuge);
                }
            }
        } catch (Exception e) {
            log.error("判断设备类型异常：" + e);
            json.setSessionExpire(true);
        }
        super.writeJson(json);
    }

    /**
     * 特殊费率详细信息查询
     */
    public void querySpecialRateDetail() {
        Object userSession = super.getRequest().getSession().getAttribute("user");
        String limitunno = getRequest().getParameter("limitunno");
        String limitrebatetype = getRequest().getParameter("limitrebatetype");
        Map map = null;
        try {
            map = terminalInfoService.querySpecialRateDetail(limitunno, limitrebatetype, (UserBean) userSession);
        } catch (Exception e) {
            log.error("查询特殊费率信息异常：" + e);
        }
        super.writeJson(map);
    }

    /**
     * 特殊费率详细信息查询---押金金额
     */
    public void querySpecialDepoist() {
        DataGridBean dgd = new DataGridBean();
        Object userSession = super.getRequest().getSession().getAttribute("user");
        String limitunno = getRequest().getParameter("limitunno");
        String limitrebatetype = getRequest().getParameter("limitrebatetype");
        dgd = terminalInfoService.querySpecialDepoist(limitunno, limitrebatetype);
        try {
        } catch (Exception e) {
            log.error("查询押金金额信息异常：" + e);
        }
        super.writeJson(dgd);
    }

    /**
     * 设备是否使用查询
     */
    public void queryTermIsUseBySns(){
        JsonBean json = new JsonBean();
        String sns = bean.getSn();
        List<Map<String, Object>> listMap=new ArrayList<Map<String, Object>>();
        try {
            if(StringUtils.isNotEmpty(sns)){
                listMap= terminalInfoService.queryTermIsUse(sns);
                if(listMap.size()>0){
                    json.setSuccess(true);
                    json.setMsg("查询成功");
                    json.setObj(listMap);
                }else{
                    json.setMsg("未查询到记录");
                }
            }
        } catch (Exception e) {
            json.setMsg("查询异常");
            log.error("查询异常：" + e);
        }

        super.writeJson(json);
    }
}
