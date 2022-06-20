package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestBodyTest {

    @DisplayName("RequestBody 객체를 생성할 수 있다")
    @Test
    public void create() {
        String input = "userId=javajigi&password=password&name=JaeSung";

        RequestBody actual = RequestBody.from(input);

        assertThat(actual.get("userId")).isEqualTo("javajigi");
        assertThat(actual.get("password")).isEqualTo("password");
        assertThat(actual.get("name")).isEqualTo("JaeSung");
    }

    @DisplayName("예외 반환 검증")
    @ParameterizedTest
    @ValueSource(strings = {"=&=", "&password=password", "=", " "})
    public void invalid(String input) {
        assertThatThrownBy(() -> RequestBody.from(input)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("key값 조회 시 결과가 없으면 예외를 반환한다")
    @Test
    public void invalidGet() {
        String input = "userId=javajigi&password=password&name=JaeSung";

        RequestBody actual = RequestBody.from(input);

        assertThatThrownBy(() -> actual.get("userName")).isInstanceOf(IllegalArgumentException.class);
    }
}
