package webserver;

import webserver.request.Cookie;
import webserver.request.HttpMethod;
import webserver.response.HeaderProperty;

public interface Request {

    HttpMethod getMethod();

    String getPath();

    String getParameter(String field);

    Cookie getCookie();

    String getAccept();

    boolean matchPath(String path);

    boolean containPath(String path);

    String getHeader(HeaderProperty headerProperty);
}
