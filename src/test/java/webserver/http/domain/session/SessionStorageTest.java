package webserver.http.domain.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Headers;
import webserver.http.domain.request.Request;
import webserver.http.domain.request.RequestLine;
import webserver.http.domain.response.Response;
import webserver.http.domain.response.Status;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;


class SessionStorageTest {

    private SessionStorage sessionStorage;

    private SessionIdGenerator fixedSessionIdGenerator = () -> "12345";

    @DisplayName("요청 쿠키에 세션ID (JWP_SID 쿠키값)이 포함되지 않는 경우, 새키를 가진 세션 생성후 세션저장소에 저장한다.")
    @Test
    void setupBeforeProcess_when_JWP_SID_cookie_empty() {
        sessionStorage = new SessionStorage(
                new ConcurrentHashMap<>(),
                fixedSessionIdGenerator
        );

        Request request = new Request(
                RequestLine.from("POST /path?name=jordy&age=20 HTTP/1.1"),
                Headers.from(List.of(
                        "Cookie: name1=value1"
                ))
        );
        sessionStorage.getOrGenerateSession(request);

        assertThat(sessionStorage).usingRecursiveComparison()
                .isEqualTo(
                        new SessionStorage(
                                Map.of(
                                        "12345", new Session("12345", Map.of())
                                ),
                                () -> "12345"
                        )
                );
    }

    @DisplayName("쿠키에 세션저장소에 저장되지 않은 세션ID가 넘어오는 경우, 요청으로 넘어온 세션ID를 키로 가진 세션 생성후, 세션저장소에 저장한다.")
    @Test
    void setupBeforeProcess_when_JWP_SID_cookie_invalid() {
        sessionStorage = new SessionStorage(
                new ConcurrentHashMap<>(
                        Map.of(
                                "validSessionId", new Session("validSessionId", Map.of())
                        )
                ),
                fixedSessionIdGenerator
        );

        Request request = new Request(
                RequestLine.from("POST /path?name=jordy&age=20 HTTP/1.1"),
                Headers.from(List.of(
                        "Cookie: JWP_SID=newSessionId"
                ))
        );
        sessionStorage.getOrGenerateSession(request);

        assertThat(sessionStorage).usingRecursiveComparison()
                .isEqualTo(
                        new SessionStorage(
                                Map.of(
                                        "validSessionId", new Session("validSessionId", Map.of()),
                                        "newSessionId", new Session("newSessionId", Map.of())
                                ),
                                () -> "12345"
                        )
                );
    }

    @DisplayName("쿠키에 세션저장소에 저장된 세션ID가 넘어오는 경우, 해당 세션을 컨텍스트 홀더에 저장한다.")
    @Test
    void setupBeforeProcess_when_JWP_SID_cookie_is_contained_session_storage() {
        sessionStorage = new SessionStorage(
                new ConcurrentHashMap<>(
                        Map.of(
                                "validSessionId", new Session("validSessionId", Map.of(
                                        "name", "jordy",
                                        "age", 20
                                ))
                        )
                ),
                fixedSessionIdGenerator
        );

        Request request = new Request(
                RequestLine.from("POST /path?name=jordy&age=20 HTTP/1.1"),
                Headers.from(List.of(
                        "Cookie: JWP_SID=validSessionId"
                ))
        );
        sessionStorage.getOrGenerateSession(request);

        assertThat(sessionStorage).usingRecursiveComparison()
                .isEqualTo(
                        new SessionStorage(
                                Map.of(
                                        "validSessionId",
                                        new Session(
                                                "validSessionId",
                                                Map.of(
                                                        "name", "jordy",
                                                        "age", 20
                                                )
                                        )
                                ),
                                () -> "12345"
                        )
                );
    }

    @DisplayName("요청 처리후, 현재 세션에 저장된 속성이 아무것도 없는 경우, 세션저장소에서 삭제한다.")
    @Test
    void removeCurrentSession_empty_session() {
        Session session = Session.from("sessionId");
        SessionContextHolder.saveCurrentSession(session);

        sessionStorage = new SessionStorage(
                new ConcurrentHashMap<>(
                        Map.of(
                                "sessionId", session
                        )
                ),
                fixedSessionIdGenerator
        );

        Response response = Response.ok();

        sessionStorage.teardownAfterProcess(response);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(new Response(Status.ok()));

        assertThat(sessionStorage)
                .usingRecursiveComparison()
                .isEqualTo(
                        new SessionStorage(
                                Map.of(),
                                () -> "12345"
                        )
                );
    }

    @DisplayName("요청 처리후, 현재 세션에 저장된 속성이 비어있지 않는 경우, 세션저장소에 해당 세션을 그대로 보관한다.")
    @Test
    void removeCurrentSession_non_empty_session() {
        Session session = Session.from("sessionId");
        session.setAttribute("name", "jordy");
        session.setAttribute("age", 20);
        SessionContextHolder.saveCurrentSession(session);

        sessionStorage = new SessionStorage(
                new ConcurrentHashMap<>(
                        Map.of(
                                "sessionId", session
                        )
                ),
                fixedSessionIdGenerator
        );

        Response response = Response.ok();

        sessionStorage.teardownAfterProcess(response);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(
                        new Response(
                                Status.ok(),
                                Headers.from(
                                        List.of(
                                                "Set-Cookie: JWP_SID=sessionId; Path=/"
                                        )
                                ),
                                null
                        )
                );

        assertThat(sessionStorage)
                .usingRecursiveComparison()
                .isEqualTo(
                        new SessionStorage(
                                Map.of(
                                    "sessionId", session
                                ),
                                () -> "12345"
                        )
                );
    }
}