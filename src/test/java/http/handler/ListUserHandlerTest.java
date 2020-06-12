package http.handler;

import com.google.common.collect.Maps;
import http.common.*;
import http.handler.mapper.Dispatcher;
import http.request.HttpRequest;
import http.request.requestline.RequestLine;
import http.response.HttpResponse;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import static http.common.Cookies.COOKIE_KEY_VALUE_SEPARATOR;
import static http.common.Cookies.SESSION_ID_COOKIE_NAME;
import static http.common.HttpHeader.LOCATION_HEADER_NAME;
import static http.common.HttpHeaders.*;
import static http.common.HttpMethod.GET;
import static http.handler.ListUserHandler.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class ListUserHandlerTest {
    private static final Logger log = LoggerFactory.getLogger(ListUserHandlerTest.class);

    @Test
    void testGetHttpResponseForLoginedUser() throws IOException, URISyntaxException {
        RequestLine requestLine = RequestLine.parse(GET.name() + " " + Dispatcher.LIST_USER_URL + " HTTP/1.1");

        HttpSession httpSession = HttpSessionUtil.getSession();
        HttpHeaders httpHeaders = getLoginedHttpHeaders(httpSession.getId());
        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpEntity(httpHeaders, Strings.EMPTY));
        HttpResponse httpResponse = new HttpResponse(null);

        ListUserHandler handler = new ListUserHandler();
        handler.handle(httpRequest, httpResponse);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getHttpHeaderMap()).isEmpty();

        assertThat(handler.compileTemplate(USER_LIST_LOCATION, Collections.emptyMap()).getBytes())
                .isEqualTo(handler.getHttpResponseBody(httpRequest));
    }

    private HttpHeaders getLoginedHttpHeaders(String sessionId) {
        log.debug("sessionId: {}", sessionId);

        Map<String, String> headers = Maps.newHashMap();
        headers.put(COOKIE_HEADER_NAME, SESSION_ID_COOKIE_NAME + COOKIE_KEY_VALUE_SEPARATOR + sessionId);
        return new HttpHeaders(headers);
    }

    @Test
    void testGetHttpResponseForNotLoginedUser() throws IOException, URISyntaxException {
        RequestLine requestLine = RequestLine.parse(GET.name() + " " + Dispatcher.LIST_USER_URL + " HTTP/1.1");

        HttpHeaders httpHeaders = new HttpHeaders(Collections.emptyMap());
        HttpRequest httpRequest = new HttpRequest(requestLine, new HttpEntity(httpHeaders, Strings.EMPTY));
        HttpResponse httpResponse = new HttpResponse(null);

        ListUserHandler handler = new ListUserHandler();
        handler.handle(httpRequest, httpResponse);

        assertThat(httpResponse).isNotNull();
        assertThat(httpResponse.getHttpHeaderMap()).contains(
                entry(LOCATION_HEADER_NAME, LOGIN_FAILED_PATH)
        );

        assertThat(handler.compileTemplate(NOT_LOGIN_LOCATION, Collections.emptyMap()).getBytes())
                .isEqualTo(handler.getHttpResponseBody(httpRequest));
    }
}