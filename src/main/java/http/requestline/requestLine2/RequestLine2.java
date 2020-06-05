package http.requestline.requestLine2;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class RequestLine2 {
    public static Method getMethod(String requestLine) {
        return Method.match(requestLine);
    }

    public static String getUrl(String requestLine) {
        return Path2.getUrl(requestLine);
    }

    public static Map<String, String> getQueries(String requestLine) {
        if(Method.isGet(requestLine)){
            return Path2.getQueries(requestLine);
        }
        return Collections.EMPTY_MAP;
    }

    public static String getProtocol(String requestLine) {
        return Protocol2.getProtocol(requestLine);
    }

    public static String getVersion(String requestLine) {
        return Protocol2.getVersion(requestLine);
    }
}
