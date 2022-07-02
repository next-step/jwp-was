package controller;

import db.DataBase;
import http.*;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CreatUserControllerTest {

    @BeforeEach
    void setup() {
        DataBase.clear();
    }

    @Test
    void serving_post() throws IOException, URISyntaxException {
        RequestLine requestLine = new RequestLine("POST /user/create HTTP/1.1");
        String body = "userId=javajigi&password=password&name=JaeSung&email=javajigi@slipp.net";

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Content-Length", String.valueOf(body.getBytes().length));
        headers.put("Accept", "*/*");


        HttpRequest httpRequest = new HttpRequest(
                requestLine,
                headers,
                body);

        HttpResponse httpResponse = RequestControllerContainer.match(httpRequest);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.FOUND);
        assertThat(httpResponse.getContentType()).isEqualTo(MediaType.TEXT_HTML_UTF8);
        assertThat(httpResponse.getPath()).isEqualTo("/index.html");

        User user = DataBase.findUserById("javajigi");

        assertThat(user.getUserId()).isEqualTo("javajigi");
        assertThat(user.checkPassword("password")).isTrue();
        assertThat(user.getName()).isEqualTo("JaeSung");
        assertThat(user.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    void serving_get() throws IOException, URISyntaxException {
        RequestLine requestLine = new RequestLine("GET /user/create?" +
                "userId=javajigi&" +
                "password=password&" +
                "name=JaeSung&" +
                "email=javajigi@slipp.net" +
                " HTTP/1.1");

        HttpRequest httpRequest = new HttpRequest(requestLine, null, null);

        HttpResponse httpResponse = RequestControllerContainer.match(httpRequest);

        assertThat(httpResponse.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);

        User user = DataBase.findUserById("javajigi");

        assertThat(user).isNull();
    }

}