package webserver.http.request;

import java.io.BufferedReader;
import java.io.IOException;

public interface Request {
    String getPath();
    String getHeader(String key);
    String getParameter(String key);
    void process(BufferedReader br) throws IOException;

}
