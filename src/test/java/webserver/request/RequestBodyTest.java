package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("요청 바디 테스트")
class RequestBodyTest {

    @DisplayName("요청 바디 객체 생성")
    @Test
    void createRequestBody() {
        assertAll(
                () -> assertThatNoException().isThrownBy(
                        () -> RequestBody.from("userId=javajigi&password=password&name=JaeSung")),
                () -> assertThatNoException().isThrownBy(
                        () -> RequestBody.from(""))
        );
    }
}
