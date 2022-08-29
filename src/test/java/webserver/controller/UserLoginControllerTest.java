package webserver.controller;

import db.DataBase;
import http.httprequest.HttpRequest;
import http.httprequest.requestbody.RequestBody;
import http.httprequest.requestheader.RequestHeader;
import http.httprequest.requestline.HttpMethod;
import http.httprequest.requestline.Path;
import http.httprequest.requestline.Protocol;
import http.httprequest.requestline.ProtocolType;
import http.httprequest.requestline.RequestLine;
import http.httprequest.requestline.RequestParams;
import http.httprequest.requestline.Version;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserLoginControllerTest {
    @Test
    @DisplayName("경로가 잘 설정됐는지 확인")
    void getPath() {
        Controller controller = new UserLoginController();
        assertThat(controller.getPath()).isEqualTo("/user/login");
    }

    @Test
    @DisplayName("execute")
    void execute() throws IOException, URISyntaxException {
        DataBase.addUser(new User("test", "1234", "test", "test@test.com"));
        Controller controller = new UserLoginController();
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(
                        HttpMethod.POST,
                        new Path("/user/login", new RequestParams(Collections.emptyMap())),
                        new Protocol(ProtocolType.HTTP, new Version("1.1"))
                ),
                RequestHeader.from(Collections.emptyList()),
                new RequestBody(Map.of("userId", "test","password", "1234"))
        );

        HttpResponse httpResponse = controller.serve(httpRequest);

        assertAll(
                () -> assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.FOUND),
                () -> assertThat(httpResponse.getResponseHeader().getHeaderEntries().contains(Map.entry("Location", "/index.html"))),
                () -> assertThat(httpResponse.getResponseHeader().getHeaderEntries().contains(Map.entry("Set-Cookie", "logined=true; Path=/")))
        );
    }
}