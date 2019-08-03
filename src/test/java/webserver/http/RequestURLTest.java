package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestURLTest {

    @ParameterizedTest
    @CsvSource(value = {"userId:javajigi", "password:password", "name:JaeSung"}, delimiter = ':')
    void parse(String input, String expected) {
        RequestURL requestURL = RequestURL.parse("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(requestURL.getQuery(input)).isEqualTo(expected);
    }
}
