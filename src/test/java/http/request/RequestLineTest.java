package http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {
    private static final Logger log = LoggerFactory.getLogger(RequestLineTest.class);

    @DisplayName("RequestLine Exception 테스트")
    @ParameterizedTest
    @CsvSource({
        "'GET', '', ''",
        "'POST', '', ''",
        "'', '/users', ''",
        "'', '/users?userId=javajigi&password=password&name=JaeSung', ''",
        "'', '', 'HTTP'",
        "'', '', '1.0'",
        "'', '', '1.1'",
        "'', '', '2.0'",
        "'', '', 'HTTP/1.0'",
        "'', '', 'HTTP/1.1'",
        "'', '', 'HTTP/2.0'",
        "'GET', '/users', ''",
        "'POST', '/users', ''",
        "'GET', '/users?userId=javajigi&password=password&name=JaeSung', ''",
        "'POST', '/users?userId=javajigi&password=password&name=JaeSung', ''",
        "'GET', '', 'HTTP/1.0'",
        "'GET', '', 'HTTP/1.1'",
        "'GET', '', 'HTTP/2.0'",
        "'POST', '', 'HTTP/1.0'",
        "'POST', '', 'HTTP/1.1'",
        "'POST', '', 'HTTP/2.0'",
        "'', '/users', 'HTTP/1.0'",
        "'', '/users', 'HTTP/1.1'",
        "'', '/users', 'HTTP/2.0'",
        "'', '/users?userId=javajigi&password=password&name=JaeSung', 'HTTP/1.0'",
        "'', '/users?userId=javajigi&password=password&name=JaeSung', 'HTTP/1.1'",
        "'', '/users?userId=javajigi&password=password&name=JaeSung', 'HTTP/2.0'",
        "'', '', ''"
    })
    public void testRequestLine(String method, String path, String protocolAndVersion) {
        log.info("=============================================");
        log.info("method: {}", method);
        log.info("path: {}", path);
        log.info("protocolAndVersion: {}", protocolAndVersion);
        log.info("=============================================");

        assertThrows(IllegalArgumentException.class, () -> new RequestLine(method + " " + path + " " + protocolAndVersion));
    }

    @DisplayName("QueryString 없는 RequestLine 테스트")
    @ParameterizedTest
    @CsvSource({
            "'GET', '/users', '', 'HTTP', '1.0'",
            "'GET', '/users', '', 'HTTP', '1.1'",
            "'GET', '/users', '', 'HTTP', '2.0'",
            "'POST', '/users', '', 'HTTP', '1.0'",
            "'POST', '/users', '', 'HTTP', '1.1'",
            "'POST', '/users', '', 'HTTP', '2.0'",
    })
    public void testRequestLineWithoutQueryString(String method, String path, String queryString, String protocol, String version) {
        RequestLine requestLine = new RequestLine(buildRequestLine(method, path, queryString, protocol, version));

        assertThat(requestLine).isNotNull();
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getPath()).isEqualTo(path);
        assertThat(requestLine.getQueryString()).isEmpty();
        assertThat(requestLine.getProtocol()).isEqualTo(protocol);
        assertThat(requestLine.getVersion()).isEqualTo(version);
    }

    @DisplayName("QueryString 이 존재하는 RequestLine 테스트")
    @ParameterizedTest
    @CsvSource({
            "'GET', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.0'",
            "'GET', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.1'",
            "'GET', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '2.0'",
            "'POST', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.0'",
            "'POST', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '1.1'",
            "'POST', '/users', 'userId=javajigi&password=password&name=JaeSung', 'HTTP', '2.0'",
    })
    public void testRequestLineWithQueryString(String method, String path, String queryString, String protocol, String version) {
        RequestLine requestLine = new RequestLine(buildRequestLine(method, path, queryString, protocol, version));

        assertThat(requestLine).isNotNull();
        assertThat(requestLine.getMethod()).isEqualTo(method);
        assertThat(requestLine.getPath()).isEqualTo(path);

        assertThat(requestLine.getQueryString()).contains(
            entry("userId", "javajigi"),
            entry("password", "password"),
            entry("name", "JaeSung")
        );

        assertThat(requestLine.getProtocol()).isEqualTo(protocol);
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