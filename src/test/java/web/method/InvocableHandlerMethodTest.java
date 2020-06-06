package web.method;

import http.HttpRequest;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class InvocableHandlerMethodTest {

    @Test
    public void initTest() throws NoSuchMethodException {
        InvocableHandlerMethod invocableHandlerMethod = InvocableHandlerMethod.of(
                new InvokeTestClass(),
                InvokeTestClass.class.getDeclaredMethod("getPath", HttpRequest.class));

        MethodParameter methodParameter = MethodParameter.of(InvokeTestClass.class.getDeclaredMethod("getPath", HttpRequest.class), 0);

        assertThat(invocableHandlerMethod.getParameters()).contains(methodParameter);
    }

    @Test
    public void invokeTest() throws NoSuchMethodException {
        InvocableHandlerMethod invocableHandlerMethod = InvocableHandlerMethod.of(
                new InvokeTestClass(),
                InvokeTestClass.class.getDeclaredMethod("getMessage"));

        assertThat(invocableHandlerMethod.invoke()).isEqualTo("Hello World");
    }

    @Test
    public void invokeWithHttpRequestTest() throws IOException, NoSuchMethodException {
        HttpRequest httpRequest = HttpRequest.from(new BufferedReader(new StringReader("GET /users/info HTTP/1.1")));

        InvocableHandlerMethod invocableHandlerMethod = InvocableHandlerMethod.of(
                new InvokeTestClass(),
                InvokeTestClass.class.getDeclaredMethod("getPath", HttpRequest.class));

        assertThat(invocableHandlerMethod.invoke(httpRequest)).isEqualTo("/users/info");
    }

    public static class InvokeTestClass {
        public String getMessage() {
            return "Hello World";
        }

        public String getPath(HttpRequest httpRequest) {
            return httpRequest.getPath();
        }
    }
}
