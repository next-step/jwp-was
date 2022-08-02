package webserver.util;

import webserver.http.HttpRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class HttpRequestUtil {

    public static HttpRequest createWithPostRequest(String path, String requestBody) throws UnsupportedEncodingException {
        String headerString = "POST " + path + " HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length:" + requestBody.getBytes().length + "\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n\n" +
                requestBody;
        InputStream inputStream = new ByteArrayInputStream(headerString.getBytes(Charset.forName("UTF-8")));
        return new HttpRequest(inputStream);
    }

    public static HttpRequest createWithGetRequest(String path) throws UnsupportedEncodingException {
        String headerString = "GET " + path + " HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n\n";
        InputStream inputStream = new ByteArrayInputStream(headerString.getBytes(Charset.forName("UTF-8")));
        return new HttpRequest(inputStream);
    }
}
