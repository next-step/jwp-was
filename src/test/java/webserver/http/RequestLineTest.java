package webserver.http;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import webserver.http.request.Method;
import webserver.http.request.Protocol;
import webserver.http.request.RequestParameters;
import webserver.http.request.RequestLine;
import webserver.http.request.Version;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestLineTest {

    @DisplayName("GET 파싱")
    @Test
    void parseGETRequestLine() {
        RequestLine requestLine = RequestLine.parse("GET /users HTTP/1.1");

        Method method = requestLine.getMethod();
        String path = requestLine.getUri().getPath();
        Protocol protocol = requestLine.getProtocol();
        Version version = requestLine.getVersion();

        assertAll(
                () -> assertThat(method).isEqualTo(Method.GET),
                () -> assertThat(path).isEqualTo("/users"),
                () -> assertThat(protocol).isEqualTo(Protocol.HTTP),
                () -> assertThat(version).isEqualTo(Version.V_1_1)
        );
    }

    @DisplayName("POST 파싱")
    @Test
    void parsePOSTRequestLine() {
        RequestLine requestLine = RequestLine.parse("POST /users HTTP/1.1");

        Method method = requestLine.getMethod();
        String path = requestLine.getUri().getPath();
        Protocol protocol = requestLine.getProtocol();
        Version version = requestLine.getVersion();

        assertAll(
                () -> assertThat(method).isEqualTo(Method.POST),
                () -> assertThat(path).isEqualTo("/users"),
                () -> assertThat(protocol).isEqualTo(Protocol.HTTP),
                () -> assertThat(version).isEqualTo(Version.V_1_1)
        );
    }

    @DisplayName("QueryString 파싱")
    @Test
    void parseQueryStringRequestLine() {
        RequestLine requestLine = RequestLine.parse("GET /users?userId=dean&password=password&name=Dongchul HTTP/1.1");

        Method method = requestLine.getMethod();
        String path = requestLine.getUri().getPath();
        RequestParameters requestParameters = requestLine.getUri()
                                                         .getRequestParameters();
        Protocol protocol = requestLine.getProtocol();
        Version version = requestLine.getVersion();

        assertAll(
                () -> assertThat(method).isEqualTo(Method.GET),
                () -> assertThat(path).isEqualTo("/users"),
                () -> assertThat(requestParameters.get("userId")).isEqualTo("dean"),
                () -> assertThat(requestParameters.get("password")).isEqualTo("password"),
                () -> assertThat(requestParameters.get("name")).isEqualTo("Dongchul"),
                () -> assertThat(protocol).isEqualTo(Protocol.HTTP),
                () -> assertThat(version).isEqualTo(Version.V_1_1)
        );
    }

    @DisplayName("문자열이 null이거나 공백인경우 Exception을 던진다.")
    @NullAndEmptySource
    @ParameterizedTest
    void parseNullAndEmpty(String value) {
        ThrowingCallable throwingCallable = () -> RequestLine.parse(value);
        assertThatIllegalArgumentException().isThrownBy(throwingCallable);
    }

}
