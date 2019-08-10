package webserver.resolver;

import db.DataBase;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import support.TestHttpMessageLoader;
import webserver.request.HttpRequest;
import webserver.request.HttpRequestFactory;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.InputStream;

/**
 * Created by hspark on 2019-08-11.
 */
class RequestResolverTest {
    public RequestResolver requestResolver = new RequestResolver();

    @Test
    void test_index() throws Exception {
        InputStream in = TestHttpMessageLoader.load("Http_GET_index.txt");
        HttpRequest httpRequest = HttpRequestFactory.create(in);
        HttpResponse httpResponse = new HttpResponse();
        requestResolver.resolve(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void test_login_fail() throws Exception {
        InputStream in = TestHttpMessageLoader.load("Http_POST_login.txt");
        HttpRequest httpRequest = HttpRequestFactory.create(in);
        HttpResponse httpResponse = new HttpResponse();
        requestResolver.resolve(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(httpResponse.getHeader("Location")).isEqualTo("/user/login_failed.html");
    }

    @Test
    void test_login_success() throws Exception {
        User user = new User("phs1116", "1234", "test", "test@test.com");
        DataBase.addUser(user);
        InputStream in = TestHttpMessageLoader.load("Http_POST_login.txt");
        HttpRequest httpRequest = HttpRequestFactory.create(in);
        HttpResponse httpResponse = new HttpResponse();
        requestResolver.resolve(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(httpResponse.getHeader("Location")).isEqualTo("/index.html");
    }

    @Test
    void test_signup() throws Exception {
        InputStream in = TestHttpMessageLoader.load("Http_GET_signUp.txt");
        HttpRequest httpRequest = HttpRequestFactory.create(in);
        HttpResponse httpResponse = new HttpResponse();
        requestResolver.resolve(httpRequest, httpResponse);


        User actual = DataBase.findUserById("test");
        Assertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        Assertions.assertThat(httpResponse.getHeader("Location")).isEqualTo("/index.html");
        Assertions.assertThat(actual.getUserId()).isEqualTo("test");
        Assertions.assertThat(actual.getPassword()).isEqualTo("test");
        Assertions.assertThat(actual.getName()).isEqualTo("test");
        Assertions.assertThat(actual.getEmail()).isEqualTo("test@test");
    }
}