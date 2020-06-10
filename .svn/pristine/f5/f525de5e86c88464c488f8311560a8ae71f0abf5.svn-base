package com.hrt.frame.action.sysadmin;

import com.alibaba.fastjson.JSONObject;
import com.hrt.frame.base.action.BaseAction;
import com.hrt.frame.entity.model.EnumModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.EnumBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.service.sysadmin.IEnumService;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * EnumAction
 * <p>
 * This is description
 * </p>
 *
 * @author xuegangliu 2020/01/16
 * @since 1.8
 **/
public class EnumAction extends BaseAction implements ModelDriven<EnumBean> {
    private static final Log log = LogFactory.getLog(EnumAction.class);
    private IEnumService enumService;

    public IEnumService getEnumService() {
        return enumService;
    }

    public void setEnumService(IEnumService enumService) {
        this.enumService = enumService;
    }

    private EnumBean enumBean=new EnumBean();

    public EnumBean getEnumBean() {
        return enumBean;
    }

    public void setEnumBean(EnumBean enumBean) {
        this.enumBean = enumBean;
    }

    @Override
    public EnumBean getModel() {
        return enumBean;
    }

    /**
     * 获取枚举表格
     */
    public void getEnumGridList(){
        super.writeJson(enumService.queryEunmGrid(enumBean));
    }

    /**
     * 获取枚举映射信息
     */
    public void getEnumNameMap(){
        Map map = enumService.getEnumNameList();
        Map result = new HashMap();
        result.put("enumMap",JSONObject.toJSONString(map));
        super.writeJson(result);
    }

    /**
     * 添加枚举信息
     */
    public void saveEnumInfo(){
        JsonBean jsonBean=new JsonBean();
        enumService.saveEnumModel(enumBean);
        jsonBean.setSuccess(true);
        jsonBean.setMsg("添加枚举信息成功!");
        super.writeJson(jsonBean);
    }

    /**
     * 删除单条枚举信息
     */
    public void removeEnumInfo(){
        JsonBean jsonBean=new JsonBean();
        EnumModel enumModel=new EnumModel();
        enumModel.setEnumId(enumBean.getEnumId());
        enumService.deleteEnumModelById(enumModel);
        jsonBean.setSuccess(true);
        jsonBean.setMsg("删除枚举信息成功!");
        super.writeJson(jsonBean);
    }

    /**
     * 获取枚举列表信息
     */
    public void getEnumInfoList(){
        DataGridBean dataGridBean=enumService.getEnumModelList(enumBean);
        super.writeJson(dataGridBean);
    }

    /**
     * 修改枚举信息状态
     */
    public void modifyEnumStatus(){
        JsonBean jsonBean=new JsonBean();
        boolean flag = enumService.updateEnumStatus(enumBean);
        if(flag){
            jsonBean.setMsg("枚举信息状态修改成功!");
            jsonBean.setSuccess(true);
        }else{
            jsonBean.setMsg("枚举信息状态修改失败!");
            jsonBean.setSuccess(false);
        }
        super.writeJson(jsonBean);
    }

    /**
     * 修改枚举信息
     */
    public void modifyEnumInfo(){
        JsonBean jsonBean=new JsonBean();
        boolean flag = enumService.updateEnumInfo(enumBean);
        if(flag){
            jsonBean.setMsg("枚举信息状态修改成功!");
            jsonBean.setSuccess(true);
        }else{
            jsonBean.setMsg("枚举信息状态修改失败!");
            jsonBean.setSuccess(false);
        }
        super.writeJson(jsonBean);
    }


}
