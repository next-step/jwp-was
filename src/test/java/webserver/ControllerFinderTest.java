package webserver;

import controller.UserController;
import model.User;
import model.controller.View;
import model.http.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerFinderTest {
    @Test
    void findControllerMethodWithGet() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method givenMethod = UserController.class.getMethod("createUser", User.class);
        RequestLine requestLine = RequestLine.of(HttpMethod.GET, RequestUri.of("/user/create"), HttpVersion.HTTP1_1);
        Method methodFound = ControllerFinder.findController(requestLine).get();
        assertThat(methodFound).isEqualTo(givenMethod);
        assertThat(methodFound.invoke(methodFound.getDeclaringClass().newInstance(), new User())).isEqualTo(View.of("/user/profile"));
    }

    @Test
    void findControllerMethodWithPost() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method givenMethod = UserController.class.getMethod("createUserPost", User.class);
        RequestLine requestLine = RequestLine.of(HttpMethod.POST, RequestUri.of("/user/create"), HttpVersion.HTTP1_1);
        Method methodFound = ControllerFinder.findController(requestLine).get();
        assertThat(methodFound).isEqualTo(givenMethod);
        assertThat(methodFound.invoke(methodFound.getDeclaringClass().newInstance(), new User())).isEqualTo(View.of("/user/profile"));
    }
}
