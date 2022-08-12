package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.header.Header;
import webserver.http.request.requestline.Protocol;
import webserver.http.response.statusline.StatusCode;
import webserver.http.response.statusline.StatusLine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpResponseTest {
    @Test
    @DisplayName("HttpResponse 를 생성한다.")
    void create_HttpRequest() {
        HttpResponse httpResponse = new HttpResponse(new StatusLine(Protocol.ofHttpV11(), StatusCode.OK), new Header(), new byte[0]);
        assertThat(httpResponse).isNotNull().isInstanceOf(HttpResponse.class);
    }

    @Test
    @DisplayName("HttpResponse 응답라인, 헤더가 null 일 경우 예외가 발생한다.")
    void throw_exception_response_null() {
        assertAll(
                () -> assertThatThrownBy(() -> new HttpResponse(null, new Header(), new byte[0])).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new HttpResponse(StatusLine.of(Protocol.ofHttpV11(), StatusCode.OK), null, new byte[0])).isInstanceOf(IllegalArgumentException.class)
        );
    }
}