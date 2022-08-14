package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import http.httprequest.HttpRequest;
import http.httprequest.requestline.HttpMethod;
import http.httpresponse.HttpHeaders;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import http.httpresponse.ResponseHeader;
import utils.AuthUtil;

import java.io.IOException;
import java.util.Collections;

public class UserListController implements Controller {

    private static final String PATH = "/user/list";
    private final Handlebars handlebars;

    public UserListController() {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");

        this.handlebars = new Handlebars(loader);
    }

    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public boolean isMatch(HttpMethod httpMethod, String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("[Controller] path must not be empty");
        }

        if (httpMethod == null) {
            throw new IllegalArgumentException("[Controller] path must not be empty");
        }

        return httpMethod.equals(HttpMethod.GET) && path.equals(PATH);
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) throws IOException {
        if (!AuthUtil.isLoggedIn(httpRequest)) {
            return new HttpResponse(
                    HttpStatusCode.UNAUTHORIZED,
                    new ResponseHeader(Collections.singletonMap(HttpHeaders.LOCATION, "/user/login.html"))
            );
        }

        return new HttpResponse(
                HttpStatusCode.OK,
                new ResponseHeader(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                userListBody()
        );
    }

    private String userListBody() throws IOException {
        return handlebars.compile("user/list")
                .apply(Collections.singletonMap("users", DataBase.findAll()));
    }
}
