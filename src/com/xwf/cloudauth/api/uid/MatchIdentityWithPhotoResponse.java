
package com.xwf.cloudauth.api.uid;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>MatchIdentityWithPhotoResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="MatchIdentityWithPhotoResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://api.cloudauth.xwf.com/uid}BaseResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="verifySimilarity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MatchIdentityWithPhotoResponse", propOrder = {
    "verifySimilarity"
})
public class MatchIdentityWithPhotoResponse
    extends BaseResponse
{

    protected BigDecimal verifySimilarity;

    /**
     * 获取verifySimilarity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVerifySimilarity() {
        return verifySimilarity;
    }

    /**
     * 设置verifySimilarity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVerifySimilarity(BigDecimal value) {
        this.verifySimilarity = value;
    }

}
