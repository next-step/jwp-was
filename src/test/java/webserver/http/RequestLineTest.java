package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class RequestLineTest {

    @DisplayName("GET Method의 대한 정보를 파싱한다.")
    @Test
    void getMethodRequest(){

        final String requestInput = "GET /users HTTP/1.1";

        HttpRequest getRequest = new RequestLine(requestInput).toRequest();

        then(getRequest).isNotNull();
        then(getRequest.getHttpMethod()).isEqualTo("GET");
        then(getRequest.getPath()).isEqualTo("/users");
        then(getRequest.getVersion()).isEqualTo("1.1");
    }
}
