
package com.xwf.cloudauth.api.uid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MatchTwoPhotosRequest complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MatchTwoPhotosRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://api.cloudauth.xwf.com/uid}BaseRequest"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="photo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="photo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchTwoPhotosRequest", propOrder = {
    "photo1",
    "photo2"
})
public class MatchTwoPhotosRequest
    extends BaseRequest
{

    protected String photo1;
    protected String photo2;

    /**
     * 获取photo1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoto1() {
        return photo1;
    }

    /**
     * 设置photo1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoto1(String value) {
        this.photo1 = value;
    }

    /**
     * 获取photo2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoto2() {
        return photo2;
    }

    /**
     * 设置photo2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoto2(String value) {
        this.photo2 = value;
    }

}
