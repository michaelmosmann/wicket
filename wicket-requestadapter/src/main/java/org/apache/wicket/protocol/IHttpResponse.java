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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public interface IHttpResponse
{

	void addCookie(Cookie cookie);

	void addHeader(String name, String value);

	void setContentType(String type);

	void setDateHeader(String name, long date);

	void setHeader(String name, String value);

	PrintWriter getWriter() throws IOException;

	OutputStream getOutputStream() throws IOException;

	void setStatus(int sc);

	void sendError(int sc, String msg) throws IOException;

	void sendError(int sc) throws IOException;

	String encodeURL(String url);

	String encodeRedirectURL(String url);

	void sendRedirect(String location) throws IOException;

	void flushBuffer() throws IOException;

	boolean isCommitted();

	void reset();

}
