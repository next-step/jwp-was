package controller.user;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import controller.AbstractController;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestHeader;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Stream;

import static utils.HandlebarsUtils.loader;

public class UserListController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    private static final String USER_LIST_PATH = "/user/list.html";
    public static final String ROOT_FILE = "/index.html";
    public static final String COOKIE = "Cookie";


    @Override
    public HttpResponse doGet(HttpRequest httpRequest) throws IOException {
        logger.debug("UserListController : {}", httpRequest.getRequestPath());

        if (!isLoginStatus(httpRequest.getHeaders())) {
            return HttpResponse.sendRedirect(ROOT_FILE);
        }
        Collection<User> users = DataBase.findAll();

        return HttpResponse.forward(USER_LIST_PATH, loader(users).getBytes(StandardCharsets.UTF_8));
    }

    private boolean isLoginStatus(RequestHeader requestHeader) {
        return Stream.of(requestHeader.getHeaders().get(COOKIE))
                .anyMatch(loginStatus -> StringUtils.equals(loginStatus, "logined=true"));
    }
}
