package service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpResponseFactory;
import webserver.session.FailedSessionLoginException;
import webserver.session.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends GetController {


    private static final UserLoginService userLoginService = new UserLoginService();

    @Override
    HttpResponse doGet(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie(HttpSession.COOKIE_KEY);
        try {
            userLoginService.login(sessionId);
        } catch (FailedSessionLoginException e) {
            return HttpResponseFactory.response302("/user/login");
        }

        return compile(httpRequest.getPathStr());
    }

    private HttpResponse compile(String filePath) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(filePath);
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("users", DataBase.findAll());
            String body = template.apply(parameterMap);
            return new HttpResponse(body.getBytes(), "202");
        } catch (IOException e) {
            throw new ResourceException(filePath);
        }
    }
}
