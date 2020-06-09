package http;

import org.junit.jupiter.api.Test;
import utils.HttpStringBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpHeadersTest {
    @Test
    public void createTest() {
        String headerString = HttpStringBuilder.builder()
                .addHeader(HeaderProperty.CONTENT_TYPE.getValue(), MediaType.APPLICATION_JSON.getValue())
                .addHeader(HeaderProperty.ACCEPT.getValue(), MediaType.APPLICATION_JSON.getValue())
                .addHeader(HeaderProperty.USER_AGENT.getValue(), "Mozilla/5.0")
                .buildHeaders();

        HttpHeaders httpHeaders = HttpHeaders.from(headerString);

        assertThat(httpHeaders.getHeader(HeaderProperty.CONTENT_TYPE.getValue())).isEqualTo(MediaType.APPLICATION_JSON.getValue());
        assertThat(httpHeaders.getHeader(HeaderProperty.ACCEPT.getValue())).isEqualTo(MediaType.APPLICATION_JSON.getValue());
        assertThat(httpHeaders.getHeader(HeaderProperty.USER_AGENT.getValue())).isEqualTo("Mozilla/5.0");
    }
}
