package http.controller.user;

import exception.JwpException;
import http.HttpSession;
import http.HttpStatus;
import http.controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import utils.TemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserProfileController extends AbstractController {
    private static final String REQUEST_MAPPING_VALUE = "/user/profile.html";

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        Map<String, Object> userMap = new HashMap<>();
        HttpSession session = request.getSession();
        userMap.put("userId", session.getAttribute("userId"));
        userMap.put("email", session.getAttribute("email"));

        try {
            String profilePage = TemplateUtils.getTemplate("user/profile", userMap);
            response.body(HttpStatus.OK, profilePage.getBytes(), "text/html");
        } catch (IOException e) {
            throw new JwpException("get template fail", e);
        }
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
