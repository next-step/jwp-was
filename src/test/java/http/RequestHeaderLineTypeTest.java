package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestHeaderLineTypeTest {
    @Test
    void FIRST_LINE() {
        RequestHeaderLineType result = RequestHeaderLineType.parse("POST /user/create HTTP/1.1");
        assertThat(result).isEqualTo(RequestHeaderLineType.FIRST_LINE);
    }

    @Test
    void CONTENT_LENGTH_LINE() {
        RequestHeaderLineType result = RequestHeaderLineType.parse("Content-Length: 59");
        assertThat(result).isEqualTo(RequestHeaderLineType.CONTENT_LENGTH_LINE);
    }

    @Test
    void CONTENT_TYPE_LINE() {
        RequestHeaderLineType result = RequestHeaderLineType.parse("Content-Type: application/x-www-form-urlencoded");
        assertThat(result).isEqualTo(RequestHeaderLineType.CONTENT_TYPE_LINE);
    }

    @Test
    void EMPTY_LINE() {
        RequestHeaderLineType result = RequestHeaderLineType.parse("");
        assertThat(result).isEqualTo(RequestHeaderLineType.EMPTY_LINE);
    }
}
