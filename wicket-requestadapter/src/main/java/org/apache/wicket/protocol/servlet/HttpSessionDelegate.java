package org.apache.wicket.protocol.servlet;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.wicket.protocol.IHttpSession;

public class HttpSessionDelegate implements IHttpSession
{
	private final HttpSession session;

	public HttpSessionDelegate(HttpSession session)
	{
		this.session = session;
	}

	public long getCreationTime()
	{
		return session.getCreationTime();
	}

	@Override
	public String getId()
	{
		return session.getId();
	}

	public long getLastAccessedTime()
	{
		return session.getLastAccessedTime();
	}

	public ServletContext getServletContext()
	{
		return session.getServletContext();
	}

	public void setMaxInactiveInterval(int interval)
	{
		session.setMaxInactiveInterval(interval);
	}

	public int getMaxInactiveInterval()
	{
		return session.getMaxInactiveInterval();
	}

	public HttpSessionContext getSessionContext()
	{
		return session.getSessionContext();
	}

	@Override
	public Object getAttribute(String name)
	{
		return session.getAttribute(name);
	}

	public Object getValue(String name)
	{
		return session.getValue(name);
	}

	@Override
	public Enumeration getAttributeNames()
	{
		return session.getAttributeNames();
	}

	public String[] getValueNames()
	{
		return session.getValueNames();
	}

	@Override
	public void setAttribute(String name, Object value)
	{
		session.setAttribute(name, value);
	}

	public void putValue(String name, Object value)
	{
		session.putValue(name, value);
	}

	@Override
	public void removeAttribute(String name)
	{
		session.removeAttribute(name);
	}

	public void removeValue(String name)
	{
		session.removeValue(name);
	}

	public void invalidate()
	{
		session.invalidate();
	}

	public boolean isNew()
	{
		return session.isNew();
	}
}
