package http;

public class TestConstant {
    public static final String CREATE_USER_REQUEST = "POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 53\n" +
            "Cache-Control: max-age=0\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "Origin: http://localhost:8080\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
            "Sec-Fetch-Site: same-origin\n" +
            "Sec-Fetch-Mode: navigate\n" +
            "Sec-Fetch-User: ?1\n" +
            "Sec-Fetch-Dest: document\n" +
            "Referer: http://localhost:8080/user/form.html\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko,ja;q=0.9,en-US;q=0.8,en;q=0.7,ko-KR;q=0.6\n" +
            "\n" +
            "userId=seul&password=test&name=Eeseul Park&email=seul";
    public static final String USER_LIST_REQUEST = "GET /user/list.html HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Cache-Control: max-age=0\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
            "Sec-Fetch-Site: none\n" +
            "Sec-Fetch-Mode: navigate\n" +
            "Sec-Fetch-User: ?1\n" +
            "Sec-Fetch-Dest: document\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko,ja;q=0.9,en-US;q=0.8,en;q=0.7,ko-KR;q=0.6\n" +
            "Cookie: logined=true\n" +
            "\n";
    public static final String USER_LOGIN_REQUEST = "POST /user/login HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 53\n" +
            "Cache-Control: max-age=0\n" +
            "Upgrade-Insecure-Requests: 1\n" +
            "Origin: http://localhost:8080\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
            "Sec-Fetch-Site: same-origin\n" +
            "Sec-Fetch-Mode: navigate\n" +
            "Sec-Fetch-User: ?1\n" +
            "Sec-Fetch-Dest: document\n" +
            "Referer: http://localhost:8080/user/form.html\n" +
            "Accept-Encoding: gzip, deflate, br\n" +
            "Accept-Language: ko,ja;q=0.9,en-US;q=0.8,en;q=0.7,ko-KR;q=0.6\n" +
            "\n" +
            "userId=seul&password=test";
}
