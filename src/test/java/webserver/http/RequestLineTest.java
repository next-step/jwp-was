package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestLineTest {

    @DisplayName("GET Request 검증")
    @Test
    void getMethodRequest() {

        HttpRequest httpRequest = new RequestLine(RequestFixture.ofGetRequest()).toRequest();

        then(httpRequest).isNotNull();
        then(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        then(httpRequest.getUri().getPath()).isEqualTo(RequestFixture.PATH);
        then(httpRequest.getHttpProtocol().toString()).isEqualTo(RequestFixture.PROTOCOL);
    }

    @DisplayName("POST Request 검증")
    @Test
    void postMethodRequest() {

        HttpRequest httpRequest = new RequestLine(RequestFixture.ofPostRequest()).toRequest();

        then(httpRequest).isNotNull();
        then(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.POST);
        then(httpRequest.getUri().getPath()).isEqualTo(RequestFixture.PATH);
        then(httpRequest.getHttpProtocol().toString()).isEqualTo(RequestFixture.PROTOCOL);
    }

    @DisplayName("Query String 검증")
    @Test
    void queryStringRequest() {

        HttpRequest httpRequest = new RequestLine(RequestFixture.ofGetAndQueryStringRequest()).toRequest();

        then(httpRequest).isNotNull();
        then(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        then(httpRequest.getUri().getPath()).isEqualTo(RequestFixture.PATH);
        then(httpRequest.getHttpProtocol().toString()).isEqualTo(RequestFixture.PROTOCOL);
    }

    @DisplayName("잘못된 요청 검증")
    @Test
    void badRequest() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new RequestLine(RequestFixture.ofBadRequest())
        );
    }
}
