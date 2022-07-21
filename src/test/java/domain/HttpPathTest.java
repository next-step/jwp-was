package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("HttpPath 단위 테스트")
class HttpPathTest {
    @DisplayName("경로 형식이 맞지 않으면 예외를 발생한다.")
    @ParameterizedTest(name = "{displayName} - {arguments}")
    @ValueSource(strings = {
            "/users?userId",
            "/users?userId=javajigi=JaeSung",
            "/users?userId=javajigi?name=JaeSung"
    })
    void initException(String path) {
        assertThatThrownBy(() -> new HttpPath(path))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(HttpPath.VALIDATION_MESSAGE);
    }
}
