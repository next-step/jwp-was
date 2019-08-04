package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpUtilsTest {

    @ParameterizedTest
    @CsvSource(value = {"userId:javajigi", "password:password", "name:JaeSung"}, delimiter = ':')
    void parseQueryString(String input, String expected) {
        Map<String, String> queryString = HttpUtils.parseQueryString("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.get(input)).isEqualTo(expected);
    }
}
