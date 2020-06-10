
package com.hrt.gmms.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for agentUnitInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agentUnitInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="agentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amountConfirmDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="areaType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankAccNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankArea" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankBranch" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankOpenAcc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bankType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="buid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="businessContacts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessContactsMail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="businessContactsPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contactTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contractNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="legalType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maintainDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="maintainType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="maintainUid" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="maintainUserId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="openDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="registryNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="riskContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskContactMail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="riskContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondContactPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secondContactTel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="signUserId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="unno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cronym" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agentUnitInfo", propOrder = {
    "accType",
    "agentName",
    "amountConfirmDate",
    "areaType",
    "baddr",
    "bankAccName",
    "bankAccNo",
    "bankArea",
    "bankBranch",
    "bankOpenAcc",
    "bankType",
    "bno",
    "buid",
    "businessContacts",
    "businessContactsMail",
    "businessContactsPhone",
    "contact",
    "contactPhone",
    "contactTel",
    "contractNo",
    "legalNum",
    "legalPerson",
    "legalType",
    "maintainDate",
    "maintainType",
    "maintainUid",
    "maintainUserId",
    "openDate",
    "registryNo",
    "riskAmount",
    "riskContact",
    "riskContactMail",
    "riskContactPhone",
    "rno",
    "secondContact",
    "secondContactPhone",
    "secondContactTel",
    "signUserId",
    "unno",
    "cronym",
    "upperUnit1",
    "upperUnit2",
    "upperUnit3",
    "isM35",
    "payBankId",
    "parCompany",
    "mid",
    "cycle",
    "cashRate",
    "purseType"
})
public class AgentUnitInfo {

    protected String accType;
    protected String agentName;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar amountConfirmDate;
    protected String areaType;
    protected String baddr;
    protected String bankAccName;
    protected String bankAccNo;
    protected String bankArea;
    protected String bankBranch;
    protected String bankOpenAcc;
    protected String bankType;
    protected String bno;
    protected Integer buid;
    protected String businessContacts;
    protected String businessContactsMail;
    protected String businessContactsPhone;
    protected String contact;
    protected String contactPhone;
    protected String contactTel;
    protected String contractNo;
    protected String legalNum;
    protected String legalPerson;
    protected String legalType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar maintainDate;
    protected String maintainType;
    protected Integer maintainUid;
    protected Integer maintainUserId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar openDate;
    protected String registryNo;
    protected Double riskAmount;
    protected String riskContact;
    protected String riskContactMail;
    protected String riskContactPhone;
    protected String rno;
    protected String secondContact;
    protected String secondContactPhone;
    protected String secondContactTel;
    protected Integer signUserId;
    protected String unno;
    protected String cronym;
    protected String upperUnit1;
    protected String upperUnit2;
    protected String upperUnit3;
    protected String isM35;
    protected String payBankId;     //支付系统行号
    protected String parCompany;    //一代归属运营中心
    protected String mid;    //mid
    protected Integer cycle;
    protected Double cashRate;
    protected Integer purseType;
    
    public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Double getCashRate() {
		return cashRate;
	}

	public void setCashRate(Double cashRate) {
		this.cashRate = cashRate;
	}

	public Integer getPurseType() {
		return purseType;
	}

	public void setPurseType(Integer purseType) {
		this.purseType = purseType;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getParCompany() {
		return parCompany;
	}

	public void setParCompany(String parCompany) {
		this.parCompany = parCompany;
	}

	public String getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}

	public String getIsM35() {
		return isM35;
	}

	public void setIsM35(String isM35) {
		this.isM35 = isM35;
	}

	public String getUpperUnit1() {
		return upperUnit1;
	}

	public void setUpperUnit1(String upperUnit1) {
		this.upperUnit1 = upperUnit1;
	}

	public String getUpperUnit2() {
		return upperUnit2;
	}

	public void setUpperUnit2(String upperUnit2) {
		this.upperUnit2 = upperUnit2;
	}

	public String getUpperUnit3() {
		return upperUnit3;
	}

	public void setUpperUnit3(String upperUnit3) {
		this.upperUnit3 = upperUnit3;
	}

	/**
     * Gets the value of the accType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccType() {
        return accType;
    }

    /**
     * Sets the value of the accType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccType(String value) {
        this.accType = value;
    }

    /**
     * Gets the value of the agentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Sets the value of the agentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentName(String value) {
        this.agentName = value;
    }

    /**
     * Gets the value of the amountConfirmDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAmountConfirmDate() {
        return amountConfirmDate;
    }

    /**
     * Sets the value of the amountConfirmDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAmountConfirmDate(XMLGregorianCalendar value) {
        this.amountConfirmDate = value;
    }

    /**
     * Gets the value of the areaType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaType() {
        return areaType;
    }

    /**
     * Sets the value of the areaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaType(String value) {
        this.areaType = value;
    }

    /**
     * Gets the value of the baddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaddr() {
        return baddr;
    }

    /**
     * Sets the value of the baddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaddr(String value) {
        this.baddr = value;
    }

    /**
     * Gets the value of the bankAccName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccName() {
        return bankAccName;
    }

    /**
     * Sets the value of the bankAccName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccName(String value) {
        this.bankAccName = value;
    }

    /**
     * Gets the value of the bankAccNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccNo() {
        return bankAccNo;
    }

    /**
     * Sets the value of the bankAccNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccNo(String value) {
        this.bankAccNo = value;
    }

    /**
     * Gets the value of the bankArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankArea() {
        return bankArea;
    }

    /**
     * Sets the value of the bankArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankArea(String value) {
        this.bankArea = value;
    }

    /**
     * Gets the value of the bankBranch property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankBranch() {
        return bankBranch;
    }

    /**
     * Sets the value of the bankBranch property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankBranch(String value) {
        this.bankBranch = value;
    }

    /**
     * Gets the value of the bankOpenAcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankOpenAcc() {
        return bankOpenAcc;
    }

    /**
     * Sets the value of the bankOpenAcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankOpenAcc(String value) {
        this.bankOpenAcc = value;
    }

    /**
     * Gets the value of the bankType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankType() {
        return bankType;
    }

    /**
     * Sets the value of the bankType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankType(String value) {
        this.bankType = value;
    }

    /**
     * Gets the value of the bno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBno() {
        return bno;
    }

    /**
     * Sets the value of the bno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBno(String value) {
        this.bno = value;
    }

    /**
     * Gets the value of the buid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBuid() {
        return buid;
    }

    /**
     * Sets the value of the buid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBuid(Integer value) {
        this.buid = value;
    }

    /**
     * Gets the value of the businessContacts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContacts() {
        return businessContacts;
    }

    /**
     * Sets the value of the businessContacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContacts(String value) {
        this.businessContacts = value;
    }

    /**
     * Gets the value of the businessContactsMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContactsMail() {
        return businessContactsMail;
    }

    /**
     * Sets the value of the businessContactsMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContactsMail(String value) {
        this.businessContactsMail = value;
    }

    /**
     * Gets the value of the businessContactsPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessContactsPhone() {
        return businessContactsPhone;
    }

    /**
     * Sets the value of the businessContactsPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessContactsPhone(String value) {
        this.businessContactsPhone = value;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContact(String value) {
        this.contact = value;
    }

    /**
     * Gets the value of the contactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * Sets the value of the contactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPhone(String value) {
        this.contactPhone = value;
    }

    /**
     * Gets the value of the contactTel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     * Sets the value of the contactTel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactTel(String value) {
        this.contactTel = value;
    }

    /**
     * Gets the value of the contractNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * Sets the value of the contractNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNo(String value) {
        this.contractNo = value;
    }

    /**
     * Gets the value of the legalNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalNum() {
        return legalNum;
    }

    /**
     * Sets the value of the legalNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalNum(String value) {
        this.legalNum = value;
    }

    /**
     * Gets the value of the legalPerson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * Sets the value of the legalPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalPerson(String value) {
        this.legalPerson = value;
    }

    /**
     * Gets the value of the legalType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalType() {
        return legalType;
    }

    /**
     * Sets the value of the legalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalType(String value) {
        this.legalType = value;
    }

    /**
     * Gets the value of the maintainDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaintainDate() {
        return maintainDate;
    }

    /**
     * Sets the value of the maintainDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaintainDate(XMLGregorianCalendar value) {
        this.maintainDate = value;
    }

    /**
     * Gets the value of the maintainType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaintainType() {
        return maintainType;
    }

    /**
     * Sets the value of the maintainType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaintainType(String value) {
        this.maintainType = value;
    }

    /**
     * Gets the value of the maintainUid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaintainUid() {
        return maintainUid;
    }

    /**
     * Sets the value of the maintainUid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaintainUid(Integer value) {
        this.maintainUid = value;
    }

    /**
     * Gets the value of the maintainUserId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaintainUserId() {
        return maintainUserId;
    }

    /**
     * Sets the value of the maintainUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaintainUserId(Integer value) {
        this.maintainUserId = value;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpenDate(XMLGregorianCalendar value) {
        this.openDate = value;
    }

    /**
     * Gets the value of the registryNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistryNo() {
        return registryNo;
    }

    /**
     * Sets the value of the registryNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistryNo(String value) {
        this.registryNo = value;
    }

    /**
     * Gets the value of the riskAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRiskAmount() {
        return riskAmount;
    }

    /**
     * Sets the value of the riskAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRiskAmount(Double value) {
        this.riskAmount = value;
    }

    /**
     * Gets the value of the riskContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskContact() {
        return riskContact;
    }

    /**
     * Sets the value of the riskContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskContact(String value) {
        this.riskContact = value;
    }

    /**
     * Gets the value of the riskContactMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskContactMail() {
        return riskContactMail;
    }

    /**
     * Sets the value of the riskContactMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskContactMail(String value) {
        this.riskContactMail = value;
    }

    /**
     * Gets the value of the riskContactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiskContactPhone() {
        return riskContactPhone;
    }

    /**
     * Sets the value of the riskContactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiskContactPhone(String value) {
        this.riskContactPhone = value;
    }

    /**
     * Gets the value of the rno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRno() {
        return rno;
    }

    /**
     * Sets the value of the rno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRno(String value) {
        this.rno = value;
    }

    /**
     * Gets the value of the secondContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondContact() {
        return secondContact;
    }

    /**
     * Sets the value of the secondContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondContact(String value) {
        this.secondContact = value;
    }

    /**
     * Gets the value of the secondContactPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondContactPhone() {
        return secondContactPhone;
    }

    /**
     * Sets the value of the secondContactPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondContactPhone(String value) {
        this.secondContactPhone = value;
    }

    /**
     * Gets the value of the secondContactTel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecondContactTel() {
        return secondContactTel;
    }

    /**
     * Sets the value of the secondContactTel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecondContactTel(String value) {
        this.secondContactTel = value;
    }

    /**
     * Gets the value of the signUserId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSignUserId() {
        return signUserId;
    }

    /**
     * Sets the value of the signUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSignUserId(Integer value) {
        this.signUserId = value;
    }

    /**
     * Gets the value of the unno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnno() {
        return unno;
    }

    /**
     * Sets the value of the unno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnno(String value) {
        this.unno = value;
    }
    
    /**
     * Gets the value of the cronym property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCronym() {
        return cronym;
    }

    /**
     * Sets the value of the cronym property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCronym(String value) {
        this.cronym = value;
    }

	@Override
	public String toString() {
		return "AgentUnitInfo [accType=" + accType + ", agentName=" + agentName + ", amountConfirmDate="
				+ amountConfirmDate + ", areaType=" + areaType + ", baddr=" + baddr + ", bankAccName=" + bankAccName
				+ ", bankAccNo=" + bankAccNo + ", bankArea=" + bankArea + ", bankBranch=" + bankBranch
				+ ", bankOpenAcc=" + bankOpenAcc + ", bankType=" + bankType + ", bno=" + bno + ", buid=" + buid
				+ ", businessContacts=" + businessContacts + ", businessContactsMail=" + businessContactsMail
				+ ", businessContactsPhone=" + businessContactsPhone + ", contact=" + contact + ", contactPhone="
				+ contactPhone + ", contactTel=" + contactTel + ", contractNo=" + contractNo + ", legalNum=" + legalNum
				+ ", legalPerson=" + legalPerson + ", legalType=" + legalType + ", maintainDate=" + maintainDate
				+ ", maintainType=" + maintainType + ", maintainUid=" + maintainUid + ", maintainUserId="
				+ maintainUserId + ", openDate=" + openDate + ", registryNo=" + registryNo + ", riskAmount="
				+ riskAmount + ", riskContact=" + riskContact + ", riskContactMail=" + riskContactMail
				+ ", riskContactPhone=" + riskContactPhone + ", rno=" + rno + ", secondContact=" + secondContact
				+ ", secondContactPhone=" + secondContactPhone + ", secondContactTel=" + secondContactTel
				+ ", signUserId=" + signUserId + ", unno=" + unno + ", cronym=" + cronym + ", upperUnit1=" + upperUnit1
				+ ", upperUnit2=" + upperUnit2 + ", upperUnit3=" + upperUnit3 + ", isM35=" + isM35 + ", payBankId="
				+ payBankId + "]";
	}
    
}
