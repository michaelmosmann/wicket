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
package org.apache.wicket.protocol;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.wicket.protocol.servlet._HaveToRefactor;

public interface IHttpRequest
{
	String getCharacterEncoding();

	String getHeader(String id);

	void setCharacterEncoding(String encoding) throws UnsupportedEncodingException;

	Object getAttribute(String name);

	String getRequestURI();

	String getScheme();

	String getContextPath();

	String getServerName();

	int getServerPort();

	String getQueryString();

	@_HaveToRefactor
	Cookie[] getCookies();

	Locale getLocale();

	long getDateHeader(String name);

	Enumeration<String> getHeaders(String name);

	Map<String, String[]> getParameterMap();

}
