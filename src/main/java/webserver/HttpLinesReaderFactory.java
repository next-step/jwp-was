package webserver;

import webserver.http.request.requestline.HttpMethod;

import java.io.BufferedReader;

public class HttpLinesReaderFactory {
    public static HttpLinesReaderStrategy create(BufferedReader bufferedReader, HttpMethod httpMethod) {
        switch (httpMethod) {
            case GET:
                return new HttpGetMethodLinesReader(bufferedReader);
            case POST:
                return new HttpPostMethodLinesReader(bufferedReader);
            default:
                throw new IllegalArgumentException("지원하지 않는 HTTP 메소드입니다.");
        }
    }
}
