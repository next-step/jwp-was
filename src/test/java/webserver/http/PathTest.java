package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PathTest {
    @DisplayName("Path 생성 테스트")
    @Test
    void from_noneData() {
        String onlyPath = "/users";
        Path path = new Path(onlyPath);
        assertThat(path).isNotNull();
        assertThat(path.getPath()).isEqualTo("/users");
    }

    @DisplayName("유효하지 않은 Path 생성 시 예외 발생 테스트")
    @Test
    void from_noneData_exception() {
        String wrongPath = "!users";
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Path(wrongPath);
        }).withMessageContaining("형식");
    }

    @DisplayName("Query String으로 데이터를 전달하는 Path 생성 테스트")
    @Test
    void from_hasData() {
        String queryString = "/users?userId=javajigi&password=password&name=JaeSung";
        Path path = new Path(queryString);
        assertThat(path).isNotNull();
        assertThat(path.getPath()).isEqualTo("/users");
        assertThat(path.getQueryString()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
    }

    @DisplayName("Query String으로 데이터를 전달하는 유효하지 않은 Path 생성 시 예외 발생 테스트")
    @Test
    void from_hasData_exception() {
        String wrongQueryString = "/users?userId=javajigi?password=password&name=JaeSung";
        assertThatIllegalArgumentException().isThrownBy(() -> {
            new Path(wrongQueryString);
        }).withMessageContaining("속성");
    }
}
