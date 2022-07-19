package utils;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class RequestHeaderTest {
    RequestHeader requestHeader = RequestHeader.getInstance();
    @Test
    void parsing() {
        String data = "POST /user/create HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 59\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

        StringReader sr = new StringReader(data);
        BufferedReader br = new BufferedReader(sr);

        requestHeader.parsing(br);
    }
}