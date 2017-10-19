package com.museumbooking.volleyservice;


import com.museumbooking.model.MuseumModel;

import java.util.Map;

/**
 * Parameter class for service details
 * @author Aswathy_G
 *
 * @param <T>
 */

public class InvokerParams <T extends MuseumModel> extends MuseumModel {
	
	private static final long serialVersionUID = 2282228345343588570L;
	private Class<T> beanClazz;
	/** Service to invoke. */
	private String serviceToInvoke;
	/**
	 * Get, Post etc.
	 */
	private HttpMethod httpMethod;
	
	/** http, https. */
	private HttpProtocol httpProtocol;

	/** Parameters required. */
	private Map<String, DataPart> multiPartParameters;

	/** Parameters required. */
	private Map<String, String> urlParameters;
	
	/**
	 *  The additional request information required.
	 */
	private String additionalRequestInformation;
	
	public Class<T> getBeanClazz() {
		return beanClazz;
	}

	public void setBeanClazz(Class<T> beanClazz) {
		this.beanClazz = beanClazz;
	}

	public Map<String, DataPart> getMultiPartParameters() {
		return multiPartParameters;
	}

	public void setMultiPartParameters(Map<String, DataPart> multiPartParameters) {
		this.multiPartParameters = multiPartParameters;
	}

	public String getServiceToInvoke() {
		return serviceToInvoke;
	}

	public void setServiceToInvoke(String serviceToInvoke) {
		this.serviceToInvoke = serviceToInvoke;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public HttpProtocol getHttpProtocol() {
		return httpProtocol;
	}

	public void setHttpProtocol(HttpProtocol httpProtocol) {
		this.httpProtocol = httpProtocol;
	}

	public Map<String, String> getUrlParameters() {
		return urlParameters;
	}

	public void setUrlParameters(Map<String, String> urlParameters) {
		this.urlParameters = urlParameters;
	}

	public String getAdditionalRequestInformation() {
		return additionalRequestInformation;
	}

	public void setAdditionalRequestInformation(String additionalRequestInformation) {
		this.additionalRequestInformation = additionalRequestInformation;
	}
	
}
