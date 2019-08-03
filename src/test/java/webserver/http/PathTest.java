package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PathTest {
    @ParameterizedTest
    @ValueSource(strings = {"/user"})
    void path저장확인(String pathString) {
        //when
        Path path = Path.newInstance(pathString);

        //then
        assertThat(path.getPath()).isEqualTo(pathString);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users?userId=javajigi&password=password&name=JaeSung"
            , "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1"})
    void check_queryString(String pathString) {
        //when
        Path path = Path.newInstance(pathString);

        //then
        assertThat(path.getParameters()).isNotNull();
    }
}
