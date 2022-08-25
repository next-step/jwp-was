package controller;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import webserver.Cookie;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private static LoginController controller;

    @BeforeAll
    static void setUp() {
        controller = new LoginController();
        User user = new User(
                "javajigi",
                "password",
                "JaeSung",
                "javajigi@40slipp.net"
        );
        DataBase.addUser(user);
    }

    private InputStream createInputStream(String fileName) throws FileNotFoundException {
        String testDirectory = "./src/test/resources/";
        return new FileInputStream(testDirectory + fileName);
    }

    @Test
    void success() throws Exception {
        // given
        HttpRequest httpRequest = new HttpRequest(createInputStream("Http_Login_Success.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse httpResponse = new HttpResponse(dataOutputStream);

        // when
        controller.doPost(httpRequest, httpResponse);

        // then
        assertAll(
                () -> assertThat(httpResponse.getHeaders().getValue("Location")).isEqualTo("/index.html"),
                () -> assertThat(httpResponse.getHeaders().getValue(Cookie.SET_COOKIE).split(";")[0]).isEqualTo("logined=true")
        );
    }

    @Test
    void failure() throws Exception {
        // given
        HttpRequest httpRequest = new HttpRequest(createInputStream("Http_Login_Failure.txt"));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        HttpResponse httpResponse = new HttpResponse(dataOutputStream);

        // when
        controller.doPost(httpRequest, httpResponse);

        // then
        assertAll(
                () -> assertThat(httpResponse.getHeaders().getValue("Location")).isEqualTo("/login_failed.html"),
                () -> assertThat(httpResponse.getHeaders().getValue(Cookie.SET_COOKIE).split(";")[0]).isEqualTo("logined=false")
        );
    }
}
