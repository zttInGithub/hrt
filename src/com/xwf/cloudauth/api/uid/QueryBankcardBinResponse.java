
package com.xwf.cloudauth.api.uid;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>QueryBankcardBinResponse complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="QueryBankcardBinResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://api.cloudauth.xwf.com/uid}BaseResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="bankcardBin" type="{http://api.cloudauth.xwf.com/uid}bankcardBin" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryBankcardBinResponse", propOrder = {
    "bankcardBin"
})
public class QueryBankcardBinResponse
    extends BaseResponse
{

    protected BankcardBin bankcardBin;

    /**
     * 获取bankcardBin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BankcardBin }
     *     
     */
    public BankcardBin getBankcardBin() {
        return bankcardBin;
    }

    /**
     * 设置bankcardBin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BankcardBin }
     *     
     */
    public void setBankcardBin(BankcardBin value) {
        this.bankcardBin = value;
    }

}
