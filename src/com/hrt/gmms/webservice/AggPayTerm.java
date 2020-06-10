
package com.hrt.gmms.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


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
@XmlType(name = "aggPayTerm", propOrder = {
    "bapid",
    "unno",
    "mid",
    "shopname",
    "qrtid",
    "minfo1",
    "minfo2",
    "cby",
    "typeflag"
})
public class AggPayTerm {

	protected Integer bapid;
	protected String unno;
	protected String mid;
    protected String shopname;
    protected String qrtid;
    protected String minfo1;
    protected String minfo2;
    protected String cby;
    protected Integer typeflag;
    
	public Integer getTypeflag() {
		return typeflag;
	}
	public void setTypeflag(Integer typeflag) {
		this.typeflag = typeflag;
	}
	public String getMinfo2() {
		return minfo2;
	}
	public void setMinfo2(String minfo2) {
		this.minfo2 = minfo2;
	}
	public Integer getBapid() {
		return bapid;
	}
	public void setBapid(Integer bapid) {
		this.bapid = bapid;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getQrtid() {
		return qrtid;
	}
	public void setQrtid(String qrtid) {
		this.qrtid = qrtid;
	}
	public String getMinfo1() {
		return minfo1;
	}
	public void setMinfo1(String minfo1) {
		this.minfo1 = minfo1;
	}
	public String getCby() {
		return cby;
	}
	public void setCby(String cby) {
		this.cby = cby;
	}

}
