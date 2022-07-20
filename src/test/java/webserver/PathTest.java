package webserver;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class PathTest {

    @ParameterizedTest
    @CsvSource(value = {"/users?userId=kkwan0226:/users", "/users:/users"}, delimiter = ':')
    void 올바른_REQUEST_PATH_테스트(String requestUrl, String path) {
        assertThat(Path.from(requestUrl).getPath()).isEqualTo(path);
    }
}
