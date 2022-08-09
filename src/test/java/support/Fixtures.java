package support;

import java.util.List;
import model.User;
import webserver.request.HttpRequest;
import webserver.request.RequestBody;

public class Fixtures {
    public static HttpRequest createHttpRequest(String cookieString) {
        return new HttpRequest(List.of(
                String.format("POST /user/create HTTP/1.1\n" +
                        "Host: localhost:8080\n" +
                        "Connection: keep-alive\n" +
                        "Content-Length: 59\n" +
                        "Content-Type: application/x-www-form-urlencoded\n" +
                        "Cookie: %s\n" +
                        "Accept: */*\n", cookieString
                ).split("\n")
        ));
    }

    public static User createUser() {
        return new User("id", "password", "name", "email");
    }

    public static RequestBody createRequestBody() {
        return new RequestBody("userId=id&password=password&name=name&email=email");
    }
}
