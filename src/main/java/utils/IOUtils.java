package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class IOUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    /**
     * @param BufferedReader 는
     *            Request Body를 시작하는 시점이어야
     * @param contentLength 는
     *            Request Header의 Content-Length 값이다.
     * @return
     * @throws IOException
     */
    public static String readData(BufferedReader br, int contentLength) throws IOException {
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

    public static HttpRequest convertRequest(InputStream is) throws IOException {
        return HttpRequest.parsing(getBufferedReader(is));
    }


    private static BufferedReader getBufferedReader(InputStream is) {
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

    public static Map<String, String> changeStringToMap(String queryString) {
        String[] querySplit = queryString.split("\\&");
        Map<String, String> requestMap = new HashMap<>();
        for (String couple : querySplit) {
            String[] keyValue = couple.split("=");
            requestMap.put(keyValue[0], keyValue[1]);
        }
        return requestMap;
    }
}
