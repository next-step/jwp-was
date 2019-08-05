package webserver.http.response;

import org.junit.jupiter.api.Test;
import webserver.Ordered;
import webserver.response.ResponseHandler;
import webserver.response.ServletResponseHandler;
import webserver.response.StaticResponseHandler;

import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHandlerTest {

    @Test
    void responseHandlerOrderTest() {
        List<ResponseHandler> responseHandlers = asList(new StaticResponseHandler(), new ServletResponseHandler());
        responseHandlers.sort(Comparator.comparingInt(Ordered::getOrder));
        assertThat(responseHandlers.get(0).getClass().getName()).isEqualTo("webserver.response.ServletResponseHandler");
    }

}
