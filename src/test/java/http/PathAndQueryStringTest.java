package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PathAndQueryStringTest {
    @Test
    void createQueryString() {
        
        PathAndQueryString pathAndQueryString = PathAndQuerySpliter.split("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(pathAndQueryString.getPath()).isEqualTo("/users");
        assertThat(pathAndQueryString.getQeuryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }
}
