package com.hrt.biz.bill.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hrt.biz.bill.dao.IMerchantInfoDao;
import com.hrt.biz.bill.dao.IMerchantTwoUploadDao;
import com.hrt.biz.bill.entity.model.MerchantInfoModel;
import com.hrt.biz.bill.entity.model.MerchantTwoUploadModel;
import com.hrt.biz.bill.entity.pagebean.MerchantTwoUploadBean;
import com.hrt.biz.bill.service.IMerchantTwoUploadService;
import com.hrt.frame.constant.Constant;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.util.PictureWaterMarkUtil;

public class MerchantTwoUploadServiceImpl implements IMerchantTwoUploadService {

		private IMerchantTwoUploadDao merchantTwoUploadDao;
		private IMerchantInfoDao merchantInfoDao;

		public IMerchantInfoDao getMerchantInfoDao() {
			return merchantInfoDao;
		}

		public void setMerchantInfoDao(IMerchantInfoDao merchantInfoDao) {
			this.merchantInfoDao = merchantInfoDao;
		}

		public IMerchantTwoUploadDao getMerchantTwoUploadDao() {
			return merchantTwoUploadDao;
		}

		public void setMerchantTwoUploadDao(IMerchantTwoUploadDao merchantTwoUploadDao) {
			this.merchantTwoUploadDao = merchantTwoUploadDao;
		}

		@Override
		public DataGridBean queryMerchantTwoUpload(MerchantTwoUploadBean merchantTwoUpload, UserBean user) {
			DataGridBean dgb = new DataGridBean();
			String sql = "select b.*,a.rname from bill_merchantinfo a ,bill_merchanttwoupload b where a.mid=b.mid ";
			String sqlCount = "select count(1) from bill_merchantinfo a,bill_merchanttwoupload b where a.mid=b.mid ";
			//权限
			if("110000".equals(user.getUnNo())){
			}else{
				sql += " and a.unno='"+user.getUnNo()+"' ";
				sqlCount += " and a.unno='"+user.getUnNo()+"' ";
			}
			//查询条件
			if(merchantTwoUpload.getMid()!=null&&!"".equals(merchantTwoUpload.getMid())){
				sql += " and a.mid='"+merchantTwoUpload.getMid()+"'";
				sqlCount += " and a.mid='"+merchantTwoUpload.getMid()+"'";
			}
			if(merchantTwoUpload.getApproveStatus()!=null&&!"".equals(merchantTwoUpload.getApproveStatus())){
				sql += " and b.approvestatus='"+merchantTwoUpload.getApproveStatus()+"'";
				sqlCount += " and b.approvestatus='"+merchantTwoUpload.getApproveStatus()+"'";
			}
			if(merchantTwoUpload.getMaintaindate()!=null&&!"".equals(merchantTwoUpload.getMaintaindate())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(merchantTwoUpload.getMaintaindate());
				sql += " and b.maintaindate>=to_date('"+date+" 00:00:00','yyyy-MM-dd hh24:mi:ss') "
						+ "and b.maintaindate<=to_date('"+date+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
				sqlCount += " and b.maintaindate>=to_date('"+date+" 00:00:00','yyyy-MM-dd hh24:mi:ss') "
						+ "and b.maintaindate<=to_date('"+date+" 23:59:59','yyyy-MM-dd hh24:mi:ss')";
			}
			List<Map<String, Object>> list = merchantTwoUploadDao.queryObjectsBySqlList2(sql, null, merchantTwoUpload.getPage(), merchantTwoUpload.getRows());
			BigDecimal counts = merchantTwoUploadDao.querysqlCounts(sqlCount, null);
			dgb.setRows(list);
			dgb.setTotal(counts.intValue());
			return dgb;
		}

		@Override
		public void saveMerchantTwoUpload(MerchantTwoUploadBean merchantTwoUpload, UserBean user) {
			MerchantTwoUploadModel mtum = new MerchantTwoUploadModel();
			mtum.setMid(merchantTwoUpload.getMid());
			//上传文件
			if(merchantTwoUpload.getLegalNameFile() != null){
				StringBuffer fName1 = new StringBuffer();
				fName1.append(mtum.getMid());
				fName1.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName1.append(imageDay);
				fName1.append(".21");//二次上传专用名称
				fName1.append(merchantTwoUpload.getLegalName().substring(merchantTwoUpload.getLegalName().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantTwoUpload.getLegalNameFile(),fName1.toString(),imageDay);
				mtum.setLegalName(fName1.toString());
			}
			if(merchantTwoUpload.getLegalName2File() != null){
				StringBuffer fName2 = new StringBuffer();
				fName2.append(mtum.getMid());
				fName2.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName2.append(imageDay);
				fName2.append(".22");
				fName2.append(merchantTwoUpload.getLegalName2().substring(merchantTwoUpload.getLegalName2().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantTwoUpload.getLegalName2File(),fName2.toString(),imageDay);
				mtum.setLegalName2(fName2.toString());
			}
			if(merchantTwoUpload.getBupLoadFile() != null){
				StringBuffer fName3 = new StringBuffer();
				fName3.append(mtum.getMid());
				fName3.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName3.append(imageDay);
				fName3.append(".23");
				fName3.append(merchantTwoUpload.getBupLoad().substring(merchantTwoUpload.getBupLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantTwoUpload.getBupLoadFile(),fName3.toString(),imageDay);
				mtum.setBupLoad(fName3.toString());
			}
			if(merchantTwoUpload.getBigdealUpLoadFile() != null){
				StringBuffer fName4 = new StringBuffer();
				fName4.append(mtum.getMid());
				fName4.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName4.append(imageDay);
				fName4.append(".24");
				fName4.append(merchantTwoUpload.getBigdealUpLoad().substring(merchantTwoUpload.getBigdealUpLoad().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantTwoUpload.getBigdealUpLoadFile(),fName4.toString(),imageDay);
				mtum.setBigdealUpLoad(fName4.toString());
			}
			if(merchantTwoUpload.getLaborContractImgFile() != null){
				StringBuffer fName5 = new StringBuffer();
				fName5.append(mtum.getMid());
				fName5.append(".");
				String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
				fName5.append(imageDay);
				fName5.append(".25");
				fName5.append(merchantTwoUpload.getLaborContractImg().substring(merchantTwoUpload.getLaborContractImg().lastIndexOf(".")).toLowerCase());
				uploadFile(merchantTwoUpload.getLaborContractImgFile(),fName5.toString(),imageDay);
				mtum.setLaborContractImg(fName5.toString());
			}
			mtum.setMaintaindate(new Date());
			mtum.setMaintaintype("A");
			mtum.setApproveStatus("W");
			merchantTwoUploadDao.saveObject(mtum);
		}
		/**
		 * 上传
		 */
		private void uploadFile(File upload, String fName, String imageDay) {
			try {
				String realPath = queryUpLoadPath() + File.separator + imageDay;
				File dir = new File(realPath);
				dir.mkdirs();
				String fPath = realPath + File.separator + fName;
//				File destFile = new File(fPath);
				FileInputStream fis = new FileInputStream(upload);
				// @date:20190125 资料二次上传的图片添加水印
				PictureWaterMarkUtil.addWatermark(fis,fPath, Constant.PICTURE_WATER_MARK,
						fName.substring(fName.lastIndexOf(".")+1));
//				FileOutputStream fos = new FileOutputStream(destFile);
//				byte [] buffer = new byte[1024];
//				int len = 0;
//				while((len = fis.read(buffer))>0){
//					fos.write(buffer,0,len);
//				}
//				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 查询上传路径
		 */
		private String queryUpLoadPath() {
			String title="HrtFrameWork";
			return merchantTwoUploadDao.executeSql("SELECT UPLOAD_PATH FROM SYS_PARAM WHERE TITLE='"+title+"'").get(0).get("UPLOAD_PATH");
		}
		public String imagePathUtil(String imageDay){
			String[] split = imageDay.split("\\.");
			if(split[1].length()==8){
				return split[1];
			}else{
				return split[2];
			}
		}
		@Override
		public List<Object> queryMerchantInfoDetailed(Integer bmtuid) {
			MerchantTwoUploadModel mm=(MerchantTwoUploadModel) merchantTwoUploadDao.queryObjectByHql("from MerchantTwoUploadModel m where m.bmtuid=?", new Object[]{bmtuid});
			String upload = queryUpLoadPath();
			List<Object> list2=new ArrayList<Object>();
			if(mm.getLegalName()!="" && mm.getLegalName()!=null){
				list2.add(upload+File.separator+imagePathUtil(mm.getLegalName())+File.separator+mm.getLegalName());
			}else{
				list2.add("");
			}
			if(mm.getLegalName2()!="" && mm.getLegalName2()!=null){
				list2.add(upload+File.separator+imagePathUtil(mm.getLegalName2())+File.separator+mm.getLegalName2());
			}else{
				list2.add("");
			}
			if(mm.getBupLoad()!="" && mm.getBupLoad()!=null){
				list2.add(upload+File.separator+imagePathUtil(mm.getBupLoad())+File.separator+mm.getBupLoad());
			}else{
				list2.add(""); 
			}
			if(mm.getBigdealUpLoad()!="" && mm.getBigdealUpLoad()!=null){
				list2.add(upload+File.separator+imagePathUtil(mm.getBigdealUpLoad())+File.separator+mm.getBigdealUpLoad());
			}else{
				list2.add(""); 
			}
			if(mm.getLaborContractImg()!="" && mm.getLaborContractImg()!=null){
				list2.add(upload+File.separator+imagePathUtil(mm.getLaborContractImg())+File.separator+mm.getLaborContractImg());
			}else{
				list2.add(""); 
			}
			return list2; 
		}

		@Override
		public void updateMerchantInfo(MerchantTwoUploadBean merchantTwoUpload, UserBean user) {
			MerchantTwoUploadModel mtum = merchantTwoUploadDao.queryObjectByHql("from MerchantTwoUploadModel where bmtuid="+merchantTwoUpload.getBmtuid(), new Object[]{});
			if("K".equals(mtum.getApproveStatus())){
				//上传文件
				if(merchantTwoUpload.getLegalNameFile() != null){
					StringBuffer fName1 = new StringBuffer();
					fName1.append(mtum.getMid());
					fName1.append(".");
					String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
					fName1.append(imageDay);
					fName1.append(".21");//二次上传专用名称
					fName1.append(merchantTwoUpload.getLegalName().substring(merchantTwoUpload.getLegalName().lastIndexOf(".")).toLowerCase());
					uploadFile(merchantTwoUpload.getLegalNameFile(),fName1.toString(),imageDay);
					mtum.setLegalName(fName1.toString());
				}
				if(merchantTwoUpload.getLegalName2File() != null){
					StringBuffer fName2 = new StringBuffer();
					fName2.append(mtum.getMid());
					fName2.append(".");
					String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
					fName2.append(imageDay);
					fName2.append(".22");
					fName2.append(merchantTwoUpload.getLegalName2().substring(merchantTwoUpload.getLegalName2().lastIndexOf(".")).toLowerCase());
					uploadFile(merchantTwoUpload.getLegalName2File(),fName2.toString(),imageDay);
					mtum.setLegalName2(fName2.toString());
				}
				if(merchantTwoUpload.getBupLoadFile() != null){
					StringBuffer fName3 = new StringBuffer();
					fName3.append(mtum.getMid());
					fName3.append(".");
					String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
					fName3.append(imageDay);
					fName3.append(".23");
					fName3.append(merchantTwoUpload.getBupLoad().substring(merchantTwoUpload.getBupLoad().lastIndexOf(".")).toLowerCase());
					uploadFile(merchantTwoUpload.getBupLoadFile(),fName3.toString(),imageDay);
					mtum.setBupLoad(fName3.toString());
				}
				if(merchantTwoUpload.getBigdealUpLoadFile() != null){
					StringBuffer fName4 = new StringBuffer();
					fName4.append(mtum.getMid());
					fName4.append(".");
					String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
					fName4.append(imageDay);
					fName4.append(".24");
					fName4.append(merchantTwoUpload.getBigdealUpLoad().substring(merchantTwoUpload.getBigdealUpLoad().lastIndexOf(".")).toLowerCase());
					uploadFile(merchantTwoUpload.getBigdealUpLoadFile(),fName4.toString(),imageDay);
					mtum.setBigdealUpLoad(fName4.toString());
				}
				if(merchantTwoUpload.getLaborContractImgFile() != null){
					StringBuffer fName5 = new StringBuffer();
					fName5.append(mtum.getMid());
					fName5.append(".");
					String imageDay=new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
					fName5.append(imageDay);
					fName5.append(".25");
					fName5.append(merchantTwoUpload.getLaborContractImg().substring(merchantTwoUpload.getLaborContractImg().lastIndexOf(".")).toLowerCase());
					uploadFile(merchantTwoUpload.getLaborContractImgFile(),fName5.toString(),imageDay);
					mtum.setLaborContractImg(fName5.toString());
				}
				mtum.setMaintaindate(new Date());
				mtum.setMaintaintype("M");
				mtum.setApproveStatus("W");
				merchantTwoUploadDao.updateObject(mtum);
			}
		}

		@Override
		public Integer updateMerchantInfoK(MerchantTwoUploadBean merchantTwoUpload, UserBean user) {
			MerchantTwoUploadModel mtum = merchantTwoUploadDao.queryObjectByHql("from MerchantTwoUploadModel where bmtuid="+merchantTwoUpload.getBmtuid(), new Object[]{});
			if("W".equals(mtum.getApproveStatus())){
				mtum.setApproveDate(new Date());
				mtum.setApproveNote(merchantTwoUpload.getApproveNote());
				mtum.setApproveStatus("K");
				merchantTwoUploadDao.updateObject(mtum);
				return 1;
			}else{
				return 0;
			}
		}

		@Override
		public Integer updateMerchantInfoY(MerchantTwoUploadBean merchantTwoUpload, UserBean user) {
			MerchantTwoUploadModel mtum = merchantTwoUploadDao.queryObjectByHql("from MerchantTwoUploadModel where bmtuid="+merchantTwoUpload.getBmtuid(), new Object[]{});
			String upload = queryUpLoadPath();
			if("W".equals(mtum.getApproveStatus())){
				mtum.setApproveDate(new Date());
				mtum.setApproveStatus("Y");
				merchantTwoUploadDao.updateObject(mtum);
				MerchantInfoModel model = merchantInfoDao.queryObjectByHql("from MerchantInfoModel where mid=?", new Object[]{mtum.getMid()});
				if(mtum.getLegalName()!=null&&!"".equals(mtum.getLegalName())){
					//修改文件名
					String[] split = mtum.getLegalName().split("\\.");
					String fileN = mtum.getMid()+"."+split[1]+".1.jpg";
					//防止源文件存在，选择覆盖
					uploadFile2(upload+File.separator+imagePathUtil(mtum.getLegalName())+File.separator+mtum.getLegalName(), upload+File.separator+imagePathUtil(fileN)+File.separator+fileN);
					//修改商户表
					model.setLegalUploadFileName(fileN);
				}
				if(mtum.getLegalName2()!=null&&!"".equals(mtum.getLegalName2())){
					String[] split = mtum.getLegalName2().split("\\.");
					String fileN = mtum.getMid()+"."+split[1]+".5.jpg";
					uploadFile2(upload+File.separator+imagePathUtil(mtum.getLegalName2())+File.separator+mtum.getLegalName2(), upload+File.separator+imagePathUtil(fileN)+File.separator+fileN);
					model.setMaterialUpLoad(fileN);
				}
				if(mtum.getBupLoad()!=null&&!"".equals(mtum.getBupLoad())){
					String[] split = mtum.getBupLoad().split("\\.");
					String fileN = mtum.getMid()+"."+split[1]+".2.jpg";
					uploadFile2(upload+File.separator+imagePathUtil(mtum.getBupLoad())+File.separator+mtum.getBupLoad(), upload+File.separator+imagePathUtil(fileN)+File.separator+fileN);
					model.setBupLoad(fileN);
				}
				if(mtum.getBigdealUpLoad()!=null&&!"".equals(mtum.getBigdealUpLoad())){
					String[] split = mtum.getBigdealUpLoad().split("\\.");
					String fileN = mtum.getMid()+"."+split[1]+".7.jpg";
					uploadFile2(upload+File.separator+imagePathUtil(mtum.getBigdealUpLoad())+File.separator+mtum.getBigdealUpLoad(), upload+File.separator+imagePathUtil(fileN)+File.separator+fileN);
					model.setBigdealUpLoad(fileN);
				}
				if(mtum.getLaborContractImg()!=null&&!"".equals(mtum.getLaborContractImg())){
					String[] split = mtum.getLaborContractImg().split("\\.");
					String fileN = mtum.getMid()+"."+split[1]+".G.jpg";
					uploadFile2(upload+File.separator+imagePathUtil(mtum.getLaborContractImg())+File.separator+mtum.getLaborContractImg(), upload+File.separator+imagePathUtil(fileN)+File.separator+fileN);
					model.setLaborContractImg(fileN);
				}
				merchantInfoDao.updateObject(model);
				return 1;
			}else{
				return 0;
			}
		}
		/**
		 * 重命名文件,使用 fileOld的名字命名fileNew
		 * @param fileNew
		 * @param fileOld
		 */
		private void uploadFile2(String fileNew, String fileOld){
			FileInputStream ins = null;
			FileOutputStream out = null;
			try {
				ins = new FileInputStream(fileNew);//新文件
		        out = new FileOutputStream(fileOld);//原文件
		        byte[] b = new byte[1024];
		        int n=0;
		        while((n=ins.read(b))!=-1){
		            out.write(b, 0, n);
		        }
		        
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					ins.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public Integer queryMerchant(MerchantTwoUploadBean merchantTwoUpload, UserBean user) {
			Integer i = merchantInfoDao.querysqlCounts2("select count(1) from bill_merchantinfo where unno='"+user.getUnNo()+"' and mid='"+merchantTwoUpload.getMid()+"'", null);
			if(i<1){
				return 2;
			}
			String sql = "select count(1) from bill_merchantTwoUpload where approvestatus in('W','K') and mid = '"+merchantTwoUpload.getMid()+"'";
			return merchantInfoDao.querysqlCounts2(sql, null);
		}

		@Override
		public Integer deleteUploadImg(MerchantTwoUploadBean merchantTwoUpload, UserBean user) {
			MerchantTwoUploadModel mtum = merchantTwoUploadDao.queryObjectByHql("from MerchantTwoUploadModel where bmtuid="+merchantTwoUpload.getBmtuid(), new Object[]{});
			if(mtum==null){
				return 0;
			}
			if(merchantTwoUpload.getLegalName() != null && !"".equals(merchantTwoUpload.getLegalName())){
				mtum.setLegalName(null);
			}
			if(merchantTwoUpload.getLegalName2() != null && !"".equals(merchantTwoUpload.getLegalName2())){
				mtum.setLegalName2(null);
			}
			if(merchantTwoUpload.getBupLoad() != null && !"".equals(merchantTwoUpload.getBupLoad())){
				mtum.setBupLoad(null);
			}
			if(merchantTwoUpload.getBigdealUpLoad() != null && !"".equals(merchantTwoUpload.getBigdealUpLoad())){
				mtum.setBigdealUpLoad(null);
			}
			if(merchantTwoUpload.getLaborContractImg() != null && !"".equals(merchantTwoUpload.getLaborContractImg())){
				mtum.setLaborContractImg(null);
			}
			merchantTwoUploadDao.updateObject(mtum);
			return 1;
		}
}
