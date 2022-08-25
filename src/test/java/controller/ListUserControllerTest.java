package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import webserver.Cookie;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ListUserControllerTest {

    private static ListUserController listUserController;

    @BeforeAll
    static void setUp() {
        listUserController = new ListUserController();
    }

    private InputStream createInputStream(String fileName) throws FileNotFoundException {
        String testDirectory = "./src/test/resources/";
        return new FileInputStream(testDirectory + fileName);
    }

    @Test
    void success() throws Exception {
        // given
        HttpRequest httpRequest = new HttpRequest(createInputStream("Http_UserList_200.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse httpResponse = new HttpResponse(dataOutputStream);
        Cookie cookie = httpRequest.getCookie();

        // when
        listUserController.doGet(httpRequest, httpResponse);

        // then
        assertThat(cookie.getValue("logined")).isEqualTo("true");
        assertThat(httpResponse.getHeaders().getValue("Location")).isEqualTo("user/list");
    }

    @Test
    void failure() throws Exception {
        // given
        HttpRequest httpRequest = new HttpRequest(createInputStream("Http_UserList_302.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse httpResponse = new HttpResponse(dataOutputStream);
        Cookie cookie = httpRequest.getCookie();

        // when
        listUserController.doGet(httpRequest, httpResponse);

        // then
        assertThat(cookie.getValue("logined")).isEqualTo("false");
        assertThat(httpResponse.getHeaders().getValue("Location")).isEqualTo("/login.html");
    }
}
