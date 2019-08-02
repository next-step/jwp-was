package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PartsTest {

    @ParameterizedTest
    @CsvSource(value = {"userId:javajigi", "password:password", "name:JaeSung"}, delimiter = ':')
    void parse(String input, String expected) {
        Parts parts = Parts.parse("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(parts.getQuery(input)).isEqualTo(expected);
    }
}
