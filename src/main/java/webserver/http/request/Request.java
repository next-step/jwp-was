package webserver.http.request;

import webserver.http.HttpSession;

import java.util.UUID;

public interface Request {

    String getPath();

    Method getMethod();

    RequestParameters getRequestParameters();

    RequestBody getRequestBody();

    String getHeader(String key);

    String getCookie(String key);

    HttpSession getSession();
}
