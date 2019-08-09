package webserver;

import webserver.request.Cookies;
import webserver.request.HttpMethod;
import webserver.response.HeaderProperty;

public interface Request {

    HttpMethod getMethod();

    String getPath();

    String getParameter(String field);

    Cookies getCookies();

    String getAccept();

    boolean matchPath(String path);

    boolean containPath(String path);

    String getHeader(HeaderProperty headerProperty);

    HttpSession getSession();

    String getCookie(String key);
}
