package utils;

import http.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testutils.HttpRequestGenerator;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로그인 쿠키와 관련된 유틸 클래스")
class LoginUtilTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("loggedIn 쿠키값의 존재 및 값 확인 테스트")
    void isLoggedIn(final HttpRequest httpRequest, final boolean expected) {
        assertThat(LoginUtil.isLoggedIn(httpRequest)).isEqualTo(expected);
    }

    private static Stream<Arguments> isLoggedIn() throws IOException {
        return Stream.of(
                Arguments.of(
                        HttpRequestGenerator.init(
                                "GET some-request HTTP/1.1\n" +
                                        "Cookie: logined=true\n" +
                                        "\n"
                        ),
                        true
                ),
                Arguments.of(
                        HttpRequestGenerator.init(
                                "GET some-request HTTP/1.1\n" +
                                        "Cookie: logined=false\n" +
                                        "\n"
                        ),
                        false
                ),
                Arguments.of(
                        HttpRequestGenerator.init(
                                "GET some-request HTTP/1.1\n" +
                                        "\n"
                        ),
                        false
                )
        );
    }
}
