package controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ListUserControllerTest {

    private String testDirectory = "./src/test/resources/http/";

    @Test
    void 회원목록조회테스트_로그인상태() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_LIST_LOGIN.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new ListUserController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        System.out.println(response);
        assertThat(response).contains("HTTP/1.1 200 OK");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8");
    }

    @Test
    void 회원목록조회테스트_로그인안한상태() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_LIST_NOTLOGIN.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new ListUserController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        System.out.println(response);
        assertThat(response).contains("HTTP/1.1 302 Found");
        assertThat(response).contains("Location: /user/login.html");
    }

    private InputStream createInputStream(String filename) throws FileNotFoundException {
        return new FileInputStream(testDirectory + filename);
    }
}
