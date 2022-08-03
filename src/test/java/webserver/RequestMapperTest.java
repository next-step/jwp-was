package webserver;

import controller.Controller;
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

        assertThat(controller.isPath(requestMappingInfo));
    }

    @Test
    void 뷰컨트롤러_가져오기() {
        final String path = "/index.html";

        final RequestMappingInfo requestMappingInfo = new RequestMappingInfo(HttpMethod.GET, path);

        Controller controller = requestMapper.mapping(requestMappingInfo);

        assertThat(controller.isPath(requestMappingInfo));
    }
}