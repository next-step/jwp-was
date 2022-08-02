package webserver.request.domain.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HeaderTest {

    @Test
    @DisplayName("Header 생성 테스트")
    public void createRequestHeader () {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "localhost:8080");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Accept", "*/*");
        Header header = new Header(headerMap);

        assertThat(header.getHeader("Host")).isEqualTo("localhost:8080");
        assertThat(header.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(header.getHeader("Accept")).isEqualTo("*/*");
    }

}
