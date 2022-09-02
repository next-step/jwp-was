package webserver;

import controller.*;
import model.http.HttpMethod;
import model.request.RequestMappingInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestMapperTest {

    private final RequestMapper requestMapper = new RequestMapper();

    @Test
    void 회원가입_컨트롤러_가져오기() {
        final String path = "/user/create";
        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.POST, path, new UserCreateController());

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.getClass()).isEqualTo(UserCreateController.class);
    }

    @Test
    void 뷰컨트롤러_가져오기() {
        final String path = "/index.html";
        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.GET, path, new ViewController());

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.getClass()).isEqualTo(ViewController.class);
    }

    @Test
    void 로그인_컨트롤러_가져오기() {
        final String path = "/user/login";
        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.POST, path, new LoginController());

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.getClass()).isEqualTo(LoginController.class);
    }
    @Test
    void 유저목록_컨트롤러_가져오기() {
        final String path = "/user/list";
        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.GET, path, new UserListController());

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.getClass()).isEqualTo(UserListController.class);
    }
}