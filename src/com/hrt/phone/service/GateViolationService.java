package com.hrt.phone.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.phone.entity.model.GateViolationModel;
import com.hrt.phone.entity.pagebean.GateViolationBean;

public interface GateViolationService {


	List<Map<String, Object>> queryViolations(String sql);
	
	public void save(GateViolationModel gateViolationModel);

	Integer saveGateCarIfFrist(GateViolationBean gateViolationBean,Integer leftTimes);
	
	List<GateViolationModel>  queryViolationInfobyNumber(GateViolationBean gateViolationBean)throws ParseException ;
	
	JsonBean saveViolationInfobySANFANG(GateViolationBean gateViolationBean);
	
	Integer updateQueryTimes(String mid);
}
