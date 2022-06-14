package webserver.request;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestHeaderTest {

    private RequestHeader requestHeader;

    @BeforeEach
    void setUp() {
        requestHeader = RequestHeader.from(Arrays.asList(
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                "Content-Length: 64"
        ));
    }

    @DisplayName("RequestHeader 로 부터 Content-Type 을 가져올 수 있다.")
    @Test
    void getContentType() {
        assertThat(requestHeader.getContentType())
                .isEqualTo("text/html");
    }

    @DisplayName("RequestHeader 로 부터 Content-Length 를 가져올 수 있다.")
    @Test
    void getContentLength() {
        assertThat(requestHeader.getContentLength())
                .isEqualTo(64);
    }
}
