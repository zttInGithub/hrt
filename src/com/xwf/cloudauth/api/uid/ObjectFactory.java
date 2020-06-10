
package com.xwf.cloudauth.api.uid;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xwf.cloudauth.api.uid package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryBankcardBinRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "queryBankcardBinRequest");
    private final static QName _QueryBankcardBinResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "queryBankcardBinResponse");
    private final static QName _AuthenticateCardRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "authenticateCardRequest");
    private final static QName _AuthenticateCardResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "authenticateCardResponse");
    private final static QName _AuthCardWithoutOTPRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "authCardWithoutOTPRequest");
    private final static QName _AuthCardWithoutOTPResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "authCardWithoutOTPResponse");
    private final static QName _MatchIdentityWithPhotoRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityWithPhotoRequest");
    private final static QName _MatchIdentityWithPhotoResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityWithPhotoResponse");
    private final static QName _MatchTwoPhotosRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchTwoPhotosRequest");
    private final static QName _MatchTwoPhotosResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchTwoPhotosResponse");
    private final static QName _MatchIdentityRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityRequest");
    private final static QName _MatchIdentityResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityResponse");
    private final static QName _MatchIdentityWithLivePhotoRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityWithLivePhotoRequest");
    private final static QName _MatchIdentityWithLivePhotoResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityWithLivePhotoResponse");
    private final static QName _MatchIdentityReturnPhotoRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityReturnPhotoRequest");
    private final static QName _MatchIdentityReturnPhotoResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "matchIdentityReturnPhotoResponse");
    private final static QName _VerifyCardOtpRequest_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "verifyCardOtpRequest");
    private final static QName _VerifyCardOtpResponse_QNAME = new QName("http://api.cloudauth.xwf.com/uid", "verifyCardOtpResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xwf.cloudauth.api.uid
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryBankcardBinRequest }
     * 
     */
    public QueryBankcardBinRequest createQueryBankcardBinRequest() {
        return new QueryBankcardBinRequest();
    }

    /**
     * Create an instance of {@link QueryBankcardBinResponse }
     * 
     */
    public QueryBankcardBinResponse createQueryBankcardBinResponse() {
        return new QueryBankcardBinResponse();
    }

    /**
     * Create an instance of {@link AuthenticateCardRequest }
     * 
     */
    public AuthenticateCardRequest createAuthenticateCardRequest() {
        return new AuthenticateCardRequest();
    }

    /**
     * Create an instance of {@link AuthenticateCardResponse }
     * 
     */
    public AuthenticateCardResponse createAuthenticateCardResponse() {
        return new AuthenticateCardResponse();
    }

    /**
     * Create an instance of {@link AuthCardWithoutOTPRequest }
     * 
     */
    public AuthCardWithoutOTPRequest createAuthCardWithoutOTPRequest() {
        return new AuthCardWithoutOTPRequest();
    }

    /**
     * Create an instance of {@link AuthCardWithoutOTPResponse }
     * 
     */
    public AuthCardWithoutOTPResponse createAuthCardWithoutOTPResponse() {
        return new AuthCardWithoutOTPResponse();
    }

    /**
     * Create an instance of {@link MatchIdentityWithPhotoRequest }
     * 
     */
    public MatchIdentityWithPhotoRequest createMatchIdentityWithPhotoRequest() {
        return new MatchIdentityWithPhotoRequest();
    }

    /**
     * Create an instance of {@link MatchIdentityWithPhotoResponse }
     * 
     */
    public MatchIdentityWithPhotoResponse createMatchIdentityWithPhotoResponse() {
        return new MatchIdentityWithPhotoResponse();
    }

    /**
     * Create an instance of {@link MatchTwoPhotosRequest }
     * 
     */
    public MatchTwoPhotosRequest createMatchTwoPhotosRequest() {
        return new MatchTwoPhotosRequest();
    }

    /**
     * Create an instance of {@link MatchIdentityRequest }
     * 
     */
    public MatchIdentityRequest createMatchIdentityRequest() {
        return new MatchIdentityRequest();
    }

    /**
     * Create an instance of {@link MatchIdentityResponse }
     * 
     */
    public MatchIdentityResponse createMatchIdentityResponse() {
        return new MatchIdentityResponse();
    }

    /**
     * Create an instance of {@link MatchIdentityReturnPhotoResponse }
     * 
     */
    public MatchIdentityReturnPhotoResponse createMatchIdentityReturnPhotoResponse() {
        return new MatchIdentityReturnPhotoResponse();
    }

    /**
     * Create an instance of {@link VerifyCardOtpRequest }
     * 
     */
    public VerifyCardOtpRequest createVerifyCardOtpRequest() {
        return new VerifyCardOtpRequest();
    }

    /**
     * Create an instance of {@link VerifyCardOtpResponse }
     * 
     */
    public VerifyCardOtpResponse createVerifyCardOtpResponse() {
        return new VerifyCardOtpResponse();
    }

    /**
     * Create an instance of {@link BaseRequest }
     * 
     */
    public BaseRequest createBaseRequest() {
        return new BaseRequest();
    }

    /**
     * Create an instance of {@link BaseResponse }
     * 
     */
    public BaseResponse createBaseResponse() {
        return new BaseResponse();
    }

    /**
     * Create an instance of {@link BankcardBin }
     * 
     */
    public BankcardBin createBankcardBin() {
        return new BankcardBin();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryBankcardBinRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "queryBankcardBinRequest")
    public JAXBElement<QueryBankcardBinRequest> createQueryBankcardBinRequest(QueryBankcardBinRequest value) {
        return new JAXBElement<QueryBankcardBinRequest>(_QueryBankcardBinRequest_QNAME, QueryBankcardBinRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryBankcardBinResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "queryBankcardBinResponse")
    public JAXBElement<QueryBankcardBinResponse> createQueryBankcardBinResponse(QueryBankcardBinResponse value) {
        return new JAXBElement<QueryBankcardBinResponse>(_QueryBankcardBinResponse_QNAME, QueryBankcardBinResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticateCardRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "authenticateCardRequest")
    public JAXBElement<AuthenticateCardRequest> createAuthenticateCardRequest(AuthenticateCardRequest value) {
        return new JAXBElement<AuthenticateCardRequest>(_AuthenticateCardRequest_QNAME, AuthenticateCardRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticateCardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "authenticateCardResponse")
    public JAXBElement<AuthenticateCardResponse> createAuthenticateCardResponse(AuthenticateCardResponse value) {
        return new JAXBElement<AuthenticateCardResponse>(_AuthenticateCardResponse_QNAME, AuthenticateCardResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthCardWithoutOTPRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "authCardWithoutOTPRequest")
    public JAXBElement<AuthCardWithoutOTPRequest> createAuthCardWithoutOTPRequest(AuthCardWithoutOTPRequest value) {
        return new JAXBElement<AuthCardWithoutOTPRequest>(_AuthCardWithoutOTPRequest_QNAME, AuthCardWithoutOTPRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthCardWithoutOTPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "authCardWithoutOTPResponse")
    public JAXBElement<AuthCardWithoutOTPResponse> createAuthCardWithoutOTPResponse(AuthCardWithoutOTPResponse value) {
        return new JAXBElement<AuthCardWithoutOTPResponse>(_AuthCardWithoutOTPResponse_QNAME, AuthCardWithoutOTPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityWithPhotoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityWithPhotoRequest")
    public JAXBElement<MatchIdentityWithPhotoRequest> createMatchIdentityWithPhotoRequest(MatchIdentityWithPhotoRequest value) {
        return new JAXBElement<MatchIdentityWithPhotoRequest>(_MatchIdentityWithPhotoRequest_QNAME, MatchIdentityWithPhotoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityWithPhotoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityWithPhotoResponse")
    public JAXBElement<MatchIdentityWithPhotoResponse> createMatchIdentityWithPhotoResponse(MatchIdentityWithPhotoResponse value) {
        return new JAXBElement<MatchIdentityWithPhotoResponse>(_MatchIdentityWithPhotoResponse_QNAME, MatchIdentityWithPhotoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchTwoPhotosRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchTwoPhotosRequest")
    public JAXBElement<MatchTwoPhotosRequest> createMatchTwoPhotosRequest(MatchTwoPhotosRequest value) {
        return new JAXBElement<MatchTwoPhotosRequest>(_MatchTwoPhotosRequest_QNAME, MatchTwoPhotosRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityWithPhotoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchTwoPhotosResponse")
    public JAXBElement<MatchIdentityWithPhotoResponse> createMatchTwoPhotosResponse(MatchIdentityWithPhotoResponse value) {
        return new JAXBElement<MatchIdentityWithPhotoResponse>(_MatchTwoPhotosResponse_QNAME, MatchIdentityWithPhotoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityRequest")
    public JAXBElement<MatchIdentityRequest> createMatchIdentityRequest(MatchIdentityRequest value) {
        return new JAXBElement<MatchIdentityRequest>(_MatchIdentityRequest_QNAME, MatchIdentityRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityResponse")
    public JAXBElement<MatchIdentityResponse> createMatchIdentityResponse(MatchIdentityResponse value) {
        return new JAXBElement<MatchIdentityResponse>(_MatchIdentityResponse_QNAME, MatchIdentityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityWithPhotoRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityWithLivePhotoRequest")
    public JAXBElement<MatchIdentityWithPhotoRequest> createMatchIdentityWithLivePhotoRequest(MatchIdentityWithPhotoRequest value) {
        return new JAXBElement<MatchIdentityWithPhotoRequest>(_MatchIdentityWithLivePhotoRequest_QNAME, MatchIdentityWithPhotoRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityWithPhotoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityWithLivePhotoResponse")
    public JAXBElement<MatchIdentityWithPhotoResponse> createMatchIdentityWithLivePhotoResponse(MatchIdentityWithPhotoResponse value) {
        return new JAXBElement<MatchIdentityWithPhotoResponse>(_MatchIdentityWithLivePhotoResponse_QNAME, MatchIdentityWithPhotoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityReturnPhotoRequest")
    public JAXBElement<MatchIdentityRequest> createMatchIdentityReturnPhotoRequest(MatchIdentityRequest value) {
        return new JAXBElement<MatchIdentityRequest>(_MatchIdentityReturnPhotoRequest_QNAME, MatchIdentityRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchIdentityReturnPhotoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "matchIdentityReturnPhotoResponse")
    public JAXBElement<MatchIdentityReturnPhotoResponse> createMatchIdentityReturnPhotoResponse(MatchIdentityReturnPhotoResponse value) {
        return new JAXBElement<MatchIdentityReturnPhotoResponse>(_MatchIdentityReturnPhotoResponse_QNAME, MatchIdentityReturnPhotoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyCardOtpRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "verifyCardOtpRequest")
    public JAXBElement<VerifyCardOtpRequest> createVerifyCardOtpRequest(VerifyCardOtpRequest value) {
        return new JAXBElement<VerifyCardOtpRequest>(_VerifyCardOtpRequest_QNAME, VerifyCardOtpRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyCardOtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.cloudauth.xwf.com/uid", name = "verifyCardOtpResponse")
    public JAXBElement<VerifyCardOtpResponse> createVerifyCardOtpResponse(VerifyCardOtpResponse value) {
        return new JAXBElement<VerifyCardOtpResponse>(_VerifyCardOtpResponse_QNAME, VerifyCardOtpResponse.class, null, value);
    }

}
