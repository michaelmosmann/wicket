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
package org.apache.wicket.request;

import java.nio.charset.Charset;
import java.util.Locale;

import org.apache.wicket.request.parameter.CombinedRequestParametersAdapter;
import org.apache.wicket.request.parameter.EmptyRequestParameters;
import org.apache.wicket.request.parameter.UrlRequestParametersAdapter;

/**
 * Request object.
 * 
 * @author Matej Knopp
 */
public abstract class Request
{
	/**
	 * Returns the URL for this request. URL is relative to Wicket filter path.
	 * 
	 * @return Url instance
	 */
	public abstract Url getUrl();

	/**
	 * In case this request has been created using {@link #requestWithUrl(Url)}, this method should
	 * return the original URL.
	 * 
	 * @return original URL
	 */
	public Url getOriginalUrl()
	{
		return getUrl();
	}

	/**
	 * @return POST request parameters for this request.
	 */
	public IRequestParameters getPostParameters()
	{
		return EmptyRequestParameters.INSTANCE;
	}

	/**
	 * @return GET request parameters for this request.
	 */
	public IRequestParameters getQueryParameters()
	{
		return new UrlRequestParametersAdapter(getUrl());
	}

	/**
	 * @return all request parameters for this request (both POST and GET parameters)
	 */
	public IRequestParameters getRequestParameters()
	{
		return new CombinedRequestParametersAdapter(getQueryParameters(), getPostParameters());
	}

	/**
	 * Returns locale for this request.
	 * 
	 * @return locale
	 */
	public abstract Locale getLocale();

	/**
	 * Returns request with specified URL and same POST parameters as this request.
	 * 
	 * @param url
	 *            Url instance
	 * @return request with specified URL.
	 */
	public Request requestWithUrl(final Url url)
	{
		return new Request()
		{
			@Override
			public Url getUrl()
			{
				return url;
			}

			@Override
			public Url getOriginalUrl()
			{
				return Request.this.getOriginalUrl();
			}

			@Override
			public Locale getLocale()
			{
				return Request.this.getLocale();
			}

			@Override
			public IRequestParameters getPostParameters()
			{
				return Request.this.getPostParameters();
			}

			@Override
			public Charset getCharset()
			{
				return Request.this.getCharset();
			}
		};
	}

	/**
	 * Returns prefix from Wicket Filter mapping to context path. This method does not take the
	 * actual URL into account.
	 * <p>
	 * For example if Wicket filter is mapped to hello/* this method should return ../ regardless of
	 * actual URL (after Wicket filter)
	 * 
	 * @return prefix to context path for this request.
	 * 
	 */
	public String getPrefixToContextPath()
	{
		return "";
	}

	public abstract Charset getCharset();
}