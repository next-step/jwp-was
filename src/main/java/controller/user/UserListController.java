package controller.user;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private static final String USER_LIST_PATH = "/user/list.html";
    public static final String ROOT_PATH = "/";

    @Override
    public void doPost(Request request, Response response) {

    }

    @Override
    public void doGet(Request request, Response response) throws IOException {
        if (!isLoginStatus(request.getHeader())) {
            response.sendRedirect(ROOT_PATH);
            return;
        }
        Collection<User> users = DataBase.findAll();
        String loadData = loader(users);

        response.forward(USER_LIST_PATH, loadData.getBytes(StandardCharsets.UTF_8));
        response.responseOk();
    }

    private boolean isLoginStatus(RequestHeader requestHeader) {
        logger.debug("Cookie : {}", requestHeader.getHeaders().get(COOKIE));
        return Stream.of(requestHeader.getHeaders().get(COOKIE))
                .anyMatch(loginStatus -> StringUtils.equals(loginStatus, "logined=true"));
    }
}
