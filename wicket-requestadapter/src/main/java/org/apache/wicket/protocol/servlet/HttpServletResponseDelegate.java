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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.protocol.Cookie;
import org.apache.wicket.protocol.IHttpResponse;

public class HttpServletResponseDelegate implements IHttpResponse
{
	private final HttpServletResponse response;

	public HttpServletResponseDelegate(HttpServletResponse response)
	{
		this.response = response;
	}

	@Override
	public void addCookie(Cookie cookie)
	{
		response.addCookie(HttpServletCookies.toServletCookie(cookie));
	}

	public boolean containsHeader(String name)
	{
		return response.containsHeader(name);
	}

	@Override
	public String encodeURL(String url)
	{
		return response.encodeURL(url);
	}

	public String getCharacterEncoding()
	{
		return response.getCharacterEncoding();
	}

	@Override
	public String encodeRedirectURL(String url)
	{
		return response.encodeRedirectURL(url);
	}

	public String getContentType()
	{
		return response.getContentType();
	}

	public String encodeUrl(String url)
	{
		return response.encodeUrl(url);
	}

	public String encodeRedirectUrl(String url)
	{
		return response.encodeRedirectUrl(url);
	}

	@Override
	public OutputStream getOutputStream() throws IOException
	{
		return response.getOutputStream();
	}

	@Override
	public void sendError(int sc, String msg) throws IOException
	{
		response.sendError(sc, msg);
	}

	@Override
	public PrintWriter getWriter() throws IOException
	{
		return response.getWriter();
	}

	@Override
	public void sendError(int sc) throws IOException
	{
		response.sendError(sc);
	}

	@Override
	public void sendRedirect(String location) throws IOException
	{
		response.sendRedirect(location);
	}

	public void setCharacterEncoding(String charset)
	{
		response.setCharacterEncoding(charset);
	}

	@Override
	public void setDateHeader(String name, long date)
	{
		response.setDateHeader(name, date);
	}

	public void addDateHeader(String name, long date)
	{
		response.addDateHeader(name, date);
	}

	@Override
	public void setHeader(String name, String value)
	{
		response.setHeader(name, value);
	}

	public void setContentLength(int len)
	{
		response.setContentLength(len);
	}

	@Override
	public void setContentType(String type)
	{
		response.setContentType(type);
	}

	@Override
	public void addHeader(String name, String value)
	{
		response.addHeader(name, value);
	}

	public void setIntHeader(String name, int value)
	{
		response.setIntHeader(name, value);
	}

	public void addIntHeader(String name, int value)
	{
		response.addIntHeader(name, value);
	}

	public void setBufferSize(int size)
	{
		response.setBufferSize(size);
	}

	@Override
	public void setStatus(int sc)
	{
		response.setStatus(sc);
	}

	public void setStatus(int sc, String sm)
	{
		response.setStatus(sc, sm);
	}

	public int getBufferSize()
	{
		return response.getBufferSize();
	}

	@Override
	public void flushBuffer() throws IOException
	{
		response.flushBuffer();
	}

	public void resetBuffer()
	{
		response.resetBuffer();
	}

	@Override
	public boolean isCommitted()
	{
		return response.isCommitted();
	}

	@Override
	public void reset()
	{
		response.reset();
	}

	public void setLocale(Locale loc)
	{
		response.setLocale(loc);
	}

	public Locale getLocale()
	{
		return response.getLocale();
	}


}
