package com.hrt.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class JxlOutExcelUtil {

	/**
	 * @param list
	 *            所有数据集 ，包含表头 每一行数据占用一个数组
	 * @param columnSize
	 *            数据一共多少列
	 * @param response
	 *            请求返回的resp
	 * @param sheetName
	 *            sheet页名称
	 * @param fileName
	 *            返回文件名称
	 * @param cellFormatType
	 *            格式化类型{t,t,t,t} t表示数字格式化
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 * @author lvJy
	 */
	public static void writeExcel(List<String[]> list, int columnSize, HttpServletResponse response, String sheetName,
			String fileName, String[] cellFormatType) throws IOException, RowsExceededException, WriteException {

		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = wwb.createSheet(sheetName, 0);

		// 创建单元格样式
		WritableFont font1 = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.NO_BOLD);
		WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.RED);
		WritableCellFormat wcf = new WritableCellFormat(font1);
		WritableCellFormat wcf2 = new WritableCellFormat(font2);
		// 设置边框;
		wcf2.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
		// 设置自动换行;
		wcf2.setWrap(true);
		// 设置文字居中对齐方式;
		wcf2.setAlignment(Alignment.CENTRE);
		// 设置垂直居中;
		wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);

		wcf.setAlignment(Alignment.CENTRE); // 平行居中
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中

		// 遍历每行
		for (int rowId = 0; rowId < list.size(); rowId++) {
			// 设置列宽
			sheet.setColumnView(rowId, 30);
			// 遍历每行里面的每个 cell
			String[] st = (String[]) list.get(rowId);
			for (int columnId = 0; columnId < columnSize; columnId++) {
				if (cellFormatType == null || cellFormatType.length == 0) { // 如果cellFormatType数组不为空
					// default
					Label label = new Label(columnId, rowId, st[columnId]);
					sheet.addCell(label);
				} else {
					if (!cellFormatType[columnId].equals("t") && rowId != 0) {
						// 如果数值不为空
						if (st[columnId] != null) {
							jxl.write.Number number = new jxl.write.Number(columnId, rowId,
									Double.parseDouble(st[columnId]));
							sheet.addCell(number);
						} else {
							Label label = new Label(columnId, rowId, st[columnId]);
							sheet.addCell(label);
						}
					} else {
						Label label = new Label(columnId, rowId, st[columnId]);
						sheet.addCell(label);
					}
				}

			}
		}
		// 写入Exel工作表
		wwb.write();
		// 关闭Excel工作薄对象
		wwb.close();

		// 关闭流
		os.flush();
		os.close();
		os = null;

	}

	/**
	 * @param sheetName
	 *            sheet页名称
	 * @param datas
	 *            所有数据集
	 * @param fileList
	 *            所有字段名称
	 * @param headFileRelationMap
	 *            所有字段和表头映射
	 * @param fileName
	 *            生成文件名称
	 * @param response
	 *            需要写入的resp
	 * @throws IOException
	 * @throws WriteException
	 * @author lvJy
	 */
	public static void export(String sheetName, List<Map<String, Object>> datas, List<String> fileList,
			Map<String, Object> headFileRelationMap, String fileName, HttpServletResponse response)
			throws IOException, WriteException {
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
		OutputStream os = response.getOutputStream();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = wwb.createSheet(sheetName, 0);

		// 表头单元格样式
		WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat titleWcf = new WritableCellFormat(titleFont);
		titleWcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		titleWcf.setWrap(true);
		titleWcf.setAlignment(Alignment.CENTRE);
		titleWcf.setVerticalAlignment(VerticalAlignment.CENTRE);

		// 数据单元格样式
		WritableFont dataFont = new WritableFont(WritableFont.createFont("微软雅黑"), 9, WritableFont.NO_BOLD);
		WritableCellFormat dataWcf = new WritableCellFormat(dataFont);
		dataWcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		dataWcf.setWrap(true);
		dataWcf.setAlignment(Alignment.LEFT); // 平行居中
		dataWcf.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中

		// 处理表头
		sheet.setRowView(0, 400, false); // 设置行高
		for (int columnId = 0; columnId < fileList.size(); columnId++) {
			// 设置列宽
			sheet.setColumnView(columnId, 21);
			Object val = headFileRelationMap.get(fileList.get(columnId));
			val = val == null ? "" : val.toString();
			Label label = new Label(columnId, 0, val.toString());
			label.setCellFormat(titleWcf);
			sheet.addCell(label);
		}
		// 处理数据
		for (int rowId = 1; rowId <= datas.size(); rowId++) {
			sheet.setRowView(rowId, 320, false); // 设置行高
			Map<String, Object> row = datas.get(rowId - 1);
			for (int columnId = 0; columnId < fileList.size(); columnId++) {
				// 设置列宽
				sheet.setColumnView(columnId, 21);
				Object val = row.get(fileList.get(columnId));
				Label label = null;
				if (val != null) {
					label = new Label(columnId, rowId, val.toString());
				} else {
					label = new Label(columnId, rowId, "");
				}
				label.setCellFormat(dataWcf);
				sheet.addCell(label);
			}
		}

		// 写入Exel工作表
		wwb.write();
		wwb.close();
		// 关闭流
		os.flush();
		os.close();
		os = null;
	}
	
	/**
	 * 导出CSV
	 * @param exportData    要导出的数据
	 * @param rowMapper    表头的数据
	 * @param outPutPath    输出路径
	 * @param filename      文加名
	 */
	@SuppressWarnings("unchecked")
	public static File writeCSVFile(List list,int columnSize,HttpServletResponse response,String fileName) throws IOException{
        File csvFile = null;
       
        response.setCharacterEncoding("GBK");
 
        BufferedWriter bos = null;

        PrintWriter fos = null;

        fos = response.getWriter();

        bos = new BufferedWriter(fos);

        //这个就就是弹出下载对话框的关键代码

        response.setHeader("Content-Disposition", "attachment;filename="  + new String( fileName.getBytes("GBK"), "ISO8859-1"));
        BufferedWriter csvFileOutputStream = bos;            
        try {
            for(int rowId=0;rowId<list.size();rowId++){
            	String[] st=(String[])list.get(rowId);
          	  	for(int columnId=0; columnId < columnSize; columnId++){
          	  	  csvFileOutputStream.write("\""
                         + st[columnId].toString() + "\"\t,");
          	  		}         	
          	  	csvFileOutputStream.newLine();     //创建行    	           	
			    }          
            csvFileOutputStream.close(); 
            bos.close();
            fos.close();

            } catch (IOException e) {
				e.printStackTrace();
			} 
        list=null;
        return csvFile;
    }

}
