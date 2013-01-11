package org.apache.wicket.protocol.servlet;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.IHttpContext;

public class ServletContextDelegate implements IHttpContext
{
	private final ServletContext context;

	public ServletContextDelegate(ServletContext context)
	{
		this.context = context;
	}

	@Override
	public String getContextPath()
	{
		return context.getContextPath();
	}
	
	
}
