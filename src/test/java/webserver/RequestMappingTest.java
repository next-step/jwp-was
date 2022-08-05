package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.controller.UserCreateController;
import webserver.controller.Controller;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RequestMapping 단위 테스트")
class RequestMappingTest {
    @DisplayName("경로를 처리할 Controller를 반환한다.")
    @Test
    void getController() {
        // when
        final Controller controller = RequestMapping.getController("/user/create");

        // then
        assertThat(controller.getClass()).isEqualTo(UserCreateController.class);
    }
}
