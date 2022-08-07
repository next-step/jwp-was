package webserver.http.response;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseMessageTest {

    @Test
    void staticResource_200_ok() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.staticResource("/css/styles.css");

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isNotEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 200 OK\r\n");
    }

    @Test
    void staticResource_404_not_found() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.staticResource("/css/nothing");

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 404 Not Found\r\n");
    }

    @Test
    void staticResource_content_type_by_file() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.staticResource("/css/styles.css");

        HttpResponseHeaders httpResponseHeaders = httpResponseMessage.getHttpResponseHeaders();
        assertThat(httpResponseHeaders.rawHeaders()).contains("Content-Type: text/css;charset=utf-8\r\n");
    }

    @Test
    void justOk() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.justOk();

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 200 OK\r\n");
    }

    @Test
    void page_ok() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.page("/index.html");

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isNotEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 200 OK\r\n");
    }

    @Test
    void page_not_found_page() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.page("/nothing.html");

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isNotEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 404 Not Found\r\n");
    }

    @Test
    void dynamicPage_200_ok() {
        Map<String, Object> viewModelMap = new HashMap<>();
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.dynamicPage("/user/list.html", viewModelMap);

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isNotEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 200 OK\r\n");
    }

    @Test
    void dynamicPage_404_not_found() {
        Map<String, Object> viewModelMap = new HashMap<>();
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.dynamicPage("/nothing.html", viewModelMap);

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isNotEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 404 Not Found\r\n");
    }

    @Test
    void redirect() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.redirect("/index.html");

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();
        List<String> rawHeaders = httpResponseMessage.getHttpResponseHeaders().rawHeaders();

        assertThat(rawHeaders).contains("Location: /index.html\r\n");
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 302 Found\r\n");
    }

    @Test
    void notFound() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.notFound();

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();

        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 404 Not Found\r\n");
    }

    @Test
    void notFoundPage() {
        HttpResponseMessage httpResponseMessage = HttpResponseMessage.notFoundPage();

        String rawStatusLine = httpResponseMessage.getHttpResponseStatusLine().rawStatusLine();

        assertThat(httpResponseMessage.getHttpResponseBody().getBodyBytes()).isNotEmpty();
        assertThat(rawStatusLine).isEqualTo("HTTP/1.1 404 Not Found\r\n");
    }
}
