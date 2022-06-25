package webserver.http.request;

public interface Request {

    String getPath();

    Method getMethod();

    RequestParameters getRequestParameters();

    RequestBody getRequestBody();

    String getHeader(String key);

    String getCookie(String key);
}
