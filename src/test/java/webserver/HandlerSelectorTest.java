package webserver;

import handler.IndexHandler;
import handler.PathHandler;
import handler.UserHandler;
import model.request.HttpRequestHeader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dummy.HttpRequestHeaderDummy.GET_INDEX_HTTP_REQUEST_DUMMY;
import static dummy.HttpRequestHeaderDummy.GET_USER_HTTP_REQUEST_DUMMY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HandlerSelectorTest {
    @Test
    @DisplayName("HttpRequestHeader를 넘기면 path의 resource를 처리할수 있는 Handler 객체를 반환한다.")
    void selectAvailableHandlerTest() {
        HttpRequestHeader getIndexHttpRequest = GET_INDEX_HTTP_REQUEST_DUMMY;
        HttpRequestHeader getUserHttpRequest = GET_USER_HTTP_REQUEST_DUMMY;

        HandlerSelector handlerSelector = new HandlerSelector();

        PathHandler indexHandlerResult = handlerSelector.selectAvailableHandler(getIndexHttpRequest);
        PathHandler userHandlerResult = handlerSelector.selectAvailableHandler(getUserHttpRequest);

        assertAll(
            () -> assertThat(indexHandlerResult.getClass()).isEqualTo(IndexHandler.class),
            () -> assertThat(userHandlerResult.getClass()).isEqualTo(UserHandler.class)
        );
    }
}
