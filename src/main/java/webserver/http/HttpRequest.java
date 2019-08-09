package webserver.http;

import enums.HttpMethod;
import webserver.handler.ModelView;

public interface HttpRequest {

    HttpMethod getMethod();

    String getPath();

    String getParameter(String name);

    String[] getParameterValues(String name);

    String getHeader(String name);

    String getBody();

    String getRequestURI();

	ModelView getModelView();

	HttpCookie getCookie(String cookieName);

	HttpSession getSession();

	HttpSession getSession(boolean create);

	boolean hasNewSession();
}
