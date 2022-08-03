package webserver;

import controller.Controller;
import controller.UserCreateController;
import controller.ViewController;
import model.HttpMethod;
import model.RequestMappingInfo;

import java.util.Arrays;
import java.util.List;

public class RequestMapper {

    private List<Controller> controllers = Arrays.asList(new UserCreateController(), new ViewController());

    public Controller mapping(RequestMappingInfo info) {

        return controllers.stream()
                .filter(controller -> controller.isPath(info))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경로입니다."));
    }
}
