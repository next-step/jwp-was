package webserver;

import controller.UserController;
import model.http.UriPath;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerFinderTest {
    @Test
    void test() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method givenMethod = UserController.class.getMethod("createUser");
        Method methodFound = ControllerFinder.findController(UriPath.of("/user/create")).get();
        assertThat(methodFound).isEqualTo(givenMethod);
        assertThat(methodFound.invoke(methodFound.getDeclaringClass().newInstance()).toString()).isEqualTo("/user/profile");
    }
}
