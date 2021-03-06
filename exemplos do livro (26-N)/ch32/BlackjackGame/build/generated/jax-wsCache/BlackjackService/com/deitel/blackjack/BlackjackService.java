
package com.deitel.blackjack;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "BlackjackService", targetNamespace = "http://blackjack.deitel.com/", wsdlLocation = "http://localhost:8080/Blackjack/BlackjackService?WSDL")
public class BlackjackService
    extends Service
{

    private final static URL BLACKJACKSERVICE_WSDL_LOCATION;
    private final static WebServiceException BLACKJACKSERVICE_EXCEPTION;
    private final static QName BLACKJACKSERVICE_QNAME = new QName("http://blackjack.deitel.com/", "BlackjackService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/Blackjack/BlackjackService?WSDL");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        BLACKJACKSERVICE_WSDL_LOCATION = url;
        BLACKJACKSERVICE_EXCEPTION = e;
    }

    public BlackjackService() {
        super(__getWsdlLocation(), BLACKJACKSERVICE_QNAME);
    }

    public BlackjackService(WebServiceFeature... features) {
        super(__getWsdlLocation(), BLACKJACKSERVICE_QNAME, features);
    }

    public BlackjackService(URL wsdlLocation) {
        super(wsdlLocation, BLACKJACKSERVICE_QNAME);
    }

    public BlackjackService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, BLACKJACKSERVICE_QNAME, features);
    }

    public BlackjackService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BlackjackService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Blackjack
     */
    @WebEndpoint(name = "BlackjackPort")
    public Blackjack getBlackjackPort() {
        return super.getPort(new QName("http://blackjack.deitel.com/", "BlackjackPort"), Blackjack.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Blackjack
     */
    @WebEndpoint(name = "BlackjackPort")
    public Blackjack getBlackjackPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://blackjack.deitel.com/", "BlackjackPort"), Blackjack.class, features);
    }

    private static URL __getWsdlLocation() {
        if (BLACKJACKSERVICE_EXCEPTION!= null) {
            throw BLACKJACKSERVICE_EXCEPTION;
        }
        return BLACKJACKSERVICE_WSDL_LOCATION;
    }

}
