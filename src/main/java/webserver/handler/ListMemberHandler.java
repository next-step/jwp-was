package webserver.handler;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import webserver.Handler;
import webserver.http.Request;
import webserver.http.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

public class ListMemberHandler implements Handler {
    @Override
    public boolean isSupport(Request request) {
        return request.getPath().equals("/user/list") && request.getMethod().isGet();
    }

    @Override
    public Response handle(Request request) {
        String logined = request.getCookie("logined");

        if (logined.equals("true")) {
            return createTemplateResponse();
        }

        Response response = new Response();
        response.sendRedirect("/user/login.html");
        return response;
    }

    private Response createTemplateResponse() {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/list");

            byte[] templateBody = template.apply(Collections.singletonMap("users", DataBase.findAll())).getBytes(StandardCharsets.UTF_8);
            return new Response(templateBody);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
