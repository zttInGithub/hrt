package com.hrt.phone.service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.hrt.phone.entity.model.GateCarModel;
import com.hrt.phone.entity.pagebean.GateCarBean;

public interface GateCarService {

	void saveGateCarInfo(GateCarModel gateCarModel);

	//GateCarModel queryCarInfo(String hql);

	List<Map<String, Object>> querySupportCities();

	String login();

//	List<Map<String, Object>> getCarcodeAndEngineLength(GateCarBean gateCarBean);
	public void updateGateCarInfo(GateCarBean gateCarBean)throws FileNotFoundException;

	Integer queryLeftTimesByMid(String mid);

	Integer queryGateCarInfoCount(String mid);

	List<GateCarBean> queryCarInfo(GateCarBean gateCarBean);

	void deleteCarInfo(Integer carId);

	void updateGateCarInfo(GateCarModel gateCarModel2);

	String queryIfUploadPic(String carNumber,String mid);

	List<GateCarModel> queryCarInfo1(GateCarBean gateCarBean);
	
}
