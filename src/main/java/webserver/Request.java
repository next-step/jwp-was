package webserver;

import webserver.request.Cookie;

public interface Request {

    String getPath();

    String getParameter(String field);

    Cookie getCookie();

    String getAccept();

    boolean matchPath(String path);

    boolean containPath(String path);
}
