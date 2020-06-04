package web;

import annotations.RequestMapping;
import http.HttpMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerKeyTest {

    @Test
    public void initTest() throws NoSuchMethodException {
        HandlerKey handlerKey = HandlerKey.from(HandlerKeyTest.class.getMethod("testMethod", String.class, String.class));

        assertThat(handlerKey.getPath()).isEqualTo("/users");
        assertThat(handlerKey.getHttpMethod()).isEqualTo(HttpMethod.POST);
    }

    @RequestMapping(value = "/users", method = HttpMethod.POST)
    public void testMethod(String s1, String s2) {

    }
}
