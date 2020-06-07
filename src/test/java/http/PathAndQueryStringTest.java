package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathAndQueryStringTest {
    @Test
    void createQueryString() {
        PathAndQueryString pathAndQueryString = PathAndQuerySpliter.splitPath("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(pathAndQueryString.getPath()).isEqualTo("/users");
        assertThat(pathAndQueryString.getQueryString()).isEqualTo(new QueryString("userId=javajigi&password=password&name=JaeSung"));
    }

    @Test
    void isSignUrl() {
        PathAndQueryString pathAndQueryString = PathAndQuerySpliter.splitPath("/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
        assertTrue(pathAndQueryString.isSignUrl());
    }
}
