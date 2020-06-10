package com.hrt.biz.bill.service;

import com.hrt.biz.bill.entity.pagebean.BillTerminalSimBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import java.util.List;
import java.util.Map;

public interface IBillTerminalSimService {

    /**
     * 查询SIM卡管理grid列表
     * @param billTerminalSimBean
     * @param userBean
     * @return
     */
    DataGridBean queryBillTerminalSimGrid(BillTerminalSimBean billTerminalSimBean, UserBean userBean);

    /**
     * 导出SIM卡管理grid列表
     * @param billTerminalSimBean
     * @param userBean
     * @return
     */
    List<Map<String, Object>> exportBillTerminalSimGrid(BillTerminalSimBean billTerminalSimBean, UserBean userBean);

    /**
     * 修改SIM卡管理信息
     * @param billTerminalSimBean
     * @param userBean
     */
    boolean updateBillTerminalSim(BillTerminalSimBean billTerminalSimBean, UserBean userBean);

    /**
     * SIM卡管理信息批量导入修改处理
     * @param billTerminalSimBean
     * @param userBean 登录用户
     * @param xlsfile 导入文件
     * @return
     */
    List<Map> saveImportTermSimModifyXls(BillTerminalSimBean billTerminalSimBean, UserBean userBean,
                                String xlsfile);

    /**
     * SIM卡管理信息匹配导入导出处理
     * @param billTerminalSimBean
     * @param userBean
     * @param xlsfile
     * @return
     */
    List<Map<String, Object>> exportBillTerminalSimMatch(BillTerminalSimBean billTerminalSimBean, UserBean userBean,
                                                        String xlsfile);
}
