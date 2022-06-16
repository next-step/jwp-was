package service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.request.Headers;
import webserver.request.RequestLine;
import webserver.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserListService extends GetService {

    @Override
    Response doGet(RequestLine requestLine) {
        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);

        try {
            Template template = handlebars.compile(requestLine.getPathStr());
            Map<String, Object> parameterMap = new HashMap<>();
            parameterMap.put("users", DataBase.findAll());
            String body = template.apply(parameterMap);
            return new Response(body.getBytes(), "202", Headers.empty());
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO custom exception
        }
    }

    @Override
    public boolean canServe(RequestLine requestLine) {
        return requestLine.matchPath("/user/list.html");
    }
}
