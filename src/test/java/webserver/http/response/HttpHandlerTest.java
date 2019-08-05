package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.Ordered;
import webserver.http.HttpHandler;
import webserver.http.HttpServletHandler;
import webserver.http.HttpStaticHandler;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class HttpHandlerTest {

    @Test
    void responseHandlerOrderTest() {
        List<HttpHandler> httpHandlers = asList(new HttpStaticHandler(), new HttpServletHandler());
        httpHandlers.sort(Comparator.comparingInt(Ordered::getOrder));
        assertThat(httpHandlers.get(0).getClass().getName()).isEqualTo("webserver.http.HttpServletHandler");
    }

}
