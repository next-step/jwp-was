package webserver.http;

import webserver.controller.UserCreateController;
import webserver.controller.UserListController;
import webserver.controller.UserLoginController;

import java.util.HashMap;

public class HttpController {

    private static HashMap<String, ControllerStructor> controllerStructorHashMap = new HashMap<>();

    static{
        controllerStructorHashMap.put("/user/create", new UserCreateController());
        controllerStructorHashMap.put("/user/login", new UserLoginController());
        controllerStructorHashMap.put("/user/list", new UserListController());
    }

    public static boolean isContainUrl(String url){
        return controllerStructorHashMap.containsKey(url);
    }

    public static HttpResponse callMethod(HttpRequest httpRequest) {
        String callUrlPath = httpRequest.getUrlPath();
        if(controllerStructorHashMap.containsKey(callUrlPath)){
            return controllerStructorHashMap.get(callUrlPath).doMethodCall(httpRequest);
        }

        return HttpResponse.pageNotFound(httpRequest);
    }
}
