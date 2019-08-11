package webserver.http.response.header;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class CookieTest {

    private static final Logger logger = LoggerFactory.getLogger(CookieTest.class);
    private static String cookieString;

    @BeforeAll
    public static void init() {
        cookieString = "webid=2788afec5eb442ea945a0f392990036d; dtck_first=20190808; dtck_media=-1; " +
                "dtck_blog=-1; dtck_channel=-1; dtck_photo_slide=-1; dtmulti=-1; " +
                "TIARA=QPSBLfMknYbXW8KwsUFBkt8J.1EVaJOSwrn2rDgSb9hFT1q3V9qvDD7cNOQ4mQX__DF8Wy2KSMdvYP1p-RnPjozw.GeXCBI2; " +
                "dtck_refresh=0; webid_sync=1565429223130";
    }

    @DisplayName("요청 쿠키 파싱")
    @Test
    void parseCookie() {

        Map<String, String> cookies = Cookie.parse(cookieString);
        logger.debug("{}", cookies);
        assertFalse(cookies.isEmpty());
    }

    @DisplayName("응답 쿠키 생성")
    @Test
    void createCookie() {

        Cookie cookie = new Cookie("dtck_refresh", "0");
        cookie.setPath("/");

        logger.debug("{}", cookie);
        assertTrue(cookie.toString().contains("dtck_refresh"));
        assertTrue(cookie.toString().contains("/"));
    }
}
