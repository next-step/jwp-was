package webserver.http.request;

import webserver.http.HeaderName;
import webserver.http.HttpMethod;
import webserver.http.cookie.Cookies;
import webserver.http.session.Session;

import java.util.Optional;

public interface Request {

    String getPath();
    boolean matchPath(final String regex);
    String getParameter(final String key);
    boolean matchMethod(final HttpMethod method);
    Optional<String> getHeader(final String key);
    Optional<String> getHeader(final HeaderName key);
    Cookies getCookies();
    Session getSession();
}
