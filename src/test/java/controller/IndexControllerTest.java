package controller;

import org.junit.jupiter.api.Test;
import webserver.RequestControllerContainer;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.RequestLine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest {

    @Test
    void service() throws Exception {
        OutputStream outputStream = new ByteArrayOutputStream();

        HttpRequest httpRequest = new HttpRequest(new RequestLine("GET / HTTP/1.1"), null, null);
        HttpResponse httpResponse = new HttpResponse(outputStream);

        RequestControllerContainer.match(httpRequest)
                .service(httpRequest, httpResponse);

        String response = outputStream.toString();

        assertThat(response).contains("HTTP/1.1 " + HttpStatus.FOUND + " \r\n");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8 \r\n");
        assertThat(response).contains("Location: /index.html \r\n");
        assertThat(response).contains("Content-Length: 0 \r\n");
    }
}