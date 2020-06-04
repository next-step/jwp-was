package http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
