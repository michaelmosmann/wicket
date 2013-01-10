/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.protocol.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.wicket.protocol.IHttpRequest;

public class HttpServletRequestDelegate implements IHttpRequest
{
	private final HttpServletRequest request;

	public Object getAttribute(String name)
	{
		return request.getAttribute(name);
	}

	public String getAuthType()
	{
		return request.getAuthType();
	}

	public Cookie[] getCookies()
	{
		return request.getCookies();
	}

	public Enumeration getAttributeNames()
	{
		return request.getAttributeNames();
	}

	public long getDateHeader(String name)
	{
		return request.getDateHeader(name);
	}

	@Override
	public String getCharacterEncoding()
	{
		return request.getCharacterEncoding();
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException
	{
		request.setCharacterEncoding(env);
	}

	public String getHeader(String name)
	{
		return request.getHeader(name);
	}

	public int getContentLength()
	{
		return request.getContentLength();
	}

	public String getContentType()
	{
		return request.getContentType();
	}

	public Enumeration getHeaders(String name)
	{
		return request.getHeaders(name);
	}

	public ServletInputStream getInputStream() throws IOException
	{
		return request.getInputStream();
	}

	public String getParameter(String name)
	{
		return request.getParameter(name);
	}

	public Enumeration getHeaderNames()
	{
		return request.getHeaderNames();
	}

	public int getIntHeader(String name)
	{
		return request.getIntHeader(name);
	}

	public Enumeration getParameterNames()
	{
		return request.getParameterNames();
	}

	public String getMethod()
	{
		return request.getMethod();
	}

	public String[] getParameterValues(String name)
	{
		return request.getParameterValues(name);
	}

	public String getPathInfo()
	{
		return request.getPathInfo();
	}

	public Map getParameterMap()
	{
		return request.getParameterMap();
	}

	public String getProtocol()
	{
		return request.getProtocol();
	}

	public String getPathTranslated()
	{
		return request.getPathTranslated();
	}

	public String getScheme()
	{
		return request.getScheme();
	}

	public String getContextPath()
	{
		return request.getContextPath();
	}

	public String getServerName()
	{
		return request.getServerName();
	}

	public int getServerPort()
	{
		return request.getServerPort();
	}

	public BufferedReader getReader() throws IOException
	{
		return request.getReader();
	}

	public String getQueryString()
	{
		return request.getQueryString();
	}

	public String getRemoteUser()
	{
		return request.getRemoteUser();
	}

	public String getRemoteAddr()
	{
		return request.getRemoteAddr();
	}

	public String getRemoteHost()
	{
		return request.getRemoteHost();
	}

	public boolean isUserInRole(String role)
	{
		return request.isUserInRole(role);
	}

	public void setAttribute(String name, Object o)
	{
		request.setAttribute(name, o);
	}

	public Principal getUserPrincipal()
	{
		return request.getUserPrincipal();
	}

	public String getRequestedSessionId()
	{
		return request.getRequestedSessionId();
	}

	public void removeAttribute(String name)
	{
		request.removeAttribute(name);
	}

	public String getRequestURI()
	{
		return request.getRequestURI();
	}

	public Locale getLocale()
	{
		return request.getLocale();
	}

	public Enumeration getLocales()
	{
		return request.getLocales();
	}

	public StringBuffer getRequestURL()
	{
		return request.getRequestURL();
	}

	public boolean isSecure()
	{
		return request.isSecure();
	}

	public RequestDispatcher getRequestDispatcher(String path)
	{
		return request.getRequestDispatcher(path);
	}

	public String getServletPath()
	{
		return request.getServletPath();
	}

	public HttpSession getSession(boolean create)
	{
		return request.getSession(create);
	}

	public String getRealPath(String path)
	{
		return request.getRealPath(path);
	}

	public int getRemotePort()
	{
		return request.getRemotePort();
	}

	public String getLocalName()
	{
		return request.getLocalName();
	}

	public HttpSession getSession()
	{
		return request.getSession();
	}

	public String getLocalAddr()
	{
		return request.getLocalAddr();
	}

	public boolean isRequestedSessionIdValid()
	{
		return request.isRequestedSessionIdValid();
	}

	public int getLocalPort()
	{
		return request.getLocalPort();
	}

	public boolean isRequestedSessionIdFromCookie()
	{
		return request.isRequestedSessionIdFromCookie();
	}

	public boolean isRequestedSessionIdFromURL()
	{
		return request.isRequestedSessionIdFromURL();
	}

	public boolean isRequestedSessionIdFromUrl()
	{
		return request.isRequestedSessionIdFromUrl();
	}

	public HttpServletRequestDelegate(HttpServletRequest request)
	{
		this.request = request;
	}
}
