package service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListController extends GetController {

    @Override
    HttpResponse doGet(HttpRequest httpRequest) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(httpRequest.getPathStr());
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("users", DataBase.findAll());
            String body = template.apply(parameterMap);
            return new HttpResponse(body.getBytes(), "202");
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO custom exception
        }
    }
}
