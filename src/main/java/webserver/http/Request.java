package webserver.http;

public interface Request {
    HttpMethod getMethod();

    String getPath();

    String getHeader(String name);

    String getParameter(String name);
}
