package webserver;

import controller.UserController;
import model.User;
import model.controller.ResponseEntity;
import model.http.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerMethodInvokerTest {
    @Test
    void test() throws Exception {
        Class<UserController> clazz = UserController.class;
        Method method = clazz.getDeclaredMethod("createUser", User.class);
        Query query = Query.of("userid=ssosso&password=ssosso_password&name=JangSoHyun&email=ssossohow@gmail.com");
        RequestLine requestLine = RequestLine.of(HttpMethod.GET, RequestUri.of(UriPath.of("/user/create"), query), HttpVersion.HTTP1_1);
        HttpRequest httpRequest = HttpRequest.of(HttpRequestHeader.of(requestLine));
        assertThat(ControllerMethodInvoker.invoke(method, httpRequest)).isEqualTo(ResponseEntity.of("/user/profile"));
    }
}
