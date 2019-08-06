package webserver.http;

import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;

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

    public void callMethod(HttpRequest httpRequest) {
        String callUrlPath = httpRequest.getUrlPath();
        if(controllerCreatorHashMap.containsKey(callUrlPath)){
            controllerCreatorHashMap.get(callUrlPath).doMethodCall(httpRequest);
        }
    }
}
