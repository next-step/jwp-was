package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestMethodPostTest {

    private RequestMethodPost requestMethodPost;

    @BeforeEach
    void setUp() {
        requestMethodPost = new RequestMethodPost("/users");
    }

    @Test
    @DisplayName("객체 생성")
    void createObject() {
        // give when
        boolean same = requestMethodPost.equals(new RequestMethodPost("/users"));
        // then
        assertThat(same).isTrue();
    }

    @Test
    @DisplayName("객체 상태 값")
    void getMethodNameAndPath() {
        // give
        String methodName = requestMethodPost.getMethodName();
        String path = requestMethodPost.getPath();
        // when then
        assertAll("method name and path",
                () -> assertThat(methodName).isEqualTo("POST"),
                () -> assertThat(path).isEqualTo("/users")
                );
    }
}
