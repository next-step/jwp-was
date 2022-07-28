package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.Protocol;

class HttpResponseTest {

    @DisplayName("정상 응답 상태 코드와 응답 본문을 가진 HttpResponse 객체를 생성한다")
    @Test
    void response_ok_with_body() {
        final StatusLine statusLine = new StatusLine(Protocol.parse("HTTP/1.1"), StatusCode.OK);
        final ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.add("Content-Type", "text/html");
        responseHeaders.add("Content-Length", "39");
        final ResponseBody responseBody = new ResponseBody("<html><body>Hello, World!</body></html>");

        final HttpResponse response = new HttpResponse(statusLine, responseHeaders, responseBody);

        assertThat(response).isEqualTo(new HttpResponse(statusLine, responseHeaders, responseBody));
    }

    @DisplayName("페이지 이동 상태 코드와 응답 본문이 없는 HttpResponse 객체를 생성한다")
    @Test
    void response_found_without_body() {
        // given
        final StatusLine statusLine = new StatusLine(Protocol.parse("HTTP/1.1"), StatusCode.FOUND);
        final ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.add("Location", "/index.html");
        final ResponseBody responseBody = ResponseBody.EMPTY_RESPONSE_BODY;

        final HttpResponse expected = new HttpResponse(statusLine, responseHeaders, responseBody);

        // when
        final HttpResponse actual = HttpResponse.redirect("/index.html");

        // then
        assertThat(actual).isEqualTo(expected);
    }

}
