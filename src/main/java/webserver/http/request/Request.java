package webserver.http.request;

public interface Request {

    String getPath();

    Method getMethod();

    RequestParameters getRequestParameters();

    RequestBody getRequestBody();

    String getCookie(String key);
}
