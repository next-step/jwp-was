package webserver.http.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webserver.http.request.header.RequestHeader;
import webserver.http.service.ResponsePostHandler;
import webserver.http.service.exception.InvalidRequestException;

class ResponsePostRequestHandlerTest {

    @DisplayName("관리되지 않는 요청은 실패되어야 한다.")
    @Test
    void handleFailedByInvalidRequest() {
        ResponsePostHandler responsePostHandler = new ResponsePostHandler();
        assertThatThrownBy(() -> responsePostHandler.handle(
                RequestHeader.create(
                        "GET /invalid/request?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1"),
                ""
        )).isInstanceOf(InvalidRequestException.class)
                .hasMessage("제공되지 않은 요청입니다.");
    }

}