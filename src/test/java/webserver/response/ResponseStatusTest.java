package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResponseStatusTest {

    @DisplayName("200 응답을 한다.")
    @Test
    void successTest() {
        assertThat(ResponseStatus.SUCCESS.toResponseHeader()).isEqualTo("200 OK");
    }
}