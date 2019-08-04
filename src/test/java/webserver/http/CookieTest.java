package webserver.http;

import org.junit.jupiter.api.Test;
import request.HttpMethod;
import request.HttpRequest;

import java.io.BufferedReader;
import java.io.StringReader;

import static header.Cookie.LOGIN_KEY;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by youngjae.havi on 2019-08-03
 */
public class CookieTest {

    @Test
    void cookie_test() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("GET /index.html HTTP/1.1\n" +
                "Cookie: logined=false\n"));
        HttpRequest httpRequest = new HttpRequest(bufferedReader);

        assertThat(httpRequest.getRequestLine().getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.getCookie().get(LOGIN_KEY)).isEqualTo("false");
    }
}
