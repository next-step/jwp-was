package controller;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.RequestControllerContainer;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.RequestLine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    @BeforeEach
    void setUp() {
        DataBase.clear();
    }

    @Test
    void service() throws Exception {
        DataBase.addUser(new User(
                "javajigi",
                "P@ssw0rD",
                "JaeSung",
                "javajigi@slipp.net"
        ));

        RequestLine requestLine = new RequestLine("GET /user/login?userId=javajigi&password=P@ssw0rD HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Accept", "*/*");

        OutputStream outputStream = new ByteArrayOutputStream();

        HttpRequest httpRequest = new HttpRequest(requestLine, headers, null);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        RequestControllerContainer.match(httpRequest)
                .service(httpRequest, httpResponse);

        String response = outputStream.toString();

        assertThat(response).contains("HTTP/1.1 " + HttpStatus.FOUND + " \r\n");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8 \r\n");
        assertThat(response).contains("Set-Cookie: logined=true; Path=/ \r\n");
        assertThat(response).contains("Location: /index.html \r\n");
        assertThat(response).contains("Content-Length: 0 \r\n");
    }

    @Test
    void service_fail() throws Exception {
        RequestLine requestLine = new RequestLine("GET /user/login?userId=test&password=111 HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Accept", "*/*");

        OutputStream outputStream = new ByteArrayOutputStream();

        HttpRequest httpRequest = new HttpRequest(requestLine, headers, null);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        RequestControllerContainer.match(httpRequest)
                .service(httpRequest, httpResponse);

        String response = outputStream.toString();

        assertThat(response).contains("HTTP/1.1 " + HttpStatus.BAD_REQUEST + " \r\n");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8 \r\n");
        assertThat(response).contains("Set-Cookie: logined=false; Path=/ \r\n");
        assertThat(response).contains("Location: /user/login_failed.html \r\n");
        assertThat(response).contains("Content-Length: 0 \r\n");
    }
}
