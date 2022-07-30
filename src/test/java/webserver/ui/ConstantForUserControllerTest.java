package webserver.ui;

public class ConstantForUserControllerTest {
    public static final String POST_USER_CREATE_REQUEST ="POST /user/create HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Length: 59\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\n";

    public static final String GET_USER_CREATE_REQUEST = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Accept: */*\n";

    public static final String POST_USER_LOGIN_REQUEST ="POST /user/login HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Content-Length: 33\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=password\n";

    public static final String POST_USER_LOGIN_REQUEST_WITH_INVALID_PASSWORD ="POST /user/login HTTP/1.1\n" +
            "Host: localhost:8080\n" +
            "Connection: keep-alive\n" +
            "Content-Type: application/x-www-form-urlencoded\n" +
            "Content-Length: 33\n" +
            "Accept: */*\n" +
            "\n" +
            "userId=javajigi&password=invalid\n";

}
