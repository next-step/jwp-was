package webserver.http.request;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.WebConfig;
import webserver.http.request.controller.Controller;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class ControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerTest.class);
    private static String loginController;
    private static String joinController;
    private static String userListController;
    private static String errorController;

    @BeforeAll
    public static void init() {
        loginController = new TestRequest.Builder("POST", "/user/login")
                .addHeader("Host", "localhost:8080")
                .addHeader("Content-Length", "23")
                .addBody("userId", "koh")
                .addBody("password", "123")
                .buildString();

        joinController = new TestRequest.Builder("POST", "/user/create")
                .addHeader("Host", "localhost:8080")
                .addBody("userId", "koh")
                .addBody("password", "123")
                .buildString();

        userListController = new TestRequest.Builder("GET", "/user/list")
                .addHeader("Host", "localhost:8080")
                .addBody("userId", "koh")
                .addBody("password", "123")
                .buildString();

        errorController = new TestRequest.Builder("DELETE", "/error")
                .addHeader("Host", "localhost:8080")
                .buildString();
    }

    @DisplayName("로그인 컨트롤러 테스트")
    @Test
    void loginControllerTest() throws IOException {
        HttpRequest request = new HttpRequest(new ByteArrayInputStream(loginController.getBytes()));
        HttpResponse response = new HttpResponse(new ByteArrayBuffer());

        Controller controller = WebConfig.getController(request.getPath());
        ModelAndView mv = controller.process(request, response);

        logger.debug("{}", mv.getViewName());
        assertNotNull(mv);
    }

    @DisplayName("회원가입 컨트롤러 테스트")
    @Test
    void joinControllerTest() throws IOException {
        HttpRequest request = new HttpRequest(new ByteArrayInputStream(joinController.getBytes()));
        HttpResponse response = new HttpResponse(new ByteArrayBuffer());

        Controller controller = WebConfig.getController(request.getPath());
        ModelAndView mv = controller.process(request, response);

        logger.debug("{}", mv.getViewName());
        assertNotNull(mv);
    }

    @DisplayName("유저리스트 컨트롤러 테스트")
    @Test
    void userListControllerTest() throws IOException {
        HttpRequest request = new HttpRequest(new ByteArrayInputStream(userListController.getBytes()));
        HttpResponse response = new HttpResponse(new ByteArrayBuffer());

        Controller controller = WebConfig.getController(request.getPath());
        ModelAndView mv = controller.process(request, response);

        logger.debug("{}", mv.getViewName());
        assertNotNull(mv);
    }

    @DisplayName("error 컨트롤러 테스트")
    @Test
    void errorControllerTest() throws IOException {
        HttpRequest request = new HttpRequest(new ByteArrayInputStream(errorController.getBytes()));
        HttpResponse response = new HttpResponse(new ByteArrayBuffer());

        Controller controller = WebConfig.getController(request.getPath());
        ModelAndView mv = controller.process(request, response);

        logger.debug("{}", mv.getViewName());
        assertNotNull(mv);
    }
}
