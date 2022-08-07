package slipp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import slipp.db.DataBase;
import slipp.model.User;
import slipp.service.AuthenticateService;
import webserver.http.domain.Headers;
import webserver.http.domain.Protocol;
import webserver.http.domain.request.Method;
import webserver.http.domain.request.Parameters;
import webserver.http.domain.request.Request;
import webserver.http.domain.request.RequestLine;
import webserver.http.domain.request.URI;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.Status;
import webserver.http.domain.session.Session;
import webserver.http.domain.session.SessionContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static webserver.http.domain.Headers.LOCATION;
import static webserver.http.domain.request.Method.GET;
import static webserver.http.domain.request.Method.POST;

class LoginControllerTest {
    private final LoginController loginController = new LoginController(new AuthenticateService());

    @DisplayName("POST 요청이고 /user/login path 요청인 경우, true 반환")
    @ParameterizedTest
    @MethodSource("provideForRequires")
    void requires(Method method, String path, boolean expected) {
        RequestLine requestLine = new RequestLine(method, new URI(path), Protocol.HTTP_1_1);
        Request request = new Request(requestLine, new Headers(new LinkedHashMap<>()));

        boolean actual = loginController.requires(request);

        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideForRequires() {
        return Stream.of(
                arguments(GET, "/user/login", false),
                arguments(POST, "/user/login", true),
                arguments(GET, "/nono.html", false)
        );
    }

    @DisplayName("parameter로 정보를 받아 로그인 처리후 302 Response를 응답한다. id,pw가 일치하지 않으면 로그인 실패처리후 302 응답")
    @ParameterizedTest
    @MethodSource("provideForHandle")
    void handle(String userId, String password, String expectedRedirectUrl, Session expectedSession) {
        SessionContextHolder.saveCurrentSession(Session.from("12345"));

        DataBase.addUser(
                new User(
                        "userId",
                        "password",
                        "name",
                        "email"
                )
        );

        RequestLine requestLine = new RequestLine(
                POST,
                new URI(
                        "/user/login",
                        new Parameters(new HashMap<>(Map.of(
                                "userId", new ArrayList<>(List.of(userId)),
                                "password", new ArrayList<>(List.of(password))
                        )))
                ),
                Protocol.HTTP_1_1);
        Request request = new Request(requestLine, new Headers(new LinkedHashMap<>()));

        Response actual = loginController.handle(request);

        assertThat(actual).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(new Response(
                        Status.found(),
                        new Headers(Map.of(
                                LOCATION, expectedRedirectUrl
                        )),
                        null
                ));

        Session currentSession = SessionContextHolder.getCurrentSession();
        assertThat(currentSession)
                .usingRecursiveComparison()
                .isEqualTo(expectedSession);
    }

    private static Stream<Arguments> provideForHandle() {
        return Stream.of(
                arguments("userId", "password", "/index.html", new Session(
                        "12345",
                        Map.of(
                                "user",
                                new User(
                                        "userId",
                                        "password",
                                        "name",
                                        "email"
                                )
                        )
                )),
                arguments("wrongId", "password", "/user/login_failed.html", new Session(
                        "12345",
                        Map.of()
                )),
                arguments("userId", "wrongPassword", "/user/login_failed.html", new Session(
                        "12345",
                        Map.of()
                )),
                arguments("wrongId", "wrongPassword", "/user/login_failed.html", new Session(
                        "12345",
                        Map.of()
                ))
        );
    }
}