package webserver.http.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseTest {

    @Test
    void redirectIsStatusCode302(){
        final String redirectUrl = "/index.html";

        HttpResponse httpResponse = HttpResponse.redirect(redirectUrl);

        assertAll(
            () -> assertEquals("http://localhost:8888" + redirectUrl, httpResponse.getResponseHeader().getLocation()),
            () -> assertEquals(StatusCode.FOUND, httpResponse.getResponseLine().getStatusCode())
        );
    }
}
