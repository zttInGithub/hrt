package com.hrt.phone.service;

import java.util.List;
import java.util.Map;

import com.hrt.frame.entity.pagebean.DataGridBean;
import com.hrt.frame.entity.pagebean.JsonBean;
import com.hrt.frame.entity.pagebean.UserBean;

public interface IPhoneCheckUnitDealDataService {

	/*
	 *功能：查询首页信息 
	 */
	List<Map<String, Object>> findHomePageData(Object userSession);

	/*
	 * 功能查询待结算信息
	 */
	DataGridBean findNoSettlement(Object userSession,Integer page,Integer rows);
	
	/*
	 * 功能查询待结算详细信息
	 */
	
	DataGridBean findNoSettlementDetail(Object userSession, Integer page, Integer rows,String mid);
	
	/*
	 * 功能查询当日交易额详细信息
	 */
	DataGridBean findTheDayTurnover(Object userSession, Integer page, Integer rows);

	JsonBean findDynamicFormData(Object userSession);

	DataGridBean findTop20(Object userSession);

	DataGridBean findGains(Object userSession, Integer page, Integer rows);

	DataGridBean findDrop(Object userSession, Integer page, Integer rows);

	JsonBean findHistertoy(Object userSession, String startDay,
			String endDay, Integer page, Integer rows,String txnAmount);
	JsonBean findHistertoyScan(Object userSession, String startDay,
			String endDay, Integer page, Integer rows,String txnAmount,String trantype);

	/**
	 * 无卡交易明细(按月表明细查询)
	 * @param userSession
	 * @param startDay
	 * @param endDay
	 * @param page
	 * @param rows
	 * @param txnAmount
	 * @param trantype
	 * @return
	 */
	JsonBean findHistertoyScanWeChat(Object userSession, String startDay,
									 String endDay,Integer page,Integer rows,String txnAmount,String trantype);

	JsonBean findHistertoy2(Object userSession, Integer page, Integer rows,
			Integer code);
	JsonBean findMonthDynamicFormData(Object userSession);

	JsonBean findYearDynamicFormData(Object userSession);

	DataGridBean findRealTimeRrading(UserBean userSession, Integer page, Integer rows);
	
	DataGridBean findWechatRealTimeRrading(UserBean userSession, Integer page, Integer rows);
	DataGridBean findWechatRealTimeRradingMd(UserBean userSession, Integer page, Integer rows);

	Integer findRealTimeRradingCount(UserBean userSession);
	
	Double findWechatRealTimeRradingTotal(UserBean userSession);
	Double findWechatRealTimeRradingTotalMd(UserBean userSession);

	DataGridBean findReceiptsUpload(Object userSession, Integer page,
			Integer rows);

	DataGridBean findHistertoyCount(Object userSession, String startDay,
			String endDay, Integer page, Integer rows, String txnAmount);

	List<Map<String, Object>> findHomePageDataNew(Object userSession);

	DataGridBean findRiskReceiptsUpload(Object userSession, Integer page, Integer rows);


}
