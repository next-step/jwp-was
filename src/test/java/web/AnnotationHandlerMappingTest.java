package web;

import annotations.Controller;
import annotations.RequestMapping;
import http.HttpRequest;
import org.junit.jupiter.api.Test;
import web.method.InvocableHandlerMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationHandlerMappingTest {

    @Test
    public void getHandlerTest() throws IOException, NoSuchMethodException {
        HttpRequest httpRequest = HttpRequest.from(new BufferedReader(new StringReader("GET /users/mypage HTTP/1.1")));

        TestClass testClass = new TestClass();
        Map<Class<?>, Object> controllers = new LinkedHashMap<>();
        controllers.put(TestClass.class, testClass);

        InvocableHandlerMethod testHandlerMethod = InvocableHandlerMethod.from(testClass, TestClass.class.getMethod("mypage"));

        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping(controllers);
        InvocableHandlerMethod invocableHandlerMethod = annotationHandlerMapping.getHandler(httpRequest);

        assertThat(invocableHandlerMethod).isEqualTo(testHandlerMethod);

    }

    @Controller
    @RequestMapping("/users")
    public static class TestClass {

        @RequestMapping("/mypage")
        public String mypage() {
            return "/users/mypage";
        }
    }
}
