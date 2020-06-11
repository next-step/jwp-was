package http.handler;

import com.google.common.collect.Maps;
import http.common.HttpEntity;
import http.common.HttpHeaders;
import http.handler.mapper.HandlerMapper;
import http.request.HttpRequest;
import http.request.requestline.RequestLine;
import http.response.HttpResponse;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import static http.common.HttpHeader.LOCATION_HEADER_NAME;
import static http.common.HttpMethod.GET;
import static http.handler.ListUserHandler.*;
import static http.handler.LoginHandler.LOGIN_FAIL_COOKIE_VALUE;
import static http.handler.LoginHandler.LOGIN_SUCCESS_COOKIE_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class ListUserHandlerTest {

    @Test
    void testGetHttpResponseForLoginedUser() throws IOException, URISyntaxException {
        RequestLine requestLine = RequestLine.parse(GET.name() + " " + HandlerMapper.LIST_USER.getUrl() + " HTTP/1.1");

        HttpHeaders httpHeaders = getLoginedHttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpEntity(httpHeaders, Strings.EMPTY));

        ListUserHandler handler = new ListUserHandler();
        HttpResponse httpResponse = handler.getHttpResponse(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getHttpHeaderMap()).isEmpty();

        assertThat(handler.compileTemplate(USER_LIST_LOCATION, Collections.emptyMap()).getBytes())
                .isEqualTo(handler.getHttpResponseBody(httpRequest));
    }

    private HttpHeaders getLoginedHttpHeaders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put(COOKIE_HEADER_NAME, LOGIN_SUCCESS_COOKIE_VALUE);
        return new HttpHeaders(headers);
    }

    @Test
    void testGetHttpResponseForNotLoginedUser() throws IOException, URISyntaxException {
        RequestLine requestLine = RequestLine.parse(GET.name() + " " + HandlerMapper.LIST_USER.getUrl() + " HTTP/1.1");

        HttpHeaders httpHeaders = getNotLoginedHttpHeaders();
        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpEntity(httpHeaders, Strings.EMPTY));

        ListUserHandler handler = new ListUserHandler();
        HttpResponse httpResponse = handler.getHttpResponse(httpRequest);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getHttpHeaderMap()).contains(
                entry(LOCATION_HEADER_NAME, LOGIN_FAILED_PATH)
        );

        assertThat(handler.compileTemplate(NOT_LOGIN_LOCATION, Collections.emptyMap()).getBytes())
                .isEqualTo(handler.getHttpResponseBody(httpRequest));
    }

    private HttpHeaders getNotLoginedHttpHeaders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put(COOKIE_HEADER_NAME, LOGIN_FAIL_COOKIE_VALUE);
        return new HttpHeaders(headers);
    }

}