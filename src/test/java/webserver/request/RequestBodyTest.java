package webserver.request;

import http.request.RequestBody;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayName("요청 바디 테스트")
class RequestBodyTest {

    @DisplayName("요청 바디 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"", "userId=javajigi&password=password&name=JaeSung"})
    void createRequestBody(String url) {
        assertThatNoException().isThrownBy(() -> RequestBody.from(url));
    }
}
