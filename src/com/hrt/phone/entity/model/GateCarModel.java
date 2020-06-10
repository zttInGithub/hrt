package com.hrt.phone.entity.model;

import java.util.Date;

/**
 * 车辆信息
 * @author sun
 *
 */
public class GateCarModel {
	private Integer carId;        //主键
	private String mid;           //商户号
	private String carNumber;     //车牌号
	private String carCode;       //车架号
	private String engineNumber;  //发动机号
	private String carType;       //车型：1：小车 2：大型客车 3：索引货车 4：中型客车  5：大型货车 6：两、三轮摩托车
	private Date mainTainDate;    //操作时间
	private String mainTainUid;   //操作人
	private String mainTainType; //操作状态
	private String driverName;    //驾驶人名字
	private String driverPhone;   //驾驶人电话
	private String driverNo;      //驾驶证编号
	private Date queryTime;       //查询时间
	private String driliPic1;      //行驶证正面图片路径
	private String driliPic2;	     //驾驶证正面路径
	private String identityCardPic;  //身份证图片路径
	private String insurancePic;     //车强交险图片路径
	private String driliBackPic;     //行驶证背面图片路径
	
	public String getDriliPic1() {
		return driliPic1;
	}
	public void setDriliPic1(String driliPic1) {
		this.driliPic1 = driliPic1;
	}
	public String getDriliPic2() {
		return driliPic2;
	}
	public void setDriliPic2(String driliPic2) {
		this.driliPic2 = driliPic2;
	}
	public String getIdentityCardPic() {
		return identityCardPic;
	}
	public void setIdentityCardPic(String identityCardPic) {
		this.identityCardPic = identityCardPic;
	}
	public String getInsurancePic() {
		return insurancePic;
	}
	public void setInsurancePic(String insurancePic) {
		this.insurancePic = insurancePic;
	}
	public String getDriliBackPic() {
		return driliBackPic;
	}
	public void setDriliBackPic(String driliBackPic) {
		this.driliBackPic = driliBackPic;
	}
	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}
	public Date getQueryTime() {
		return queryTime;
	}
	public Date getMainTainDate() {
		return mainTainDate;
	}
	public void setMainTainDate(Date mainTainDate) {
		this.mainTainDate = mainTainDate;
	}
	public String getMainTainUid() {
		return mainTainUid;
	}
	public void setMainTainUid(String mainTainUid) {
		this.mainTainUid = mainTainUid;
	}
	public String getMainTainType() {
		return mainTainType;
	}
	public void setMainTainType(String mainTainType) {
		this.mainTainType = mainTainType;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getDriverNo() {
		return driverNo;
	}
	public void setDriverNo(String driverNo) {
		this.driverNo = driverNo;
	}
	
	
}
