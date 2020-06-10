package com.hrt.frame.service.sysadmin;

import com.hrt.frame.entity.model.EnumModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.EnumBean;

import java.util.Map;

public interface IEnumService {

    /**
     * 获取枚举表 grid表格数据
     * @param enumBean
     * @return
     */
    DataGridBean queryEunmGrid(EnumBean enumBean);

    /**
     * 添加新的枚举定义
     * @param enumBean
     */
    void saveEnumModel(EnumBean enumBean);

    /**
     * 删除单条枚举定义信息
     * @param enumModel
     * @return
     */
    boolean deleteEnumModelById(EnumModel enumModel);

    /**
     * 枚举列表获取
     * @param enumBean
     * @return
     */
    DataGridBean getEnumModelList(EnumBean enumBean);

    /**
     * 更新枚举信息状态
     * @param enumBean
     * @return
     */
    boolean updateEnumStatus(EnumBean enumBean);

    /**
     * 修改枚举表信息
     * @param enumBean
     * @return
     */
    boolean updateEnumInfo(EnumBean enumBean);

    /**
     * 枚举定义名称获取
     *      key=enum_type|enum_table|enum_column|enum_code|enum_value
     *      value=enum_name
     * @return
     */
    Map getEnumNameList();
}
