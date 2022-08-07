package controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserControllerTest {

    private String testDirectory = "./src/test/resources/http/";

    @Test
    void 회원가입테스트() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_CREATE.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new CreateUserController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertThat(response).contains("HTTP/1.1 302 Found");
        assertThat(response).contains("Location: /index.html");
    }

    @Test
    void 회원가입테스트_RequestBody() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_CREATE_BODY.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new CreateUserController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertThat(response).contains("HTTP/1.1 302 Found");
        assertThat(response).contains("Location: /index.html");
    }

    @Test
    void 회원가입테스트_잘못된HTTP_METHOD() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_CREATE_INVALID_METHOD.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new CreateUserController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();
        System.out.println(response);

        assertThat(response).contains("HTTP/1.1 405 Method Not Allowed");
    }

    private InputStream createInputStream(String filename) throws FileNotFoundException {
        return new FileInputStream(testDirectory + filename);
    }
}
