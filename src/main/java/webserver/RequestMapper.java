package webserver;

import controller.*;
import model.http.HttpMethod;
import model.request.RequestMappingInfo;

import java.util.List;

public class RequestMapper {

    private List<RequestMappingInfo> controllers = List.of(
            new RequestMappingInfo(HttpMethod.POST, "/user/create", new UserCreateController()),
            new RequestMappingInfo(HttpMethod.POST, "/user/login", new LoginController()),
            new RequestMappingInfo(HttpMethod.GET, "/user/list", new LogoutController()),
            new RequestMappingInfo(HttpMethod.GET, "/user/logout", new UserListController()),
            new RequestMappingInfo(HttpMethod.GET, "/.*", new ViewController())
            );

    public Controller mapping(RequestMappingInfo info) {
        return controllers.stream()
                .filter(mappingInfo -> mappingInfo.match(info))
                .findFirst()
                .map(mappingInfo -> mappingInfo.getController())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 경로입니다."));
    }
}
