package webserver.request;

import java.io.BufferedReader;
import java.io.StringReader;

public class HelpData {
    public static BufferedReader postHelpData() {
        String data = "POST /users HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "Cookie: _ga=GA1.1.1851084625.1652927218; _ga_C3QBWSBLPT=GS1.1.1652943473.2.0.1652943473.60; wcs_bt=s_1a7f4b55d0bb \n" +
                "\n" +
                "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";
        StringReader sr = new StringReader(data);

        return new BufferedReader(sr);
    }

    public static BufferedReader getHelpData(String getMethod) {
        String data = getMethod + " /users?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 93\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n";
        StringReader sr = new StringReader(data);

        return new BufferedReader(sr);
    }


}
