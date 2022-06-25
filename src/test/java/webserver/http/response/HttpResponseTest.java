package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.Header;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestLine;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpResponseTest {

    @DisplayName("Http Response 객체를 생성할수 있다.")
    @Test
    void create() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users?username=dean HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);

        assertThatCode(() -> new HttpResponse(httpRequest)).doesNotThrowAnyException();
    }

    @DisplayName("200 응답값을 생성할수 있다.")
    @Test
    void ok_200() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users?username=dean HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        httpResponse.ok("ok".getBytes());

        assertAll(
                () -> assertThat(httpResponse.getLength()).isEqualTo(2),
                () -> assertThat(httpResponse.getStatus()).isEqualTo(200),
                () -> assertThat(httpResponse.getStatusMessage()).isEqualTo("200 OK")
        );
    }

    @DisplayName("302 응답값을 생성할수 있다.")
    @Test
    void redirect_302() {
        HttpRequest httpRequest = new HttpRequest(
                RequestLine.parse("GET /users?username=dean HTTP/1.1"),
                new Header(Collections.emptyMap(), Collections.emptyMap()), null);
        HttpResponse httpResponse = new HttpResponse(httpRequest);

        httpResponse.redirect("/index");

        assertAll(
                () -> assertThat(httpResponse.getStatus()).isEqualTo(302),
                () -> assertThat(httpResponse.getStatusMessage()).isEqualTo("302 Found"),
                () -> assertThat(httpResponse.getLocation()).isEqualTo("/index")
        );
    }

}
