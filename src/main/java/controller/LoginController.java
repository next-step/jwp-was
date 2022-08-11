package controller;

import controller.Controller;
import db.DataBase;
import model.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginController implements Controller {

    public static final String PATH = "/user/login";
    public static final String LOGIN_FAILED_PATH = "/user/login_failed.html";
    public static final String USER_ID = "userId";
    public static final String LOGIN_SUCCESS_PATH = "/index.html";
    private RequestMappingInfo mappingInfo = new RequestMappingInfo(HttpMethod.POST, PATH);

    @Override
    public HttpResponse process(HttpRequest request) throws IOException, URISyntaxException {

        final String userId = request.getBody().getOneValue(USER_ID);
        final User findUser = DataBase.findUserById(userId);

        if (findUser == null) {
            return HttpResponse.loginRedirect(LOGIN_FAILED_PATH, false);
        }
        return HttpResponse.loginRedirect(LOGIN_SUCCESS_PATH, true);
    }
}
