package service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.User;
import webserver.request.Request;
import webserver.response.HttpStatus;
import webserver.response.Response;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeader;

public class UserListService {
    private UserListService() {}

    public static Response getUserList(Request request) {
        boolean loggedIn = request.getCookie().contains("loggedIn=true");
        if (!loggedIn) {
            ResponseHeader responseHeader = new ResponseHeader(HttpStatus.FOUND)
                    .setLocation("/user/login.html");
            return new Response(responseHeader);
        }
        ResponseHeader responseHeader = new ResponseHeader(HttpStatus.OK)
                .setContentType("text/html;charset=utf-8");
        String userList = renderUserList(DataBase.findAll());
        ResponseBody responseBody = ResponseBody.from(userList);
        return new Response(responseHeader, responseBody);
    }

    private static String renderUserList(Collection<User> users) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("users", users);

        TemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/templates");
        loader.setSuffix("");
        Handlebars handlebars = new Handlebars(loader);
        try {
            Template template = handlebars.compile("/user/list.html");
            return template.apply(parameterMap);
        } catch (IOException exception) {
            return "";
        }
    }
}
