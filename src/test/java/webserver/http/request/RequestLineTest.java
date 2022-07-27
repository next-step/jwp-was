package webserver.http.request;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RequestLineTest {

    @DisplayName("GET 메서드 여부 반환")
    @ParameterizedTest
    @MethodSource("provideForIsGet")
    void isGetMethod(Method method, boolean expected) {
        RequestLine requestLine = new RequestLine(method, new URI("/path"), new Protocol("HTTP", "1.1"));
        boolean actual = requestLine.hasMethod(Method.GET);
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
        String actual = requestLine.getPath();
        assertThat(actual).isEqualTo("/path");
    }

    @DisplayName("URI에 저장된 key 에 해당하는 value를 가져온다.")
    @ParameterizedTest
    @MethodSource("ProvideForGetParameter")
    void getParameter(Parameters parameters, String expected) {
        URI uri = new URI("/path", parameters);
        RequestLine requestLine = new RequestLine(Method.GET, uri, new Protocol("HTTP", "1.1"));

        String actual = requestLine.getParameter("key");
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

    @DisplayName("Parameters를 requestLine에 추가")
    @Test
    void addParameters() {
        Parameters originalParameters = new Parameters(
                new HashMap<>(
                    Map.of(
                        "id", Lists.list("mint")
                    )
                )
        );
        RequestLine requestLine = new RequestLine(Method.GET, new URI("/path", originalParameters), new Protocol("HTTP", "1.1"));

        Parameters target = new Parameters(Map.of(
                "name", Lists.list("jordy"),
                "age", Lists.list("20")
        ));

        requestLine.addParameters(target);

        assertThat(requestLine).usingRecursiveComparison()
                .isEqualTo(expectedRequestLine());
    }

    private RequestLine expectedRequestLine() {
        Parameters addedParameters = new Parameters(Map.of(
                "id", Lists.list("mint"),
                "name", Lists.list("jordy"),
                "age", Lists.list("20")
        ));

        return new RequestLine(
                Method.GET,
                new URI("/path", addedParameters),
                new Protocol("HTTP", "1.1")
        );
    }
}