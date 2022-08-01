package webserver.http.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseStatusTest {

    @DisplayName("200 응답을 한다.")
    @Test
    void successTest() {
        assertThat(HttpResponseStatus.OK.toResponseHeader()).isEqualTo("200 OK");
    }
}