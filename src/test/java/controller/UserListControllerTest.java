package controller;

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

class UserListControllerTest {

    @Test
    void service() throws Exception {
        RequestLine requestLine = new RequestLine("GET /user/list.html HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Cookie", "logined=true; Path=/");
        headers.put("Accept", "text/html;charset=utf-8");

        OutputStream outputStream = new ByteArrayOutputStream();

        HttpRequest httpRequest = new HttpRequest(requestLine, headers, null);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        RequestControllerContainer.match(httpRequest)
                .service(httpRequest, httpResponse);

        String response = outputStream.toString();

        assertThat(response).contains("HTTP/1.1 " + HttpStatus.OK + " \r\n");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8 \r\n");
    }

    @Test
    void service_unauthorized() throws Exception {
        RequestLine requestLine = new RequestLine("GET /user/list.html HTTP/1.1");

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Cookie", "logined=false; Path=/");
        headers.put("Accept", "text/html;charset=utf-8");

        OutputStream outputStream = new ByteArrayOutputStream();

        HttpRequest httpRequest = new HttpRequest(requestLine, headers, null);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        RequestControllerContainer.match(httpRequest)
                .service(httpRequest, httpResponse);

        String response = outputStream.toString();

        assertThat(response).contains("HTTP/1.1 " + HttpStatus.FOUND + " \r\n");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8 \r\n");
    }
}
