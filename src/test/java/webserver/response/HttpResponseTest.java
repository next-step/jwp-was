package webserver.response;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.request.Protocol.HTTP_1_1;
import static webserver.response.ResponseBody.EMPTY_RESPONSE_BODY;
import static webserver.response.StatusCode.NOT_FOUND;

import java.util.List;
import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import utils.FileIoUtils;
import webserver.request.ContentType;
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

        final HttpResponse expected = new HttpResponse(statusLine, responseHeaders, EMPTY_RESPONSE_BODY);

        // when
        final HttpResponse actual = HttpResponse.redirect("/index.html");

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("응답 헤더에 쿠키를 추가한다")
    @Test
    void add_cookie_to_response_headers() {
        // given
        final StatusLine statusLine = new StatusLine(Protocol.parse("HTTP/1.1"), StatusCode.FOUND);
        final ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.add("Location", "/index.html");
        responseHeaders.add("Set-Cookie", "logined=true; Path=/");

        final HttpResponse expected = new HttpResponse(statusLine, responseHeaders, EMPTY_RESPONSE_BODY);

        // when
        final HttpResponse actual = HttpResponse.redirect("/index.html")
            .addCookie("logined", "true");

        // then
        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("템플릿 엔진의 내용을 포함한 HttpResponse 객체를 생성한다")
    @Test
    void create_response_with_template_body() {
        // given
        User user = new User("admin", "pass", "administrator", "admin@email.com");

        final List<User> users = List.of(user);
        final Map<String, Object> param = Map.of("users", users);

        // when
        final HttpResponse actual = HttpResponse.ok("user/list", param);

        // then
        final String body = "\n"
            + "<tr>\n"
            + "  <td>1</td>\n"
            + "  <td>administrator</td>\n"
            + "</tr>\n"
            + "\n";

        final HttpResponse expected = responseOk(body, ContentType.HTML);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("템플릿 엔진으로 파일을 찾지 못한 HttpResponse 객체를 생성한다")
    @Test
    void create_response_with_template_not_found() {
        final HttpResponse actual = HttpResponse.ok("notfound", null);

        final HttpResponse expected = new HttpResponse(new StatusLine(HTTP_1_1, NOT_FOUND), new ResponseHeaders(), EMPTY_RESPONSE_BODY);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정적 파일을 찾은 후 HttpResponse 객체를 응답한다")
    @ParameterizedTest
    @CsvSource(value = {
        "/index.html, '<html><body>Hello world!</body></html>\n'",
        "/css/style.css, '.header {background-color:#ffffff;border-width:0;}\n'"
    }, delimiter = ',')
    void create_response_with_static_file(String location, String body) {
        final HttpResponse actual = HttpResponse.ok(location);

        final HttpResponse expected = responseOk(body, ContentType.of(FileIoUtils.getFileExtension(location)));

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정적 파일을 찾지 못한 HttpResponse 객체를 생성한다")
    @Test
    void create_response_with_static_file_not_found() {
        final HttpResponse actual = HttpResponse.ok("notfound.html");

        final HttpResponse expected = new HttpResponse(new StatusLine(HTTP_1_1, NOT_FOUND), new ResponseHeaders(), EMPTY_RESPONSE_BODY);

        assertThat(actual).isEqualTo(expected);

    }

    private HttpResponse responseOk(final String body, final ContentType contentType) {
        final ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.add("Content-Type", contentType.getMediaType());
        responseHeaders.add("Content-Length", String.valueOf(body.getBytes().length));

        return new HttpResponse(
            new StatusLine(Protocol.parse("HTTP/1.1"), StatusCode.OK),
            responseHeaders,
            new ResponseBody(body)
        );
    }
}
