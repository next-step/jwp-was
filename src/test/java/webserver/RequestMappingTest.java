package webserver;

import model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.RequestStream;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMappingTest {
    private String CREATE_USER_URL = "/user/create";
    private String LOGIN_URL = "/user/login";

    @Test
    @Disabled
    void route() throws IOException {
        InputStream inputStream = makeGetRequestInputStream();
        RequestStream requestStream = new RequestStream(inputStream);
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        HttpResponse httpResponse = new HttpResponse();
        User user = new User("javajigi", "password", "박재성", "javajigi@slipp.net");

        assertThat(RequestMapping.mapping(httpRequest, httpResponse).orElse("")).isEqualTo(user.toString());
    }

    @Test
    void postRequestCreateUserTest() throws IOException {
        InputStream inputStream =  makePostInputStream(CREATE_USER_URL, "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net");
        RequestStream requestStream = new RequestStream(inputStream);
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        HttpResponse httpResponse = new HttpResponse();

        assertThat(RequestMapping.mapping(httpRequest, httpResponse).orElse(""))
                .isEqualTo("redirect:/index.html");
    }

    @Test
    void userLoginSuccessTest() throws IOException {
        createUser();
        InputStream inputStream = makeLoginSuccessBufferedReader();
        RequestStream requestStream = new RequestStream(inputStream);
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        HttpResponse httpResponse = new HttpResponse();

        assertThat(RequestMapping.mapping(httpRequest, httpResponse).orElse(""))
                .isEqualTo("redirect:/index.html");
    }

    @Test
    void userLoginFailTest() throws IOException {
        createUser();
        InputStream inputStream = makeLoginFailBufferedReader();
        RequestStream requestStream = new RequestStream(inputStream);
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        HttpResponse httpResponse = new HttpResponse();

        assertThat(RequestMapping.mapping(httpRequest, httpResponse).orElse(""))
                .isEqualTo("redirect:/user/login_failed.html");
    }

    private void createUser() throws IOException {
        InputStream inputStream = makePostInputStream(CREATE_USER_URL, "userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net");
        RequestStream requestStream = new RequestStream(inputStream);
        HttpRequest httpRequest = HttpRequest.parse(requestStream);
        HttpResponse httpResponse = new HttpResponse();

        RequestMapping.mapping(httpRequest, httpResponse);
    }

    private InputStream makeLoginSuccessBufferedReader() {
        return makePostInputStream(LOGIN_URL, "userId=javajigi&password=password");
    }

    private InputStream makeLoginFailBufferedReader() {
        return makePostInputStream(LOGIN_URL, "userId=javajigi&password=pas");
    }

    private InputStream makePostInputStream(String url, String data) {
        String request = "POST " + url + " HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-Length: " + data.length() + "\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Accept: */*\n" +
                "\n" +
                data;

        return new ByteArrayInputStream(request.getBytes());
    }

    private InputStream makeGetRequestInputStream() {
        String request = "GET /create?userId=javajigi&password=password&name=박재성&email=javajigi%40slipp.net HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*\n" +
                "\n";

        return new ByteArrayInputStream(request.getBytes());
    }
}
