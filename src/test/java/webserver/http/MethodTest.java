package webserver.http;

import exception.IllegalProtocolMethodException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("HTTP Method 테스트")
class MethodTest {

    @DisplayName("허용된 HTTP Method 를 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"get", "post"})
    void allowHttpMethod(String method) {
        // Act
        final Method actual = Method.from(method);

        // Assert
        assertThat(actual).isInstanceOf(Method.class);
    }

    @DisplayName("허용되지 않은 HTTP Method일 경우 IllegalException이 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"put", "patch", "head"})
    void notAllowHttpMethod(String method) {
        // Act & Assert
        assertThatThrownBy(() -> Method.from(method))
                .isInstanceOf(IllegalProtocolMethodException.class);
    }
}