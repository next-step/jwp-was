package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
}