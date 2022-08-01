package webserver.http.view.response;

import com.google.common.base.Charsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.domain.Cookie;
import webserver.http.domain.response.Response;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static webserver.http.domain.ContentType.JSON;
import static webserver.http.domain.Headers.CONTENT_TYPE;

class ResponseWriterTest {

    @DisplayName("body가 포함된 Response 객체를 Http 응답 포맷에 맞춰 byte 타입의 배열에 출력한다.")
    @Test
    void write_with_body() {
        ResponseWriter responseWriter = new ResponseWriter();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(outputStream);
        Response response = setupResponseWithBody();

        responseWriter.write(dos, response);

        String writtenMessage = outputStream.toString(Charsets.UTF_8);
        assertThat(writtenMessage).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "Set-Cookie: logined=true; path=/user/only\r\n" +
                        "Content-Length: 61\r\n" +
                        "\r\n" +
                        "{\"name\": \"jordy\", \"age\": \"20\", \"comment\": \"반갑습니다.\"}"
        );
    }

    private Response setupResponseWithBody() {
        Response response = Response.ok();

        response.addHeader(CONTENT_TYPE, JSON.getHeader());
        response.addCookie(new Cookie("logined", "true", "/user/only"));

        String body = "{\"name\": \"jordy\", \"age\": \"20\", \"comment\": \"반갑습니다.\"}";
        response.addBody(body);
        return response;
    }

    @DisplayName("body가 포함되지 않은 Response 객체를 Http 응답 포맷에 맞춰 byte 타입의 배열에 출력한다.")
    @Test
    void write_with_out_body() {
        ResponseWriter responseWriter = new ResponseWriter();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(outputStream);
        Response response = Response.sendRedirect("/index.html");

        responseWriter.write(dos, response);

        String writtenMessage = outputStream.toString(Charsets.UTF_8);
        assertThat(writtenMessage).isEqualTo(
                "HTTP/1.1 302 Found\r\n" +
                        "Location: /index.html\r\n" +
                        "\r\n"
        );
    }
}