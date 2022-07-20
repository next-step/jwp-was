package webserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {

    private RequestLine requestLine = new RequestLine();
    
    @DisplayName("GET 요청에 대한 RequestLine을 통해 정상적 파싱되었다.")
    @Test
    void parsingGetRequest() {
        // given
        String method = "GET";
        String path = "/users";
        String protocol = "HTTP";
        String version = "1.1";
        String request = "GET /users HTTP/1.1";

        // when
        RequestLine result = requestLine.parse(request);
        
        // then
        assertAll(
                () -> assertThat(result.getMethod()).isEqualTo(method),
                () -> assertThat(result.getPath()).isEqualTo(path),
                () -> assertThat(result.getProtocol()).isEqualTo(protocol),
                () -> assertThat(result.getVersion()).isEqualTo(version)
        );
    }
}
