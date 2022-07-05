package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class RequestLineTest {

    private static final String HTTP_VERSION_EXPECTED = "1.1";

    @DisplayName("GET Method의 대한 정보를 파싱한다.")
    @Test
    void getMethodRequest(){

        final String requestInput = "GET /users HTTP/1.1";

        HttpRequest httpRequest = new RequestLine(requestInput).toRequest();

        then(httpRequest).isNotNull();
        then(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        then(httpRequest.getUri().getPath()).isEqualTo("/users");
        then(httpRequest.getHttpProtocol().getVersion()).isEqualTo(HTTP_VERSION_EXPECTED);
    }

    @DisplayName("POST Method의 대한 정보를 파싱한다.")
    @Test
    void postMethodRequest(){

        final String requestInput = "POST /users HTTP/1.1";

        HttpRequest httpRequest = new RequestLine(requestInput).toRequest();

        then(httpRequest).isNotNull();
        then(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.POST);
        then(httpRequest.getUri().getPath()).isEqualTo("/users");
        then(httpRequest.getHttpProtocol().getVersion()).isEqualTo(HTTP_VERSION_EXPECTED);
    }

    @DisplayName("Query String 파서 검증")
    @Test
    void queryStringParserTest() {

        final String queryString = "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1";

        HttpRequest httpRequest = new RequestLine(queryString).toRequest();

        then(httpRequest).isNotNull();
        then(httpRequest.getHttpMethod()).isEqualTo(HttpMethod.GET);
        then(httpRequest.getUri().getPath()).isEqualTo("/users");
        then(httpRequest.getHttpProtocol().getVersion()).isEqualTo(HTTP_VERSION_EXPECTED);
    }
}
