package com.hrt.frame.service.sysadmin;

import java.util.Map;

import com.hrt.frame.entity.model.FileModel;
import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.FileBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IFileService {
	
	/**
	 * 上传的文件信息保存到数据库
	 * @param fPath
	 * @param fName
	 * @param fDesc
	 * @param loginName
	 * @throws Exception
	 */
	void saveFile(String fPath,String fName,String fDesc,String loginName) throws Exception ;

	/**
	 * 查询数据库中的已上传的文件信息
	 * @param file
	 * @return
	 * @throws Exception
	 */
	DataGridBean queryFiles(FileBean file) throws Exception;

	/**
	 * 查询运营中心报告
	 * @param file
	 * @param user
	 * @return
	 * @throws Exception
	 */
	DataGridBean listAgentFiles(FileBean file,UserBean user) throws Exception;

	/**
	 * 查询需要下载的文件
	 * @param fileID
	 * @return
	 * @throws Exception
	 */
	FileModel getFileSingle(Integer fileID) throws Exception;

	/**
	 * 获得系统路径
	 * @return
	 */
	Map<String, Object> querySysParam();

	/**
	 * 删除
	 * @param parseInt
	 * @return
	 */
	boolean deleteFile(int parseInt);
}
