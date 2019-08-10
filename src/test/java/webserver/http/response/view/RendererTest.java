package webserver.http.response.view;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.WebConfig;
import webserver.http.response.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
@DisplayName("renderer 테스트")
public class RendererTest {

    private static final Logger logger = LoggerFactory.getLogger(RendererTest.class);

    @DisplayName("리다이렉트")
    @Test
    void redirectTest() {
        rendererTest(ViewType.REDIRECT, "/test");
    }

    @DisplayName("정적 자원")
    @Test
    void staticResourceTest() {
        rendererTest(ViewType.RESOURCE, "/static/css/styles.css");
    }

    @DisplayName("템플릿 엔진 테스트")
    @Test
    void templateTest() {
        rendererTest(ViewType.TEMPLATE, "/user/login");
    }

    private void rendererTest(ViewType view, String path) {
        ByteArrayBuffer buffer = new ByteArrayBuffer();
        HttpResponse response = new HttpResponse(buffer);
        ModelAndView mv = new ModelAndView(view.getPrefix() + path);
        ViewRenderer viewRenderer = WebConfig.getViewRenderer(mv.getViewName());

        viewRenderer.render(mv, response);

        logger.debug("{}", new String(buffer.getRawData()));
        assertTrue(buffer.size() > 0);
    }
}
