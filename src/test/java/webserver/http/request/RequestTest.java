package webserver.http.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import webserver.http.header.Headers;
import webserver.http.protocol.Protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RequestTest {

    @DisplayName("Content-Length 헤더에 양수 값을 갖는 경우 true 반환")
    @ParameterizedTest
    @MethodSource("provideForHasContents")
    void hasContents(Request request, boolean expected) {
        boolean actual = request.hasContents();
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForHasContents() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("Content-Length", "13");

        Request requestWithContentLength = new Request(
                new RequestLine(Method.GET, new URI("/path", new Parameters(new HashMap<>())), new Protocol("HTTP", "1.1")),
                new Headers(keyValues)
        );

        HashMap<String, String> keyValues2 = new HashMap<>();
        keyValues2.put("Content-Length", "-13");

        Request requestWithContentLengthNegative = new Request(
                new RequestLine(Method.GET, new URI("/path", new Parameters(new HashMap<>())), new Protocol("HTTP", "1.1")),
                new Headers(keyValues2)
        );

        Request requestWithoutContentLength = new Request(
                new RequestLine(Method.GET, new URI("/path", new Parameters(new HashMap<>())), new Protocol("HTTP", "1.1")),
                new Headers(new HashMap<>())
        );

        return Stream.of(
                arguments(requestWithoutContentLength, false),
                arguments(requestWithContentLengthNegative, false),
                arguments(requestWithContentLength, true)
        );
    }

    @DisplayName("Content-Length 헤더에 값을 갖는 경우 숫자형 값으로 반환")
    @ParameterizedTest
    @MethodSource("provideForGetContentLength")
    void getContentLength(Request request, int expected) {
        int actual = request.getContentLength();
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForGetContentLength() {
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("Content-Length", "13");

        Request requestWithContentLength = new Request(
                new RequestLine(Method.GET, new URI("/path", new Parameters(new HashMap<>())), new Protocol("HTTP", "1.1")),
                new Headers(keyValues)
        );

        HashMap<String, String> keyValues2 = new HashMap<>();
        keyValues2.put("Content-Length", "-13");

        Request requestWithContentLengthNegative = new Request(
                new RequestLine(Method.GET, new URI("/path", new Parameters(new HashMap<>())), new Protocol("HTTP", "1.1")),
                new Headers(keyValues2)
        );

        Request requestWithoutContentLength = new Request(
                new RequestLine(Method.GET, new URI("/path", new Parameters(new HashMap<>())), new Protocol("HTTP", "1.1")),
                new Headers(new HashMap<>())
        );

        return Stream.of(
                arguments(requestWithoutContentLength, 0),
                arguments(requestWithContentLengthNegative, 0),
                arguments(requestWithContentLength, 13)
        );
    }

    @DisplayName("Parameters를 request에 추가한다.")
    @Test
    void addParameters() {
        Parameters originalParameters = new Parameters(
                new HashMap<>(
                        Map.of(
                                "id", Lists.list("mint")
                        )
                )
        );

        Request request = new Request(
                new RequestLine(Method.GET, new URI("/path", originalParameters), new Protocol("HTTP", "1.1")),
                new Headers(new HashMap<>())
        );

        Parameters target = new Parameters(Map.of(
                "name", Lists.list("jordy"),
                "age", Lists.list("20")
        ));

        request.addParameters(target);

        assertThat(request).usingRecursiveComparison()
                .isEqualTo(expectedRequest());
    }

    private Request expectedRequest() {
        Parameters addedParameters = new Parameters(Map.of(
                "id", Lists.list("mint"),
                "name", Lists.list("jordy"),
                "age", Lists.list("20")
        ));

        return new Request(
                new RequestLine(Method.GET, new URI("/path", addedParameters), new Protocol("HTTP", "1.1")),
                new Headers(new HashMap<>())
        );
    }

    @DisplayName("GET 메서드 여부 반환")
    @ParameterizedTest
    @MethodSource("provideForIsGet")
    void isGetMethod(Method method, boolean expected) {
        RequestLine requestLine = new RequestLine(method, new URI("/path"), new Protocol("HTTP", "1.1"));
        Request request = new Request(requestLine, new Headers(new HashMap<>()));

        boolean actual = request.hasMethod(Method.GET);
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForIsGet() {
        return Stream.of(
                arguments(Method.GET, true),
                arguments(Method.POST, false)
        );
    }

    @DisplayName("URI path를 반환")
    @Test
    void getPath() {
        RequestLine requestLine = new RequestLine(Method.GET, new URI("/path"), new Protocol("HTTP", "1.1"));
        Request request = new Request(requestLine, new Headers(new HashMap<>()));

        String actual = request.getPath();
        assertThat(actual).isEqualTo("/path");
    }

    @DisplayName("URI path를 가지고 있는지 여부 반환")
    @ParameterizedTest
    @CsvSource(value = {"/path, true", "/request, false"})
    void hasPath(String path, boolean expected) {
        RequestLine requestLine = new RequestLine(Method.GET, new URI("/path"), new Protocol("HTTP", "1.1"));
        Request request = new Request(requestLine, new Headers(new HashMap<>()));

        boolean actual = request.hasPath(path);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("requestLine에 저장된 key 에 해당하는 value를 가져온다.")
    @ParameterizedTest
    @MethodSource("ProvideForGetParameter")
    void getParameter(Parameters parameters, String expected) {
        URI uri = new URI("/path", parameters);
        RequestLine requestLine = new RequestLine(Method.GET, uri, new Protocol("HTTP", "1.1"));
        Request request = new Request(requestLine, new Headers(new HashMap<>()));


        String actual = request.getParameter("key");
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> ProvideForGetParameter() {
        return Stream.of(
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list("value")
                                )
                        ), "value"
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list("value2", "value3")
                                )
                        ), "value2"
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류"),
                                        "key", Lists.list()
                                )
                        ), null
                ),
                arguments(
                        new Parameters(
                                Map.of(
                                        "키", Lists.list("밸류")
                                )
                        ), null
                )
        );
    }

    @DisplayName("Header에 해당하는 Content-Type 값이 존재하는지 검증")
    @ParameterizedTest
    @MethodSource("provideForHasContentType")
    void hasContentType(Headers headers, String contentType, boolean expected) {
        RequestLine requestLine = new RequestLine(Method.GET, new URI("/path"), new Protocol("HTTP", "1.1"));
        Request request = new Request(requestLine, headers);

        boolean actual = request.hasContentType(contentType);
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideForHasContentType() {
        return Stream.of(
                arguments(new Headers(Map.of("Content-Type", "application/json")), "application/json", true),
                arguments(new Headers(Map.of("Content-Type", "application/json")), "text/html", false),
                arguments(new Headers(Map.of()), "text/html", false)
        );
    }
}