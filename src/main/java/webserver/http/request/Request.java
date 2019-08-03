package webserver.http.request;

import webserver.http.RequestMethod;
import webserver.http.cookie.Cookie;

public interface Request {

    String getPath();
    boolean matchPath(final String regex);
    String getParameter(final String key);
    boolean matchMethod(final RequestMethod method);
    String getHeader(final String key);
    String getBody();
    Cookie getCookie();
}
