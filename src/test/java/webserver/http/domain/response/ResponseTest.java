package webserver.http.domain.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Cookie;
import webserver.http.domain.Headers;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.domain.ContentType.JSON;
import static webserver.http.domain.Headers.CONTENT_LENGTH;
import static webserver.http.domain.Headers.CONTENT_TYPE;
import static webserver.http.domain.Headers.LOCATION;
import static webserver.http.domain.Headers.SET_COOKIE;

class ResponseTest {

    @DisplayName("OK 상태코드를 갖는 헤더, 쿠키, 바디 모두 빈 응답객체를 생성한다.")
    @Test
    void ok() {
        Response actual = Response.ok();
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of()),
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
                        new Headers(Map.of(LOCATION, "/index.html")),
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
                        null)
                );
    }

    @DisplayName("응답객체에 헤더를 추가한다.")
    @Test
    void addHeader() {
        Response response = new Response(
                Status.ok(),
                new Headers(new HashMap<>()),
                null);

        response.addHeader(CONTENT_TYPE, JSON.getHeader());
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of(CONTENT_TYPE, JSON.getHeader())),
                        null)
                );
    }

    @DisplayName("addBody(String) 응답객체에 body와 해당 body크기 만큼 Content-Length 헤더 값을 추가한다.")
    @Test
    void addBody_string() {
        Response response = new Response(
                Status.ok(),
                new Headers(new HashMap<>()),
                null);

        response.addBody("body");

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of(CONTENT_LENGTH, "4")),
                        new byte[]{98, 111, 100, 121})
                );
    }

    @DisplayName("addBody(byte[]) 응답객체에 body와 해당 body크기 만큼 Content-Length 헤더 값을 추가한다.")
    @Test
    void addBody_bytes() {
        Response response = new Response(
                Status.ok(),
                new Headers(new HashMap<>()),
                null);

        response.addBody(new byte[]{98, 111, 100, 121});

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of(CONTENT_LENGTH, "4")),
                        new byte[]{98, 111, 100, 121})
                );
    }

    @DisplayName("응답객체에 쿠키를 추가한다.")
    @Test
    void addCookie() {
        Response response = new Response(
                Status.ok(),
                new Headers(new HashMap<>()),
                null);

        response.addCookie(new Cookie("logined", "true", "/"));

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new Response(
                        Status.ok(),
                        new Headers(Map.of(
                                SET_COOKIE, "logined=true; path=/"
                        )),
                        null)
                );
    }
}