package com.hrt.biz.check.service;

import com.hrt.biz.check.entity.model.CheckWalletCashSwitchLogModel;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchModel;
import com.hrt.biz.check.entity.model.CheckWalletCashSwitchWModel;
import com.hrt.biz.check.entity.pagebean.CheckWalletCashSwitchBean;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface CheckWalletCashSwitchService {

    /**
     * 单个开通或变更
     * @param checkWalletCashSwitchBean
     * @param user
     * @return
     */
    String validateWalletCashStatusInfo(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean user);

    /**
     * 可用为钱包的活动类型列表
     * @return
     */
    DataGridBean availableRebateTypeList(UserBean user);

    String getRebateTypeListByUser(UserBean user);
    /**
     * 机构综合钱包状态是否为开启
     * @param unno
     * @return
     */
    boolean hasBeforePursetypeStatus(String unno);

    /**
     * 下级代理机构获取
     * @param checkWalletCashSwitchBean
     * @param user
     * @return
     */
    DataGridBean subUnnoList(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean user);

    /**
     * 校验机构是否存在某活动
     * @param unLvl
     * @param unno
     * @param rebateType
     * @param status
     * @param flag 是否校验该机构该活动为开通状态
     * @return
     */
    String validateWalletCashStatus(Integer unLvl,String unno,String updateUnno,String rebateType,String status,boolean flag);

    /**
     * 修改钱包下月生效信息
     * @param checkWalletCashSwitchBean
     * @param userBean
     * @return
     */
    boolean updateWalletCashWStatus(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean);

    /**
     * 本级及下级代理活动钱包生效记录
     * @param checkWalletCashSwitchBean
     * @param userBean
     * @return
     */
    DataGridBean findAllWalletCashLog(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean);
    /**
     * 钱包实时记录展示
     * @param checkWalletCashSwitchBean
     * @param userBean
     * @return
     */
    DataGridBean findAllWalletCashJS(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean);


    /**
     * 下级代理分润差额钱包本月与下月状态显示
     * @param checkWalletCashSwitchBean
     * @param userBean
     * @return
     */
    DataGridBean findAllWalletCash(CheckWalletCashSwitchBean checkWalletCashSwitchBean, UserBean userBean);

    /**
     * 新增变更实时钱包状态
     * @param xlsfile
     * @param name
     * @param user
     * @return
     */
    String saveImportWalletCashSwitchXls(String xlsfile, String name, UserBean user) throws InvocationTargetException, IllegalAccessException;

    DataGridBean findAllWalletCashW(CheckWalletCashSwitchBean checkWalletCashSwitchBean);

    CheckWalletCashSwitchModel saveWalletCash(CheckWalletCashSwitchBean checkWalletCashSwitchBean)
            throws InvocationTargetException, IllegalAccessException;

    /**
     * 是否已经存在该机构该活动的钱包开关
     * @param checkWalletCashSwitchBean
     * @return
     */
    boolean hasWalletCashByUnnoAndRebateType(CheckWalletCashSwitchBean checkWalletCashSwitchBean);

    /**
     * 单条实时新增记录存在时,保存更新到log表中
     * @param checkWalletCashSwitchBean
     */
    void updateWalletCashW(CheckWalletCashSwitchBean checkWalletCashSwitchBean);

    CheckWalletCashSwitchWModel saveWalletCashW(CheckWalletCashSwitchBean checkWalletCashSwitchBean)
            throws InvocationTargetException, IllegalAccessException;

    CheckWalletCashSwitchLogModel saveWalletCashLog(CheckWalletCashSwitchBean checkWalletCashSwitchBean)
            throws InvocationTargetException, IllegalAccessException;
    /**
     * 结算批量导入
     * @param xlsfile
     * @param name
     * @param user
     * @return
     */
	String saveImportBatchWalletCashSwitchJS(String xlsfile, String name, UserBean user);
    /**
     * 代理分润差额钱包生效记录导出*/
	List<Map<String, Object>> exportCheckWalletCashSwitchLog(CheckWalletCashSwitchBean checkWalletCashSwitchBean,
			UserBean userSession);
}
