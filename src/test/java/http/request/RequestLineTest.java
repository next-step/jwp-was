package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.common.HttpMethod;
import http.request.requestline.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {
    private static final Logger log = LoggerFactory.getLogger(RequestLineTest.class);

    @DisplayName("RequestLine Exception 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
        "TAKE /users HTTP/1.0",
        "GIVE /users HTTP/1.0",
    })
    @NullAndEmptySource
    public void testRequestLine(String requestLine) {
        assertThrows(IllegalArgumentException.class, () -> RequestLine.parse(requestLine));
    }

    @DisplayName("QueryString 없는 RequestLine 테스트")
    @ParameterizedTest
    @CsvSource({
            "'GET', '/users', '', 'HTTP', '1.0'",
            "'GET', '/users', '', 'HTTP', '1.1'",
            "'POST', '/users', '', 'HTTP', '1.0'",
            "'POST', '/users', '', 'HTTP', '1.1'",
    })
    public void testRequestLineWithoutQueryString(String method, String path, String queryString, String protocol, String version) {
        RequestLine requestLine = RequestLine.parse(buildRequestLine(method, path, queryString, protocol, version));

        assertThat(requestLine).isNotNull();
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.resolve(method));
        assertThat(requestLine.getPath()).isEqualTo(path);
        assertThat(requestLine.getQueryString()).isEmpty();
        assertThat(requestLine.getProtocolStr()).isEqualTo(protocol);
        assertThat(requestLine.getVersion()).isEqualTo(version);
    }

    @DisplayName("QueryString 이 존재하는 RequestLine 테스트")
    @ParameterizedTest
    @CsvSource({
            "'GET', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.0'",
            "'GET', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.1'",
            "'POST', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.0'",
            "'POST', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.1'",
    })
    public void testRequestLineWithQueryString(String method, String path, String queryString, String protocol, String version) {
        RequestLine requestLine = RequestLine.parse(buildRequestLine(method, path, queryString, protocol, version));

        assertThat(requestLine).isNotNull();
        assertThat(requestLine.getMethod()).isEqualTo(HttpMethod.resolve(method));
        assertThat(requestLine.getPath()).isEqualTo(path);

        assertThat(requestLine.getQueryString()).contains(
            entry("userId", "javajigi"),
            entry("password", "password"),
            entry("name", "JaeSung")
        );

        assertThat(requestLine.getProtocolStr()).isEqualTo(protocol);
        assertThat(requestLine.getVersion()).isEqualTo(version);
    }

    private String buildRequestLine(
            String method,
            String path,
            String queryString,
            String protocol,
            String version
    ) {
        return method + " " + buildPathWithQueryString(path, queryString) + " " + protocol + "/" + version;
    }

    private String buildPathWithQueryString(String path, String queryString) {
        if (StringUtils.isNotEmpty(queryString)) {
            return path + "?" + queryString;
        }

        return path;
    }
}
