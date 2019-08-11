package webserver.http.response;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.common.header.Header;
import webserver.http.request.ControllerTest;
import webserver.http.response.header.Cookie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @author : yusik
 * @date : 2019-08-10
 */
@DisplayName("HttpResponse")
public class HttpResponseTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);

    private HttpResponse response;

    @BeforeEach
    void init() {
        response = new HttpResponse(new ByteArrayBuffer());
    }

    @DisplayName("ContentType 테스트")
    @Test
    void contentTypeTest() {

        HttpResponse response = new HttpResponse(new ByteArrayBuffer());
        response.setContentType(ContentType.JS);
        assertEquals(ContentType.JS.getMediaType(), response.getHeaders().get(Header.CONTENT_TYPE.getName()));
    }

    @DisplayName("쿠키 테스트")
    @Test
    void cookieTest() {

        // case 1
        Cookie cookie = new Cookie("test", "value");
        cookie.setPath("/test");
        cookie.setDomain("127.0.0.1");
        cookie.setExpires("2022-07-22T15:21:05.000Z");
        logger.debug("case1: {}", cookie);
        response.setCookie(cookie);
        assertEquals(cookie, response.getCookie());

        // case 2
        response.setCookie("test", "value");
        logger.debug("case2: {}", response.getCookie());
        assertNotNull(response.getCookie());
    }

    @DisplayName("응답코드 테스트")
    @Test
    void statusTest() {

        response.setHttpStatus(HttpStatus.BAD_REQUEST);

        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), response.getHttpStatus().getReasonPhrase());
    }
}
