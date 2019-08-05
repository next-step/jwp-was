package webserver.http;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UriTest {
    @ParameterizedTest
    @MethodSource("provideUris")
    void parse(final String field, final String path, final String query) {
        final Uri uri = Uri.parse(field);
        assertThat(uri.toString()).isEqualTo(field);
        assertThat(uri.getPath()).isEqualTo(path);
        assertThat(uri.getQuery()).isEqualTo(query);
    }

    private static Stream<Arguments> provideUris() {
        return Stream.of(
                Arguments.of(
                        "http://www.w3.org/pub/WWW/TheProject.html",
                        "/pub/WWW/TheProject.html",
                        null
                ),
                Arguments.of(
                        "/users?userId=javajigi&password=password&name=JaeSung",
                        "/users",
                        "userId=javajigi&password=password&name=JaeSung"
                ),
                Arguments.of(
                        "/users?key1=&key2=value2",
                        "/users",
                        "key1=&key2=value2"
                )
        );
    }
}
