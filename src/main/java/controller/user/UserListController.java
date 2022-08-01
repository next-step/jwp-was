package controller.user;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import controller.AbstractController;
import db.DataBase;
import model.User;
import webserver.http.request.Request;
import webserver.http.request.RequestHeader;
import webserver.http.response.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Stream;

import static model.Constant.COOKIE;
import static utils.HandlebarsUtils.loader;

public class UserListController extends AbstractController {

    private static final String USER_LIST_PATH = "/user/list.html";

    public final static String EXTENSION_SPERATOR = ".";


    @Override
    public void doPost(Request request, Response response) {

    }

    @Override
    public void doGet(Request request, Response response) throws IOException {
        if (isLoginStatus(request.getHeader())) {
            response.sendRedirect(USER_LIST_PATH);
            return;
        }
        Collection<User> users = DataBase.findAll();
        String loadData = loader(users, USER_LIST_PATH.substring(0, USER_LIST_PATH.indexOf(EXTENSION_SPERATOR)));

        response.setBody(loadData.getBytes(StandardCharsets.UTF_8));
        response.responseOk();
    }

    private boolean isLoginStatus(RequestHeader requestHeader) {
        return Stream.of(requestHeader.getHeaders().get(COOKIE))
                .anyMatch(loginStatus -> StringUtils.equals(loginStatus, "logined=true"));
    }
}
