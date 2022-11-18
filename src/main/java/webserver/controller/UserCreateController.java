package webserver.controller;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import db.DataBase;
import http.httprequest.HttpRequest;
import http.httprequest.requestline.HttpMethod;
import http.httpresponse.HttpResponse;
import model.User;

public class UserCreateController implements Controller {
    private static final String PATH = "/user/create";

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public boolean isMatch(HttpMethod httpMethod,
                           String path) {

        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("[Controller] path must not be empty");
        }

        if (httpMethod == null) {
            throw new IllegalArgumentException("[Controller] path must not be empty");
        }

        return httpMethod.equals(HttpMethod.POST) && path.equals(PATH);
    }

    @Override
    public HttpResponse execute(HttpRequest httpRequest) {
        DataBase.addUser(new User(
                httpRequest.getBodyValue("userId"),
                httpRequest.getBodyValue("password"),
                httpRequest.getBodyValue("name"),
                httpRequest.getBodyValue("email")
        ));
        return HttpResponse.sendRedirect("/index.html");
    }
}
