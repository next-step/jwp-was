package controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    private String testDirectory = "./src/test/resources/http/";

    @Test
    void 로그인테스트_성공() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_LOGIN.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new LoginController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();
        System.out.println(response);
        assertThat(response).contains("302 Found");
        assertThat(response).contains("logined=true;");
        assertThat(response).contains("/index.html");
    }

    @Test
    void 로그인테스트_실패() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_LOGIN_FAIL.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new LoginController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertThat(response).contains("302 Found");
        assertThat(response).contains("/user/login_failed.html");
    }

    private InputStream createInputStream(String filename) throws FileNotFoundException {
        return new FileInputStream(testDirectory + filename);
    }
}
