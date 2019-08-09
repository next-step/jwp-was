package webserver.http.request;

import webserver.http.HttpMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public interface Request {
    String getPath();
    String getHeader(String key);
    String getParameter(String key);
    HttpMethod getMethod();
    void process(InputStream in) throws IOException;

}
