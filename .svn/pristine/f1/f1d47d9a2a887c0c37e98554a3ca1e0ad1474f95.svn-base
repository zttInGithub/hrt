package com.hrt.frame.service.sysadmin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.hrt.frame.dao.sysadmin.IFileDao;
import com.hrt.frame.entity.model.FileModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.FileBean;
import com.hrt.frame.entity.pagebean.UserBean;
import com.hrt.frame.service.sysadmin.IFileService;

public class FileServiceImpl implements IFileService{
	
	private IFileDao fileDao;

	public IFileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(IFileDao fileDao) {
		this.fileDao = fileDao;
	}
	
	/**
	 * 方法功能：格式化FileModel为datagrid数据格式
	 * 参数：fileList
	 *     total 总记录数
	 * 返回值：
	 * 异常：
	 */
	private DataGridBean formatToDataGrid(List<FileModel> fileList, long total) {
		List<FileBean> fileBeanList = new ArrayList<FileBean>();
		for(FileModel fileModel : fileList) {
			FileBean fileBean = new FileBean();
			BeanUtils.copyProperties(fileModel, fileBean);
			fileBean.setFileName(StringUtils.substringBefore(fileModel.getFileName().trim(), "&"));
			
			fileBeanList.add(fileBean);
		}
		
		DataGridBean dgb = new DataGridBean();
		dgb.setTotal(total);
		dgb.setRows(fileBeanList);
		
		return dgb;
	}

	public Map<String, Object> querySysParam(){
		String sql="select * from SYS_PARAM t WHERE t.title='HrtFrameWork'";
		List<Map<String, Object>> list=fileDao.queryObjectsBySqlList(sql);
		Map<String, Object> param=list.get(0);
		return param;
	}
	
	@Override
	public void saveFile(String fPath,String fName,String fDesc,String loginName) throws Exception {
		FileModel fileModel = new FileModel();
		fileModel.setFileName(fName);
		fileModel.setFilePath(fPath);
		fileModel.setFileDesc(fDesc);
		fileModel.setStatus(0);		//0可用，1不可用
		fileModel.setCreateDate(new Date());
		fileModel.setCreateUser(loginName);
		fileModel.setUpdateDate(new Date());
		fileModel.setUpdateUser(loginName);
		fileDao.saveObject(fileModel);
	}

	@Override
	public DataGridBean queryFiles(FileBean file) throws Exception {
		String hql = "from FileModel where fileDesc != 'yyzxbg'";
		String hqlCount = "select count(*) from FileModel where fileDesc != 'yyzxbg'";
		if (file.getSort() != null) {
			hql += " order by " + file.getSort() + " " + file.getOrder();
		}
		long counts = fileDao.queryCounts(hqlCount, null);
		List<FileModel> fileList = fileDao.queryFiles(hql, null, file.getPage(), file.getRows());
		DataGridBean dataGridBean = formatToDataGrid(fileList, counts);
		return dataGridBean;
	}

	/**
	 * 查询运营中心报告
	 */
	@Override
	public DataGridBean listAgentFiles(FileBean file,UserBean user) throws Exception {
		String hql = "from FileModel where fileDesc = 'yyzxbg'";
		Map map=new HashMap();
		String hqlCount = "select count(*) from FileModel where fileDesc = 'yyzxbg'";
		if("110000".equals(user.getUnNo())||"superadmin".equals(user.getLoginName())){
		}else{
			hql += " and fileName like :unno ";
			hqlCount += " and fileName like  :unno ";
			map.put("unno",user.getUnNo()+"%");
		}
		if (file.getSort() != null) {
			hql += " order by " + file.getSort() + " " + file.getOrder();
		}else{
			hql += " order by fileID desc";
		}
		
		long counts = fileDao.queryCounts(hqlCount, map);
		List<FileModel> fileList = fileDao.queryFiles(hql, map, file.getPage(), file.getRows());
		DataGridBean dataGridBean = formatToDataGrid(fileList, counts);
		return dataGridBean;
	}
	
	@Override
	public FileModel getFileSingle(Integer fileID) throws Exception {
		FileModel fileModel = fileDao.getObjectByID(FileModel.class, fileID);
		return fileModel;
	}

	@Override
	public boolean deleteFile(int parseInt) {
		try {
			fileDao.deleteObject(getFileSingle(parseInt));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
