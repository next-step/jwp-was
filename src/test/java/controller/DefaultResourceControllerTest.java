package controller;

import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultResourceControllerTest {

    private String testDirectory = "./src/test/resources/http/";

    @Test
    void 리소스_파일_HTML_테스트() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_HTML.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new DefaultResourceController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertThat(response).contains("HTTP/1.1 200 OK");
        assertThat(response).contains("Content-Type: text/html;charset=utf-8");
        assertThat(response).contains("Content-Length:");
    }

    @Test
    void 리소스_파일_CSS_테스트() throws Exception {
        HttpRequest request = new HttpRequest(createInputStream("HTTP_CSS.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HttpResponse httpResponse = new HttpResponse(byteArrayOutputStream);

        new DefaultResourceController().service(request, httpResponse);

        String response = byteArrayOutputStream.toString();

        assertThat(response).contains("HTTP/1.1 200 OK");
        assertThat(response).contains("Content-Type: text/css,*/*;q=0.1");
        assertThat(response).contains("Content-Length:");
    }

    private InputStream createInputStream(String filename) throws FileNotFoundException {
        return new FileInputStream(testDirectory + filename);
    }
}
