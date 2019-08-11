package webserver;

import controller.UserController;
import model.User;
import model.http.Query;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerMethodInvokerTest {
    @Test
    void test() throws Exception {
        Class<UserController> clazz = UserController.class;
        Method method = clazz.getDeclaredMethod("createUser", User.class);
        assertThat(ControllerMethodInvoker.invoke(method, Query.of("userid=ssosso&password=ssosso_password&name=JangSoHyun&email=ssossohow@gmail.com"))).isEqualTo("/user/profile");
    }
}
