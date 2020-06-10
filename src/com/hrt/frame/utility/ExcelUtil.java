package com.hrt.frame.utility;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;

import com.hrt.frame.entity.pagebean.UnitBean;

public class ExcelUtil {
	
	long hang = 0;
	
	/**
	 * 
	 * @param sname  sheet的名字
	 * @param data   数据集合[{MENU_ID=1, SRC=, CREATE_USER=superadmin, TEXTS=工作台}]
	 * @param excelHeader  字段名[MENU_ID, TEXTS, SRC, CREATE_USER]
	 * @param headMap	字段中文名{SRC=菜单链接, TEXTS=菜单名称, MENU_ID=菜单主键, CREATE_USER=创建人}
	 * @return
	 */
	public static HSSFWorkbook export(String sname ,List data, List excelHeader,Map<String, Object> headMap) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sname);
		HSSFRow row = sheet.createRow((int) 0);
		
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); 
		font.setFontHeightInPoints((short) 10);//设置字体大小
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		style.setWrapText(true);
		
		style.setFont(font);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		HSSFCellStyle style2 = wb.createCellStyle();
		
		style2.setWrapText(true);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		for (int i = 0; i < excelHeader.size(); i++) {
			HSSFCell cell = row.createCell(i);
			row.setHeightInPoints(20);
			cell.setCellValue(headMap.get(excelHeader.get(i)).toString());
			cell.setCellStyle(style);
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, 5213);
		}

		for (int i = 0; i < data.size(); i++) {
			row = sheet.createRow(i + 1);
			row.setHeightInPoints(15);
			Map<String, Object> map = (Map<String, Object>) data.get(i);
			for (int j = 0; j < excelHeader.size(); j++) {
				HSSFCell cell1 = row.createCell(j);
				cell1.setCellStyle(style2);
				if(map.get(excelHeader.get(j))!=null){
					cell1.setCellValue(map.get(excelHeader.get(j)).toString());
				}else{
					cell1.setCellValue("");
				}
				
			}
		}
		return wb;
	}
	
	/**
	 * 导出各级代理资料
	 * @return
	 */
	public  HSSFWorkbook exportUnno(String sname ,List<UnitBean> data) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sname);
		HSSFRow row = sheet.createRow((int) 0);
		
		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); 
		font.setFontHeightInPoints((short) 10);//设置字体大小
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		style.setWrapText(true);
		
		style.setFont(font);
		
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		HSSFCellStyle style2 = wb.createCellStyle();
		
		style2.setWrapText(true);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		HSSFCell cell = row.createCell(0);
		row.setHeightInPoints(20);
		cell.setCellValue("机构名称");
		cell.setCellStyle(style);
		sheet.autoSizeColumn(0);
		sheet.setColumnWidth(0, 15000);
		
		HSSFCell cell1 = row.createCell(1);
		row.setHeightInPoints(20);
		cell1.setCellValue("机构编号");
		cell1.setCellStyle(style);
		sheet.autoSizeColumn(1);
		sheet.setColumnWidth(1, 5213);
		
		HSSFCell cell2 = row.createCell(2);
		row.setHeightInPoints(20);
		cell2.setCellValue("级别");
		cell2.setCellStyle(style);
		sheet.autoSizeColumn(2);
		sheet.setColumnWidth(2, 5213);
		
		HSSFCell cell3 = row.createCell(3);
		row.setHeightInPoints(20);
		cell3.setCellValue("创建时间");
		cell3.setCellStyle(style);
		sheet.autoSizeColumn(3);
		sheet.setColumnWidth(3, 5213);
		
		HSSFCell cell4 = row.createCell(4);
		row.setHeightInPoints(20);
		cell4.setCellValue("创建者");
		cell4.setCellStyle(style);
		sheet.autoSizeColumn(4);
		sheet.setColumnWidth(4, 5213);
		
//		for (int i = 0; i < data.size(); i++) {
//			row = sheet.createRow(i + 1);
//			row.setHeightInPoints(15);
//			UnitBean unitBean = (UnitBean) data.get(i);
//			List<UnitBean> child = unitBean.getChildren();
//			for (int j = 0; j < excelHeader.size(); j++) {
//				HSSFCell cell0 = row.createCell(j);
//				cell0.setCellStyle(style2);
//				if(map.get(excelHeader.get(j))!=null){
//					cell0.setCellValue(map.get(excelHeader.get(j)).toString());
//				}else{
//					cell0.setCellValue("");
//				}
//			}
//		}
		wb = writeUnno(wb,data,sname,"");
		return wb;
	}
	
	public  HSSFWorkbook writeUnno(HSSFWorkbook wb,List<UnitBean> data,String sname,String space){
		HSSFSheet sheet = wb.getSheet(sname);
		
		for(int i =0;i<data.size();i++){
			hang++;
			HSSFRow row = sheet.createRow((int)hang);
			UnitBean u = data.get(i);
			
			HSSFCell cell = row.createCell(0);
			row.setHeightInPoints(20);
			cell.setCellValue(space+u.getUnitName());
	//		cell.setCellStyle(style);
			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(0, 15000);
			
			HSSFCell cell1 = row.createCell(1);
			row.setHeightInPoints(20);
			cell1.setCellValue(u.getUnNo());
	//		cell1.setCellStyle(style);
			sheet.autoSizeColumn(1);
			sheet.setColumnWidth(1, 5213);
			
			String lvl = "";
			if(u.getUnLvl()<=3){
				lvl = u.getUnLvl()-1+"";
			}else {
				lvl = u.getUnLvl()-2+"";
			}
			HSSFCell cell2 = row.createCell(2);
			row.setHeightInPoints(20);
			cell2.setCellValue(lvl);
	//		cell2.setCellStyle(style);
			sheet.autoSizeColumn(2);
			sheet.setColumnWidth(2, 5213);
			
			HSSFCell cell3 = row.createCell(3);
			row.setHeightInPoints(20);
			cell3.setCellValue(u.getCreateDate().toString());
	//		cell2.setCellStyle(style);
			sheet.autoSizeColumn(3);
			sheet.setColumnWidth(3, 5213);
			
			HSSFCell cell4 = row.createCell(4);
			row.setHeightInPoints(20);
			cell4.setCellValue(u.getCreateUser());
	//		cell3.setCellStyle(style);
			sheet.autoSizeColumn(4);
			sheet.setColumnWidth(4, 5213);
			
			if(u.getChildren()!=null&&u.getChildren().size()>0){
				wb = writeUnno(wb,u.getChildren(),sname,space+"       ");
			}
		}
		
		return wb;
	}
}
