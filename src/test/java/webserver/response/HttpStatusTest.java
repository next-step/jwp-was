package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpStatusTest {

    @DisplayName("HttpStatus 는 String 으로 변환할 수 있어야 한다.")
    @Test
    void testToString() {
        assertAll(
                () -> assertThat(HttpStatus.OK.toString()).isEqualTo("200 OK"),
                () -> assertThat(HttpStatus.FOUND.toString()).isEqualTo("302 Found"),
                () -> assertThat(HttpStatus.BAD_REQUEST.toString()).isEqualTo("400 Bad Request"),
                () -> assertThat(HttpStatus.UNAUTHORIZED.toString()).isEqualTo("401 Unauthorized"),
                () -> assertThat(HttpStatus.NOT_FOUND.toString()).isEqualTo("404 Not Found"),
                () -> assertThat(HttpStatus.INTERNAL_SERVER_ERROR.toString()).isEqualTo("500 Internal Server Error"),
                () -> assertThat(HttpStatus.NOT_IMPLEMENTED.toString()).isEqualTo("501 Not Implemented")
        );
    }
}
