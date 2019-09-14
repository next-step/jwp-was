package webserver.http.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpResponseResolverTest {
    @Test
    void status200Ok() {
        //filePath, contentType
        String filePath = "/index.html";
        String contentType = "text/html";
        HttpResponse httpResponse = HttpResponseResolver.forward(contentType, filePath);

        assertNotNull(httpResponse);
    }

    @Test
    void status302Found() {
        String redirectPath = "/index.html";
        HttpResponse httpResponse = HttpResponseResolver.redirect(redirectPath, null);

        assertNotNull(httpResponse);
    }

    @Test
    void resource() {
        String resourcePath = "/js/bootstrap.min.js";
        HttpResponse httpResponse = HttpResponseResolver.resource(resourcePath);

        assertNotNull(httpResponse);
    }
}