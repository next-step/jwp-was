package webserver.http.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.http.Cookie;
import webserver.http.HttpStatus;
import webserver.http.session.HttpSession;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static model.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {
    private HttpSession httpSession;
    private Cookie cookie;

    @BeforeEach
    void setUp() {
        httpSession = new HttpSession(new HashMap<>());
        cookie = new Cookie();
    }

    @DisplayName("FOUND 응답에 대한 테스트")
    @Test
    void testHttpResponseWithFound() {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put(LOCATION, "/index.html");

        assertThat(HttpResponse.sendRedirect("/index.html"))
                .isEqualTo(new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(headerMap)));
    }

    @DisplayName("OK 응답에 대한 테스트")
    @Test
    void testHttpResponseWithOK() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(HttpResponse.forward("/index.html"))
                .isEqualTo(new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.OK), new ResponseHeader(LOCATION, "/index.html"), new ResponseBody(body)));
    }


    @DisplayName("로그인 성공 테스트")
    @Test
    void testHttpResponseWithSuccessLogin() {
        httpSession.setAttribute("logined", "true");
        cookie.setCookie(JSESSIONID, httpSession.getId());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put(LOCATION, "/index.html");

        HttpResponse actualResponse = new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(headerMap), cookie);

        assertThat(actualResponse).isEqualTo(HttpResponse.sendRedirect("/index.html", cookie));
        assertThat(actualResponse.getCookie()).isEqualTo(cookie);
    }
}