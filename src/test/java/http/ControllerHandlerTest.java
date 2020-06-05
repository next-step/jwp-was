package http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import http.controller.UserController;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created By kjs4395 on 2020-06-05
 */
public class ControllerHandlerTest {

    @Test
    void geUserControllerTest() {
        RequestLine requestLine = RequestLineParser.parse("GET /user/create HTTP/1.1");
        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpHeaderInfo(), StringUtils.EMPTY);

        assertThat(ControllerHandler.getPathController(httpRequest)).isInstanceOf(UserController.class);
    }
}
