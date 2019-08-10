package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.controller.Controller;
import webserver.http.common.exception.UrlNotFoundException;
import webserver.http.request.handler.RequestHandler;
import webserver.http.response.view.ViewRenderer;
import webserver.http.response.view.ViewType;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("웹 설정 테스트")
public class WebConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(WebConfigTest.class);

    @DisplayName("핸들러 테스트")
    @Test
    void handlerTest() {
        RequestHandler handler = WebConfig.getHandler("/ubtets.tets");
        assertNotNull(handler);
    }

    @DisplayName("컨트롤러 테스트")
    @Test
    void controllerTest() {
        Controller controller = WebConfig.getController("/user/list");
        assertNotNull(controller);
    }

    @DisplayName("컨트롤러 예외 테스트")
    @Test
    void controllerExceptionTest() {
        assertThrows(NullPointerException.class,
                () -> WebConfig.getController("/user/list").process(null, null));
    }

    @DisplayName("렌더러 테스트")
    @Test
    void rendererTest() {
        ViewRenderer viewRenderer = WebConfig.getViewRenderer(ViewType.REDIRECT.getPrefix() + "/test");
        assertNotNull(viewRenderer);
    }

    @DisplayName("렌더러 예외 테스트")
    @Test
    void rendererExceptionTest() {
        assertThrows(UrlNotFoundException.class, () -> WebConfig.getViewRenderer("/exception"));
    }
}

