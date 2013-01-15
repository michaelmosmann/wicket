package org.apache.wicket.protocol;

import java.util.Enumeration;

public interface IHttpSession
{

	void setAttribute(String name, Object value);

	Object getAttribute(String name);

	void removeAttribute(String name);

	String getId();

	Enumeration getAttributeNames();

	IHttpContext getServletContext();

	void invalidate();

	
}
