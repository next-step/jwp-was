package webserver.http.request;

import webserver.http.HeaderName;
import webserver.http.HttpMethod;
import webserver.http.cookie.Cookies;

public interface Request {

    HttpMethod getMethod();
    String getPath();
    boolean matchPath(final String regex);
    String getParameter(final String key);
    boolean matchMethod(final HttpMethod method);
    String getHeader(final String key);
    String getHeader(final HeaderName key);
    Cookies getCookies();
}
