package org.apache.wicket.protocol;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class HttpSessionCopy implements IHttpSession
{
//	private final long creationTime;
	private final ConcurrentHashMap<String, Object> attributes;
	private final String sessionId;
//	private final ServletContext servletContext;
	private int maxInactiveInterval;

	public HttpSessionCopy(final IHttpSession originalSession) {
		this.sessionId = originalSession.getId();
//		this.servletContext = originalSession.getServletContext();
//		this.creationTime = originalSession.getCreationTime();

		this.attributes = new ConcurrentHashMap<String, Object>();
		Enumeration<String> attributeNames = originalSession.getAttributeNames();
		while (attributeNames.hasMoreElements())
		{
			String attributeName = attributeNames.nextElement();
			Object attributeValue = originalSession.getAttribute(attributeName);
			attributes.put(attributeName, attributeValue);
		}

	}

	public void destroy(){
		attributes.clear();
	}

//	@Override
//	public long getCreationTime() {
//		return creationTime;
//	}

	@Override
	public String getId() {
		return sessionId;
	}

//	// TODO: Not supported for now. Must update on every WebSocket Message
//	@Override
//	public long getLastAccessedTime() {
//		return 0;
//	}

//	@Override
//	public ServletContext getServletContext() {
//		return servletContext;
//	}
//
//	@Override
//	public void setMaxInactiveInterval(int interval) {
//		this.maxInactiveInterval = interval;
//	}
//
//	@Override
//	public int getMaxInactiveInterval() {
//		return maxInactiveInterval;
//	}

//	@Override
//	public HttpSessionContext getSessionContext() {
//		return null;
//	}

	@Override
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

//	@Override
//	public Object getValue(String name) {
//		return attributes.get(name);
//	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return attributes.keys();
	}

//	@Override
//	public String[] getValueNames() {
//		return (String[]) Collections.list(attributes.keys()).toArray();
//	}

	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

//	@Override
//	public void putValue(String name, Object value) {
//		attributes.put(name, value);
//	}

	@Override
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

//	@Override
//	public void removeValue(String name) {
//		attributes.remove(name);
//	}
//
//	// TODO: Not supported for now.
//	@Override
//	public void invalidate() {
//	}
//
//	@Override
//	public boolean isNew() {
//		return false;
//	}

}
