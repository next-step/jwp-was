package http.response;

import http.request.Headers;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static http.response.ContentType.HTML;
import static http.response.HttpStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {
    @DisplayName("Response에 Cookie(s) 추가")
    @Test
    void addCookies() {
        //given
        Cookies cookies = createCookies();
        Response response = createResponse();

        //when
        response.addCookies(cookies);

        //then
        assertThat(response.getCookies().getSize()).isEqualTo(2);
    }

    private Response createResponse() {
        return new Response(OK, HTML, new Headers(new HashMap<>()), new ResponseBody(Strings.EMPTY.getBytes()));
    }

    private Cookies createCookies() {
        Cookie cookie = new Cookie("JSESSIONID", "abc1234", "/", true);
        Cookie cookie2 = new Cookie("JSESSIONID2", "abc12345", "/index", false);
        return new Cookies(Arrays.asList(cookie, cookie2));
    }
}
