package webserver.http;

import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;
import webserver.domain.HttpResponseEntity;

import java.util.HashMap;

public class HttpController {

    private static HashMap<String, ControllerCreator> controllerCreatorHashMap = new HashMap<>();

    public HttpController() {
        controllerCreatorHashMap.put("/user/create", new UserCreateController());
        controllerCreatorHashMap.put("/user/login", new UserLoginController());
        controllerCreatorHashMap.put("/user/list", new UserListController());
    }

    public boolean isContainUrl(String url){
        return controllerCreatorHashMap.containsKey(url);
    }

    public HttpResponseEntity callMethod(HttpRequest httpRequest) {
        String callUrlPath = httpRequest.getUrlPath();
        if(controllerCreatorHashMap.containsKey(callUrlPath)){
            return controllerCreatorHashMap.get(callUrlPath).doMethodCall(httpRequest);
        }

        return HttpResponseEntity.setStatusResponse(httpRequest,
                HttpStatus.NOT_FOUND);
    }
}
