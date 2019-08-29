package webserver;

import controller.UserController;
import model.User;
import model.controller.ResponseEntity;
import model.http.HttpMethod;
import model.http.HttpVersion;
import model.http.RequestLine;
import model.http.RequestUri;
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
        assertThat(methodFound.invoke(methodFound.getDeclaringClass().newInstance(), new User())).isEqualTo(ResponseEntity.of("/user/profile"));
    }

    @Test
    void findControllerMethodWithPost() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method givenMethod = UserController.class.getMethod("createUserPost", User.class);
        RequestLine requestLine = RequestLine.of(HttpMethod.POST, RequestUri.of("/user/create"), HttpVersion.HTTP1_1);
        Method methodFound = ControllerFinder.findController(requestLine).get();
        assertThat(methodFound).isEqualTo(givenMethod);
        assertThat(methodFound.invoke(methodFound.getDeclaringClass().newInstance(), new User())).isEqualTo(ResponseEntity.of("/user/profile"));
    }
}
