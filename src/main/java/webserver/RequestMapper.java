package webserver;

import controller.Controller;
import controller.UserCreateController;
import controller.UserListController;
import controller.ViewController;
import model.HttpMethod;
import model.RequestMappingInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RequestMapper {

    private Map<RequestMappingInfo, Controller> controllers = Map.of(
            new RequestMappingInfo(HttpMethod.POST, "/user/create"), new UserCreateController(),
            new RequestMappingInfo(HttpMethod.GET, "/.*"), new ViewController(),
            new RequestMappingInfo(HttpMethod.POST, "/user/login"), new LoginController(),
            new RequestMappingInfo(HttpMethod.GET, "/user/list"), new UserListController());

    public Controller mapping(RequestMappingInfo info) {

        return controllers.keySet().stream()
                .filter(key -> key.equals(info))
                .findFirst()
                .map(key -> controllers.get(key))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경로입니다."));
    }
}
