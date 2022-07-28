package controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.exception.MethodNotAllowedException;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateUserControllerTest {

    private String testDirectory = "./src/test/resources/http/";

    @Test
    void 회원가입테스트() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_CREATE.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new CreateUserController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertThat(response).contains("302 Found");
        assertThat(response).contains("/index.html");
    }

    @Test
    void 회원가입테스트_RequestBody() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_CREATE_BODY.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new CreateUserController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertThat(response).contains("302 Found");
        assertThat(response).contains("/index.html");
    }

    @Test
    void 회원가입테스트_잘못된HTTP_METHOD() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_CREATE_INVALID_METHOD.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        assertThatThrownBy(
                () -> new CreateUserController().service(request, httpResponse)
        ).isInstanceOf(MethodNotAllowedException.class);
    }

    private InputStream createInputStream(String filename) throws FileNotFoundException {
        return new FileInputStream(testDirectory + filename);
    }
}
