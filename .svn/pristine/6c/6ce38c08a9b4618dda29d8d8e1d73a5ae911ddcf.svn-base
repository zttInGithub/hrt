package com.hrt.biz.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.alibaba.fastjson.JSONArray;
import com.hrt.biz.bill.dao.IMachineInfoDao;

@WebService
public class HrtServiceImpl implements IHrtService{
	private IMachineInfoDao machineInfoDao;
	@WebMethod(exclude=true)
	public IMachineInfoDao getMachineInfoDao() {
		return machineInfoDao;
	}
	@WebMethod(exclude=true)
	public void setMachineInfoDao(IMachineInfoDao machineInfoDao) {
		this.machineInfoDao = machineInfoDao;
	}

	@Override
	public String echo(@WebParam(name = "id")String id) {
		String sql="select * from bill_MachineInfo where MachineModel='"+id+"'";
		List list=machineInfoDao.executeSql(sql);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String getUserByName(@WebParam(name = "name")String name) {
        User user = new User();
        user.setName(name);
        return user.getName();
	}

	@Override
	public void setUser(User user) {
        System.out.println("############Server setUser###########");
        System.out.println("setUser:" + user);
	}

}
