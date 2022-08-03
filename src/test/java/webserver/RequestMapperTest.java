package webserver;

import controller.Controller;
import controller.LoginControllerTest;
import controller.UserCreateController;
import controller.ViewController;
import model.HttpMethod;
import model.RequestMappingInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {

    private final RequestMapper requestMapper = new RequestMapper();

    @Test
    void 회원가입_컨트롤러_가져오기() {
        final String path = "/user/create";
        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.POST, path);

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.getClass()).isEqualTo(UserCreateController.class);
    }

    @Test
    void 뷰컨트롤러_가져오기() {
        final String path = "/index.html";
        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.GET, path);

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.getClass()).isEqualTo(ViewController.class);
    }

    @Test
    void 로그인_컨트롤러_가져오기() {
        final String path = "/user/login";
        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.POST, path);

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.getClass()).isEqualTo(LoginController.class);
    }
}