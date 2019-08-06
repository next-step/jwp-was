package webserver.http;

import org.junit.jupiter.api.Test;
import webserver.http.Ordered;
import webserver.http.HttpHandler;
import webserver.http.HttpControllerHandler;
import webserver.http.HttpStaticHandler;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class HttpHandlerTest {

    @Test
    void responseHandlerOrderTest() {
        List<HttpHandler> httpHandlers = asList(new HttpStaticHandler(), new HttpControllerHandler());
        httpHandlers.sort(Comparator.comparingInt(Ordered::getOrder));
        assertThat(httpHandlers.get(0).getClass().getName()).isEqualTo("webserver.http.HttpControllerHandler");
    }

}
