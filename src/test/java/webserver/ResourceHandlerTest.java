package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.RequestUri;

import static org.assertj.core.api.Java6Assertions.assertThat;

class ResourceHandlerTest {

    /**
     * /css/bootstrap.min.css
     * /js/jquery-2.2.0.min.js
     */
    @DisplayName("uri path에 css가 포함되는 경우, path에 static을 포함하는지")
    @Test
    void static_resource_css() {
        RequestUri requestUri = RequestUri.parse("/css/bootstrap.min.css");

        assertThat(ResourceHandler.getResourcePath(requestUri)).contains("./static");
    }

    @DisplayName("uri path에 js가 포함되는 경우, path에 static을 포함하는지")
    @Test
    void static_resource_js() {
        RequestUri requestUri = RequestUri.parse("/css/bootstrap.min.css");

        assertThat(ResourceHandler.getResourcePath(requestUri)).contains("./static");
    }

    @DisplayName("template을 load해야하는 경우, path에 template을 포함하는지")
    @Test
    public void template_resource() {
        RequestUri requestUri = RequestUri.parse("/index.html");

        assertThat(ResourceHandler.getResourcePath(requestUri)).contains("./templates");
    }
}