package org.apache.wicket.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import org.apache.wicket.protocol.servlet._HaveToRefactor;

public class HttpRequestWrapper implements IHttpRequest
{
	private final IHttpRequest request;

	public HttpRequestWrapper(IHttpRequest request)
	{
		this.request = request;
	}
	
	@Override
	public String getCharacterEncoding()
	{
		return request.getCharacterEncoding();
	}

	@Override
	public String getHeader(String id)
	{
		return request.getHeader(id);
	}

	@Override
	public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding(encoding);
	}

	@Override
	public Object getAttribute(String name)
	{
		return request.getAttribute(name);
	}

	@Override
	public String getRequestURI()
	{
		return request.getRequestURI();
	}

	@Override
	public String getScheme()
	{
		return request.getScheme();
	}

	@Override
	public String getContextPath()
	{
		return request.getContextPath();
	}

	@Override
	public String getServerName()
	{
		return request.getServerName();
	}

	@Override
	public int getServerPort()
	{
		return request.getServerPort();
	}

	@Override
	public String getQueryString()
	{
		return request.getQueryString();
	}

	@Override
	@_HaveToRefactor
	public Cookie[] getCookies()
	{
		return request.getCookies();
	}

	@Override
	public Locale getLocale()
	{
		return request.getLocale();
	}

	@Override
	public long getDateHeader(String name)
	{
		return request.getDateHeader(name);
	}

	@Override
	public Enumeration getHeaders(String name)
	{
		return request.getHeaders(name);
	}

	@Override
	public Map<String, String[]> getParameterMap()
	{
		return request.getParameterMap();
	}

	@Override
	public String getMethod()
	{
		return request.getMethod();
	}

	@Override
	public String getContentType()
	{
		return request.getContentType();
	}

	@Override
	public int getContentLength()
	{
		return request.getContentLength();
	}

	@Override
	public InputStream getInputStream() throws IOException
	{
		return request.getInputStream();
	}

	@Override
	public IHttpSession getSession(boolean create)
	{
		return request.getSession(create);
	}

	@Override
	public IHttpSession getSession()
	{
		return request.getSession();
	}

	@Override
	public boolean isSecure()
	{
		return request.isSecure();
	}

	@Override
	public String getRemoteAddr()
	{
		return request.getRemoteAddr();
	}

	@Override
	public int getIntHeader(String name)
	{
		return request.getIntHeader(name);
	}

	@Override
	public String getRemoteHost()
	{
		return request.getRemoteHost();
	}

	@Override
	public Enumeration<?> getHeaderNames()
	{
		return request.getHeaderNames();
	}

	@Override
	public String getParameter(String name)
	{
		return request.getParameter(name);
	}

	@Override
	public Enumeration getAttributeNames()
	{
		return request.getAttributeNames();
	}

	@Override
	public Enumeration getParameterNames()
	{
		return request.getParameterNames();
	}
	
	@Override
	public String[] getParameterValues(String name)
	{
		return request.getParameterValues(name);
	}

	@Override
	public Enumeration<Locale> getLocales()
	{
		return request.getLocales();
	}

	@Override
	public String getRequestedSessionId()
	{
		return request.getRequestedSessionId();
	}

	@Override
	public String getServletPath()
	{
		return request.getServletPath();
	}
}
