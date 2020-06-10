
package com.xwf.cloudauth.api.uid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>AuthenticateCardResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="AuthenticateCardResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://api.cloudauth.xwf.com/uid}BaseResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="externalRefNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="storablePan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticateCardResponse", propOrder = {
    "externalRefNumber",
    "storablePan",
    "token"
})
public class AuthenticateCardResponse
    extends BaseResponse
{

    protected String externalRefNumber;
    protected String storablePan;
    protected String token;

    /**
     * 获取externalRefNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalRefNumber() {
        return externalRefNumber;
    }

    /**
     * 设置externalRefNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalRefNumber(String value) {
        this.externalRefNumber = value;
    }

    /**
     * 获取storablePan属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorablePan() {
        return storablePan;
    }

    /**
     * 设置storablePan属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorablePan(String value) {
        this.storablePan = value;
    }

    /**
     * 获取token属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

}
