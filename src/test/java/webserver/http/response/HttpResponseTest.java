package webserver.http.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseTest {

    @Test
    void getStaticFileIsStatusCode200(){
        final String path = "./static/js/scripts.js";
        final ContentType contentType = ContentType.JS;

        HttpResponse httpResponse = HttpResponse.getStaticFile(contentType, path);

        assertAll(
            () -> assertEquals(StatusCode.OK, httpResponse.getResponseLine().getStatusCode()),
            () -> assertEquals("text/javascript;charset=UTF-8", httpResponse.getResponseHeader().getContentType())
        );
    }

    @Test
    void getViewIsStatusCode200(){
        final String viewPath = "./templates/user/form.html";

        HttpResponse httpResponse = HttpResponse.getView(viewPath);

        assertEquals(StatusCode.OK, httpResponse.getResponseLine().getStatusCode());
    }

    @Test
    void getDynamicViewIsStatusCode200(){
        final String template = "<html>hi</html>";

        HttpResponse httpResponse = HttpResponse.getDynamicView(template);

        assertEquals(StatusCode.OK, httpResponse.getResponseLine().getStatusCode());
    }

    @Test
    void redirectIsStatusCode302(){
        final String redirectUrl = "/index.html";

        HttpResponse httpResponse = HttpResponse.redirect(redirectUrl);

        assertAll(
            () -> assertEquals("http://localhost:8888" + redirectUrl, httpResponse.getResponseHeader().getLocation()),
            () -> assertEquals(StatusCode.FOUND, httpResponse.getResponseLine().getStatusCode())
        );
    }

    @Test
    void notFoundIsStatusCode400(){
        HttpResponse httpResponse = HttpResponse.notFound();

        assertEquals(StatusCode.NOT_FOUND, httpResponse.getResponseLine().getStatusCode());
    }
}
