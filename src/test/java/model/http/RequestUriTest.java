package model.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RequestUriTest {
    @ParameterizedTest
    @ValueSource(strings = {"/",
            "/user?ssosso1=ssosso1&s_2=s__2",
            "/user/Id/test-test/?ssosso1=ssoss2",
            "/user/id"})
    void of(String requestUri) {
        RequestUri.of(requestUri);
    }
}
