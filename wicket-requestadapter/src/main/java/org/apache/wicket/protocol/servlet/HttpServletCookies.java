package org.apache.wicket.protocol.servlet;

import javax.servlet.http.Cookie;

public class HttpServletCookies
{
	public static Cookie toServletCookie(org.apache.wicket.protocol.Cookie cookie) {
		Cookie ret = new Cookie(cookie.getName(),cookie.getValue());
		ret.setComment(cookie.getComment());
		String domain = cookie.getDomain();
		if (domain!=null) ret.setDomain(domain);
		ret.setMaxAge(cookie.getMaxAge());
		ret.setPath(cookie.getPath());
		ret.setSecure(cookie.getSecure());
		ret.setVersion(cookie.getVersion());
		return ret;
	}
	
	public static org.apache.wicket.protocol.Cookie fromServletCookie(Cookie cookie) {
		org.apache.wicket.protocol.Cookie ret = new org.apache.wicket.protocol.Cookie(cookie.getName(),cookie.getValue());
		ret.setComment(cookie.getComment());
		String domain = cookie.getDomain();
		if (domain!=null) ret.setDomain(domain);
		ret.setMaxAge(cookie.getMaxAge());
		ret.setPath(cookie.getPath());
		ret.setSecure(cookie.getSecure());
		ret.setVersion(cookie.getVersion());
		return ret;
	}

	public static org.apache.wicket.protocol.Cookie[] fromServletCookies(Cookie[] cookies)
	{
		org.apache.wicket.protocol.Cookie[] ret=null;
		if (cookies!=null) {
			ret=new org.apache.wicket.protocol.Cookie[cookies.length];
			
			for (int i=0;i<cookies.length;i++) {
				ret[i]=fromServletCookie(cookies[i]);
			}
		}
		
		return ret;
	}
}
