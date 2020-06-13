package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import user.ui.CreateUserController;
import user.ui.ListUserController;
import user.ui.UserController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class MappingControllersTest {

    @Test
    @DisplayName("컨트롤러 가져오기")
    void getControllerByMappingControllers() {
        UserController userController = MappingControllers.getController("/user/list");

        boolean hasController = userController instanceof ListUserController;
        boolean hasControllerOther = userController instanceof CreateUserController;
        assertAll("Has Controller Test",
                () -> assertThat(hasController).isTrue(),
                () -> assertThat(hasControllerOther).isFalse()
        );
    }

    @Test
    @DisplayName("맵핑되는 컨트롤러가 없을 때")
    void withoutMappingController() {
        UserController userController = MappingControllers.getController("/test");
        assertThat(userController).isNotNull();
    }
}
