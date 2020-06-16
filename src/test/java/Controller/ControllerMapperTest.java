package Controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerMapperTest {
    private ControllerMapper sut;
    @DisplayName("리퀘스트가 왔을 시 적합한 컨트롤러를 반환한다.")
    @Test
    void getController() {
        // given
        String path = "/user/create";

        // when
        Controller controller = sut.getController(path);

        // then
        assertThat(controller).extracting("class").isEqualTo(CreateUserController.class);
    }
}