
package com.hrt.gmms.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for termAcc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="termAcc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cby" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modleId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="typeflag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "termAcc", propOrder = {
    "cby",
    "mid",
    "modleId",
    "tid",
    "typeflag",
    "sn",
    "unno",
    "rate",
    "secondRate",
    "depositFlag",
    "depositAmt",
    "snType",
    "rebateType",
    "nonConnection",
    "keyConfirmDate",
    "outDate",
    "snsigndate",
    "isReturnCash",
	"firmoney"
})
public class TermAcc {

    protected String cby;
    protected String mid;
    protected String modleId;
    protected String tid;
    protected Integer typeflag;
    protected String sn;
    protected String unno;
    protected Double rate;			//费率
    protected Double secondRate;		//秒到手续费
    protected Integer depositFlag;	//1-押金
    protected Integer depositAmt;	//1-押金
    protected String snType;		//三级分享 2大蓝牙
    protected String nonConnection;		//是否开通非接
    protected Integer rebateType;		//返利类型
    protected String keyConfirmDate;	//入网时间
    protected Date outDate;
    protected String snsigndate;
	private Integer isReturnCash;//是否返现0-没有 1-已返2-已返但返利金额为0
	/* 首笔最低消费金额 */
	private Double firmoney;

	public String getSnsigndate() {
		return snsigndate;
	}

	public void setSnsigndate(String snsigndate) {
		this.snsigndate = snsigndate;
	}

	public Double getFirmoney() {
		return firmoney;
	}

	public void setFirmoney(Double firmoney) {
		this.firmoney = firmoney;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public Integer getIsReturnCash() {
		return isReturnCash;
	}

	public void setIsReturnCash(Integer isReturnCash) {
		this.isReturnCash = isReturnCash;
	}

	public String getKeyConfirmDate() {
		return keyConfirmDate;
	}

	public void setKeyConfirmDate(String keyConfirmDate) {
		this.keyConfirmDate = keyConfirmDate;
	}

	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}

	public String getNonConnection() {
		return nonConnection;
	}

	public void setNonConnection(String nonConnection) {
		this.nonConnection = nonConnection;
	}

	public String getSnType() {
		return snType;
	}

	public void setSnType(String snType) {
		this.snType = snType;
	}

	public Integer getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(Integer depositAmt) {
		this.depositAmt = depositAmt;
	}

	public Integer getDepositFlag() {
		return depositFlag;
	}

	public void setDepositFlag(Integer depositFlag) {
		this.depositFlag = depositFlag;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getSecondRate() {
		return secondRate;
	}

	public void setSecondRate(Double secondRate) {
		this.secondRate = secondRate;
	}

	/**
     * Gets the value of the cby property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCby() {
        return cby;
    }

    /**
     * Sets the value of the cby property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCby(String value) {
        this.cby = value;
    }

    /**
     * Gets the value of the mid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMid() {
        return mid;
    }

    /**
     * Sets the value of the mid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMid(String value) {
        this.mid = value;
    }

    /**
     * Gets the value of the modleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModleId() {
        return modleId;
    }

    /**
     * Sets the value of the modleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModleId(String value) {
        this.modleId = value;
    }

    /**
     * Gets the value of the tid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTid() {
        return tid;
    }

    /**
     * Sets the value of the tid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTid(String value) {
        this.tid = value;
    }

    /**
     * Gets the value of the typeflag property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTypeflag() {
        return typeflag;
    }

    /**
     * Sets the value of the typeflag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTypeflag(Integer value) {
        this.typeflag = value;
    }

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getUnno() {
		return unno;
	}

	public void setUnno(String unno) {
		this.unno = unno;
	}

}
