package webserver.http.request;

import webserver.http.HttpSession;

public interface Request {

    String getPath();

    Method getMethod();

    RequestParameters getRequestParameters();

    RequestBody getRequestBody();

    String getHeader(String key);

    String getCookie(String key);

    HttpSession getSession();
}
