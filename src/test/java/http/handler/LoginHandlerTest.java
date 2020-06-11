package http.handler;

import db.DataBase;
import http.common.HttpEntity;
import http.common.HttpHeaders;
import http.handler.mapper.HandlerMapper;
import http.request.HttpRequest;
import http.request.requestline.RequestLine;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.common.HttpHeader.LOCATION_HEADER_NAME;
import static http.common.HttpMethod.POST;
import static http.handler.CreateUserHandler.INDEX_PATH;
import static http.handler.ListUserHandler.LOGIN_FAILED_PATH;
import static http.handler.ListUserHandler.TEMPLATE_PATH;
import static http.handler.LoginHandler.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class LoginHandlerTest {

    @Test
    void testGetHttpResponseForLoginSuccessUser() throws IOException, URISyntaxException {
        addUserToDataBase();

        RequestLine requestLine = RequestLine.parse(POST.name() + " " + HandlerMapper.LOGIN.getUrl() + " HTTP/1.1");
        String body = "userId=ninjasul&password=1234";

        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpEntity(HttpHeaders.EMPTY, body));

        Handler handler = new LoginHandler();
        HttpResponse httpResponse = handler.getHttpResponse(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getHttpHeaderMap()).contains(
                entry(LOCATION_HEADER_NAME, INDEX_PATH),
                entry(SET_COOKIE_HEADER_NAME, LOGIN_SUCCESS_COOKIE_VALUE + COOKIE_VALUE_DELIMITER + ROOT_PATH_COOKIE_VALUE)
        );
        assertThat(httpResponse.getHttpBody()).isEqualTo(new String(FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + INDEX_PATH)));

        DataBase.clearAllUsers();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "userId=ninjasul&password=1235",
            "userId=javajigi&password=1234",
            "userId=javajigi&password=1235",
    })
    @NullAndEmptySource
    void testGetHttpResponseForLoginFailedUser(String body) throws IOException, URISyntaxException {
        addUserToDataBase();

        RequestLine requestLine = RequestLine.parse(POST.name() + " " + HandlerMapper.LOGIN.getUrl() + " HTTP/1.1");
        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpEntity(HttpHeaders.EMPTY, body));

        Handler handler = new LoginHandler();
        HttpResponse httpResponse = handler.getHttpResponse(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getHttpHeaderMap()).contains(
                entry(LOCATION_HEADER_NAME, LOGIN_FAILED_PATH),
                entry(SET_COOKIE_HEADER_NAME, LOGIN_FAIL_COOKIE_VALUE + COOKIE_VALUE_DELIMITER + ROOT_PATH_COOKIE_VALUE)
        );
        assertThat(httpResponse.getHttpBody()).isEqualTo(new String(FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + LOGIN_FAILED_PATH)));

        DataBase.clearAllUsers();
    }

    private void addUserToDataBase() {
        User user = User.builder()
                .userId("ninjasul")
                .password("1234")
                .name("DongYub")
                .email("ninjasul@gmail.com")
                .build();

        DataBase.addUser(user);
    }
}