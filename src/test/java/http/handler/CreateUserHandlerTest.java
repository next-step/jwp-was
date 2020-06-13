package http.handler;

import db.DataBase;
import http.common.HttpEntity;
import http.common.HttpHeaders;
import http.handler.mapper.Dispatcher;
import http.request.HttpRequest;
import http.request.requestline.RequestLine;
import http.response.HttpResponse;
import model.User;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

import static http.common.HttpHeader.LOCATION_HEADER_NAME;
import static http.common.HttpMethod.POST;
import static http.handler.AbstractHandler.TEMPLATE_PATH;
import static http.handler.CreateUserHandler.INDEX_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class CreateUserHandlerTest {

    @Test
    void testGetHttpResponse() throws IOException, URISyntaxException {
        RequestLine requestLine = RequestLine.parse(POST.name() + " " + Dispatcher.CREATE_USER_URL + " HTTP/1.1");
        String body = "userId=ninjasul&password=1234&name=%EB%B0%95%EB%8F%99%EC%97%BD&email=ninjasul%40gmail.com";

        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpEntity(HttpHeaders.EMPTY, body));
        HttpResponse httpResponse = new HttpResponse(null);

        Handler handler = new CreateUserHandler();
        handler.handle(httpRequest, httpResponse);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getHttpHeaderMap()).contains(
                entry(LOCATION_HEADER_NAME, INDEX_PATH)
        );
        assertThat(httpResponse.getHttpBody()).isEqualTo(new String(FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + INDEX_PATH)));
        assertThat(DataBase.findUserById("ninjasul")).isEqualTo(User.of(httpRequest.getParameterMap()));

        DataBase.clearAllUsers();
    }
}