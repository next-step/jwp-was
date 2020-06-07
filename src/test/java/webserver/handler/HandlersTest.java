package webserver.handler;

import http.request.Request;
import http.request.RequestBody;
import http.request.Headers;
import http.request.RequestLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.handler.custom.LoginHandler;
import webserver.handler.custom.StaticResourceHandler;
import webserver.handler.custom.TemplateResourceHandler;
import webserver.handler.custom.UserCreateHandler;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlersTest {
    @DisplayName("요청의 url이 static 자원을 가리키면, StaticHandler 맵핑")
    @ParameterizedTest
    @ValueSource(strings = {".css", ".js", ".ttf", ".woff"})
    void findHandlerWhenStatic(String suffix) {
        //given
        RequestLine requestLine = new RequestLine("GET /style" + suffix + " HTTP/1.1");
        RequestBody requestBody = new RequestBody("body");
        Headers headers = new Headers(new HashMap<>());
        Request request = new Request(requestLine, headers, requestBody);

        //when
        Handler handler = Handlers.findHandler(request);

        //then
        assertThat(handler).isInstanceOf(StaticResourceHandler.class);
    }

    @DisplayName("요청의 url이 template 자원을 가리키면, TemplateHandler 맵핑")
    @ParameterizedTest
    @ValueSource(strings = {".html", ".ico"})
    void findHandlerWhenTemplate(String suffix) {
        //given
        RequestLine requestLine = new RequestLine("GET /index" + suffix + " HTTP/1.1");
        RequestBody requestBody = new RequestBody("body");
        Headers headers = new Headers(new HashMap<>());
        Request request = new Request(requestLine, headers, requestBody);

        //when
        Handler handler = Handlers.findHandler(request);

        //then
        assertThat(handler).isInstanceOf(TemplateResourceHandler.class);
    }

    @DisplayName("요청의 url이 /login이면, LoginHandler 맵핑")
    @Test
    void findHandlerWhenLogin() {
        //given
        RequestLine requestLine = new RequestLine("POST /login HTTP/1.1");
        RequestBody requestBody = new RequestBody("body");
        Headers headers = new Headers(new HashMap<>());
        Request request = new Request(requestLine, headers, requestBody);
        Handlers.addHandler(new LoginHandler("/login"));

        //when
        Handler handler = Handlers.findHandler(request);

        //then
        assertThat(handler).isInstanceOf(LoginHandler.class);
    }

    @DisplayName("요청의 url이 /users이면, UserCreateHandler 맵핑")
    @Test
    void findHandlerWhenCreateUser(){
        //given
        RequestLine requestLine = new RequestLine("POST /users HTTP/1.1");
        RequestBody requestBody = new RequestBody("body");
        Headers headers = new Headers(new HashMap<>());
        Request request = new Request(requestLine, headers, requestBody);
        Handlers.addHandler(new UserCreateHandler("/users"));

        //when
        Handler handler = Handlers.findHandler(request);

        //then
        assertThat(handler).isInstanceOf(UserCreateHandler.class);
    }
}
