package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;
import webserver.http.Cookie;
import webserver.http.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static model.Constant.LOCATION;
import static model.Constant.PROTOCOL_VERSION_ONE_ONE;
import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @DisplayName("FOUND 응답에 대한 테스트")
    @Test
    void testHttpResponseWithFound() {
        assertThat(HttpResponse.sendRedirect("/index.html"))
                .isEqualTo(new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, "/index.html")));
    }

    @DisplayName("OK 응답에 대한 테스트")
    @Test
    void testHttpResponseWithOK() throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        assertThat(HttpResponse.forward("/index.html"))
                .isEqualTo(new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.OK), new ResponseHeader(), new ResponseBody(body)));
    }


    @DisplayName("로그인 성공 테스트")
    @Test
    void testHttpResponseWithSuccessLogin() {
        Map<String, String> cookieMap = new HashMap<>();
        cookieMap.put("set-cookie", "logined=true; Path=/");

        HttpResponse actualResponse = new HttpResponse(new ResponseLine(PROTOCOL_VERSION_ONE_ONE, HttpStatus.FOUND), new ResponseHeader(LOCATION, "/index.html"), new Cookie(cookieMap));

        assertThat(actualResponse).isEqualTo(HttpResponse.sendRedirect("/index.html", cookieMap));
    }
}