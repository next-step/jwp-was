package webserver.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.request.RequestLine.PathAndQueryStrings;
import webserver.enums.HttpMethod;
import webserver.enums.Protocol;

class RequestLineTest {

    @DisplayName("null 들어오면 파싱 시 예외가 나온다.")
    @Test
    void createWithNullTest() {
        assertThatThrownBy(() -> {
            RequestLine.of(null);
        }).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("빈문자들이 들어오면 파싱 시 예외가 나온다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "      "})
    void createEmptyLineTest(String input) {
        assertThatThrownBy(() -> {
            RequestLine.of(input);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"GET", "GET /users", "GET /users HTTP", "GET /users HTTP/"})
    void illegalText(String input) {
        assertThatThrownBy(
            () -> RequestLine.of(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("RequestLine 파싱 테스트 중 GET 요청 테스트")
    @Test
    void createGetTest() {
        RequestLine getRequest = RequestLine.of("GET /users HTTP/1.1");

        assertThat(getRequest).isEqualTo(RequestLine.of(HttpMethod.GET, "/users", Protocol.HTTP_1_1));
    }

    @DisplayName("RequestLine 파싱 테스트 중 POST 요청 테스트")
    @Test
    void createPostTest() {
        RequestLine postRequest = RequestLine.of("POST /users HTTP/1.1");

        assertThat(postRequest).isEqualTo(RequestLine.of(HttpMethod.POST, "/users", Protocol.HTTP_1_1));
    }

    @DisplayName("RequestLine 파싱 테스트 중 Query String 포함한 테스트")
    @Test
    void createGetWithQueryStringTest() {
        RequestLine getRequest = RequestLine.of("GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1");

        assertThat(getRequest).isEqualTo(RequestLine.of(HttpMethod.GET, "/users?userId=javajigi&password=password&name=JaeSung", Protocol.HTTP_1_1));
    }

    @DisplayName("Path 파싱 시 QueryString 이 없는 경우 Path 만 나오고 QueryString 은 emptyMap 으로 나온다.")
    @Test
    void pathTest() {
        final String testPath = "/testPath";
        PathAndQueryStrings pathAndQueryStrings = PathAndQueryStrings.of(testPath);

        assertThat(pathAndQueryStrings.path).isEqualTo(testPath);
        assertThat(pathAndQueryStrings.queryStringsMap).isEmpty();
    }

    @DisplayName("Path 파싱 시 QueryString 포함한 경우 Path, Map 둘 다 나온다.")
    @Test
    void pathWithQueryStringTest() {
        final String testPath = "/testPath";
        final String testQueryStrings = "this=이것&that=저것";
        PathAndQueryStrings pathAndQueryStrings = PathAndQueryStrings.of(testPath + "?" + testQueryStrings);

        assertThat(pathAndQueryStrings.path).isEqualTo(testPath);
        assertThat(pathAndQueryStrings.queryStringsMap)
            .hasSize(2)
            .contains(entry("this", "이것"), entry("that", "저것"));
    }

    @DisplayName("null Path 입력 시 Path / QueryStrings 파싱은 실패한다.")
    @Test
    void emptyPathTest1() {
        assertThatThrownBy(
            () -> PathAndQueryStrings.of(null)
        ).isInstanceOf(NullPointerException.class);
    }

    @DisplayName("비어있는 Path 입력 시 Path / QueryStrings 파싱은 실패한다.")
    @Test
    void emptyPathTest2() {
        final String testPath = "";
        assertThatThrownBy(
            () -> PathAndQueryStrings.of(testPath)
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
