package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.cookie.Cookie;
import webserver.http.cookie.Cookies;
import webserver.http.header.Headers;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseTest {

    @DisplayName("OK 상태코드를 갖는 헤더, 쿠키, 바디 모두 빈 응답객체를 생성한다.")
    @Test
    void ok() {
        Response actual = Response.ok();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of()),
                        new Cookies(Map.of()),
                        null)
                );
    }

    @DisplayName("Found 상태코드를 갖는 헤더, 쿠키, 바디 모두 빈 응답객체를 생성한다.")
    @Test
    void sendRedirect() {
        Response actual = Response.sendRedirect("/index.html");
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.found(),
                        new Headers(Map.of("Location", "/index.html")),
                        new Cookies(Map.of()),
                        null)
                );
    }

    @DisplayName("Http상태코드를 매개값으로 헤더, 쿠키, 바디 모두 빈 응답객체를 생성한다.")
    @Test
    void from() {
        Response actual = Response.from(StatusCode.BAD_REQUEST);
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.badRequest(),
                        new Headers(Map.of()),
                        new Cookies(Map.of()),
                        null)
                );
    }

    @DisplayName("응답객체에 헤더를 추가한다.")
    @Test
    void addHeader() {
        Response response = new Response(
                Status.ok(),
                new Headers(new HashMap<>()),
                new Cookies(Map.of()),
                null);

        response.addHeader("Content-Type", "application/json");
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of("Content-Type", "application/json")),
                        new Cookies(Map.of()),
                        null)
                );
    }

    @DisplayName("응답객체에 body와 해당 body크기 만큼 Content-Length 헤더 값을 추가한다.")
    @Test
    void addBody() {
        Response response = new Response(
                Status.ok(),
                new Headers(new HashMap<>()),
                new Cookies(Map.of()),
                null);

        response.addBody("body");

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of("Content-Length", "4")),
                        new Cookies(Map.of()),
                        new byte[]{98, 111, 100, 121})
                );
    }

    @DisplayName("응답객체에 쿠키를 추가한다.")
    @Test
    void addCookie() {
        Response response = new Response(
                Status.ok(),
                new Headers(Map.of()),
                new Cookies(new HashMap<>()),
                null);

        response.addCookie(new Cookie("logined", "true", "/"));

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of()),
                        new Cookies(Map.of("logined", new Cookie("logined", "true", "/"))),
                        null)
                );
    }
}