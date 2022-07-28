package webserver.http.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpStatusTest {

    @Test
    void fullStatusCode() {
        assertAll(
                () -> assertThat(HttpStatus.OK.fullStatusCode()).isEqualTo("200 OK"),
                () -> assertThat(HttpStatus.NOT_FOUND.fullStatusCode()).isEqualTo("404 Not Found")
        );
    }
}
