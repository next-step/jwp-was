package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpStringUtils;
import webserver.ResponseHandler;
import webserver.http.mapping.RequestMapping;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestHeader;
import webserver.http.request.HttpRequestLine;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpResponseResolver;
import webserver.http.view.ViewHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UserListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpRequestLine requestLine = request.getHttpRequestLine();
        HttpRequestHeader requestHeader = request.getHttpRequestHeader();

        boolean isAuthorized = HttpStringUtils.checkLoginCookie(requestHeader.findByKey("Cookie"));
        if (!isAuthorized) {
            return HttpResponseResolver.forward("text/html", "/user/login.html");
        }

        try {
            List<User> users = DataBase.findAll();
            String listPage = ViewHandler.render(requestLine, users);
            return HttpResponseResolver.forward("text/html", listPage.getBytes());
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }
}