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
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestHeader;
import webserver.http.request.HttpRequestLine;
import webserver.http.response.HttpResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UserListController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UserListController.class);

    @Override
    public void handle(OutputStream out, HttpRequest request) {
        HttpRequestLine requestLine = request.getHttpRequestLine();
        HttpRequestHeader requestHeader = request.getHttpRequestHeader();

        boolean isLogined = HttpStringUtils.checkLoginCookie(requestHeader.findByKey("Cookie"));
        if (!isLogined) {
            HttpResponse response = new HttpResponse().ok("/user/login.html", "text/html");
            ResponseHandler.response(out, response);
            return;
        }

        try {
            String listPage = render(requestLine);
            HttpResponse response = new HttpResponse().okWithBody("text/html", listPage);
            ResponseHandler.response(out, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String render(HttpRequestLine requestLine) throws IOException {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix(".html");
        Handlebars handlebars = new Handlebars(loader);

        String requestUri = requestLine.getRequestUri();
        requestUri = requestUri.substring(0, requestUri.lastIndexOf(".html"));

        Template template = handlebars.compile(requestUri);
        List<User> users = DataBase.findAll();
        String listPage = template.apply(users);
        log.debug("list Page : {}", listPage);
        return listPage;
    }
}
