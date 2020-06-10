package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.action.ChangeTerRateAction;
import com.hrt.biz.bill.dao.IChangeTerRateDao;
import com.hrt.biz.bill.entity.model.ChangeTerRateModel;
import com.hrt.biz.bill.entity.pagebean.ChangeTerRateBean;
import com.hrt.biz.bill.service.IChangeTerRateService;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class ChangeTerRateServiceImpl implements IChangeTerRateService{
	
	private Log log = LogFactory.getLog(ChangeTerRateServiceImpl.class);
	private IChangeTerRateDao ChangeTerRateDao;

	public IChangeTerRateDao getChangeTerRateDao() {
		return ChangeTerRateDao;
	}

	public void setChangeTerRateDao(IChangeTerRateDao ChangeTerRateDao) {
		this.ChangeTerRateDao = ChangeTerRateDao;
	}

	@Override
	public DataGridBean queryChangeTerRate(ChangeTerRateBean bean, UserBean user) {
		DataGridBean dgb = new DataGridBean();
		String sql = "select a.* from BILL_CHANGETERRATE a where a.approveStatus!='D' ";
		if(bean.getApproveStatus()!=null&&!"".equals(bean.getApproveStatus())) {
			sql += " and a.approveStatus = '"+bean.getApproveStatus()+"'";
		}
		String sqlCount = "select count(1) from ("+sql+")";
		sql += " order by a.maintainDate desc";
		List<ChangeTerRateModel> list = ChangeTerRateDao.queryObjectsBySqlLists(sql, null, bean.getPage(), bean.getRows(), ChangeTerRateModel.class);
		Integer count = ChangeTerRateDao.querysqlCounts2(sqlCount, null);
		dgb.setRows(list);
		dgb.setTotal(count);
		return dgb;
	}

	@Override
	public Integer addChangeTerRate(ChangeTerRateBean bean, UserBean user) {
		String sqlCount = "select count(1) from bill_terminalinfo where sn>='"+bean.getSnStart()+"' and sn <='"+bean.getSnEnd()+"' and status in (1,3)";
		Integer count = ChangeTerRateDao.querysqlCounts2(sqlCount, null);
		if(count>0) {
			ChangeTerRateModel model = new ChangeTerRateModel();
			model.setSnStart(bean.getSnStart());
			model.setSnEnd(bean.getSnEnd());
			model.setRate(bean.getRate());
			model.setScanRate(bean.getScanRate());
			model.setSecondRate(bean.getSecondRate());
			model.setMaintainDate(new Date());
			model.setMaintainUser(user.getLoginName());
			model.setApproveStatus("W");
			model.setTotalNum(count);
			ChangeTerRateDao.saveObject(model);
			return 1;
		}
		return 0;
	}

	@Override
	public Integer updateChangeTerRateGo(ChangeTerRateBean bean, UserBean user) {
		ChangeTerRateModel model = ChangeTerRateDao.queryObjectByHql("from ChangeTerRateModel where bctrid=?", new Object[] {bean.getBctrid()});
		if(model!=null) {
			ChangeTerRateDao.executeUpdate("update bill_terminalinfo set rate="+model.getRate()+",scanrate="+model.getScanRate()+",secondrate="+model.getSecondRate()+" where sn>='"+model.getSnStart()+"' and sn <='"+model.getSnEnd()+"' and status in (1,3)");
			model.setApproveStatus("Y");
			model.setApproveUser(user.getLoginName());
			model.setApprovedate(new Date());
			ChangeTerRateDao.updateObject(model);
			return 1;
		}
		return 0;
	}

	@Override
	public Integer updateChangeTerRateBack(ChangeTerRateBean bean, UserBean user) {
		ChangeTerRateModel model = ChangeTerRateDao.queryObjectByHql("from ChangeTerRateModel where bctrid=?", new Object[] {bean.getBctrid()});
		if(model!=null) {
			model.setApproveStatus("K");
			model.setApproveUser(user.getLoginName());
			model.setApprovedate(new Date());
			ChangeTerRateDao.updateObject(model);
			return 1;
		}
		return 0;
	}

	@Override
	public Integer updateChangeAllTerRateGo(ChangeTerRateBean bean, UserBean user) {
		List<ChangeTerRateModel> list = ChangeTerRateDao.queryObjectsByHqlList("from ChangeTerRateModel where bctrid in ("+bean.getBctrids()+")", new Object[] {});
		for (ChangeTerRateModel model : list) {
			if(model!=null) {
				ChangeTerRateDao.executeUpdate("update bill_terminalinfo set rate="+model.getRate()+",scanrate="+model.getScanRate()+",secondrate="+model.getSecondRate()+" where sn>='"+model.getSnStart()+"' and sn <='"+model.getSnEnd()+"' and status in (1,3)");
				model.setApproveStatus("Y");
				model.setApproveUser(user.getLoginName());
				model.setApprovedate(new Date());
				ChangeTerRateDao.updateObject(model);
			}
		}
		return 1;
	}
	
	@Override
	public Integer updateChangeAllTerRateBack(ChangeTerRateBean bean, UserBean user) {
		List<ChangeTerRateModel> list = ChangeTerRateDao.queryObjectsByHqlList("from ChangeTerRateModel where bctrid in ("+bean.getBctrids()+")", new Object[] {});
		for (ChangeTerRateModel model : list) {
			if(model!=null) {
				model.setApproveStatus("K");
				model.setApproveUser(user.getLoginName());
				model.setApprovedate(new Date());
				ChangeTerRateDao.updateObject(model);
			}
		}
		return 1;
	}

    /**
     * 批量费率修改Excel导入
     * @param xlsfile 文件路径
     * @param name 文件名称
     * @param user 用户
     * @return
     */
    public synchronized Map addBatchChangeTerRate(String xlsfile, String name, UserBean user){
        Map resutMap = new HashMap();
        int sumCount = 0;
        int errCount = 0;
        File filename = new File(xlsfile);
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(filename));
            HSSFSheet sheet = workbook.getSheetAt(0);
            sumCount = sheet.getPhysicalNumberOfRows()-1;
            for(int i = 1; i <= sumCount; i++){
                ChangeTerRateModel model = new ChangeTerRateModel();
                HSSFRow row = sheet.getRow(i);
                HSSFCell cell = row.getCell((short)0);
                if(cell == null || cell.toString().trim().equals("")){
                    break;
                }else{
                    //SN起始
                    row.getCell((short)0).setCellType(Cell.CELL_TYPE_STRING);
                    String start = row.getCell((short)0).getStringCellValue().trim();
                    model.setSnStart(start);

                    //SN截止
                    row.getCell((short)1).setCellType(Cell.CELL_TYPE_STRING);
                    String end = row.getCell((short)1).getStringCellValue().trim();
                    model.setSnEnd(end);

                    //费率
                    row.getCell((short)2).setCellType(Cell.CELL_TYPE_STRING);
                    model.setRate(Double.parseDouble(row.getCell((short)2).getStringCellValue().trim()));

                    //扫码费率
                    row.getCell((short)3).setCellType(Cell.CELL_TYPE_STRING);
                    model.setScanRate(Double.parseDouble(row.getCell((short)3).getStringCellValue().trim()));

                    //手续费
                    row.getCell((short)4).setCellType(Cell.CELL_TYPE_STRING);
                    model.setSecondRate(Double.parseDouble(row.getCell((short)4).getStringCellValue().trim()));

                    model.setMaintainDate(new Date());
                    model.setMaintainUser(user.getLoginName());
                    model.setApproveStatus("W");
                    String sqlCount = "select count(1) from bill_terminalinfo where sn>='"+start+"' and sn <='"+end+"' and status in (1,3)";
                    Integer count = ChangeTerRateDao.querysqlCounts2(sqlCount, null);
                    model.setTotalNum(count);
                    if(count>0) {
                        ChangeTerRateDao.saveObject(model);
                    }else {
                        errCount++;
                    }
                }
            }
        } catch (IOException e) {
        	log.error("批量费率修改异常："+e);
            e.printStackTrace();
        }
        resutMap.put("errCount",errCount);
        resutMap.put("sumCount",sumCount);
        return resutMap;

    }

}
