package http.controller.user;

import http.HttpStatus;
import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.user.User;
import utils.TemplateUtils;
import utils.UserData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListController extends AbstractController {
    private static final String REQUEST_MAPPING_VALUE = "/user/list.html";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws Exception {
        Map<String, Object> userMap = new HashMap<>();
        List<User> users = UserData.findAll();
        userMap.put("users", users);

        String listPage = TemplateUtils.getTemplate("user/list", userMap);
        response.body(HttpStatus.OK, listPage.getBytes(), "text/html");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        response.methodNotAllowed(request);
    }

    @Override
    public boolean useAuthentication() {
        return true;
    }

    @Override
    public String getRequestMappingValue() {
        return REQUEST_MAPPING_VALUE;
    }
}
