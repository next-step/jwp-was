package webserver.http.request;

public interface Request {

    String getPath();

    Method getMethod();

    QueryString getQueryString();
}
