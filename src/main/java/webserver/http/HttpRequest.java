package webserver.http;

import enums.HttpMethod;

public interface HttpRequest {

    HttpMethod getMethod();

    String getPath();

    String getParameter(String name);

    String[] getParameterValues(String name);

    String getHeader(String name);

    String getBody();

    String getRequestURI();
}
