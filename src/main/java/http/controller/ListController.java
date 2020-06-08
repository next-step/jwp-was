package http.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;

import db.DataBase;
import http.HttpHeaderInfo;
import http.HttpRequest;
import http.HttpResponse;
import http.enums.HttpResponseCode;
import model.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

public class ListController extends PathController{
    private static final Logger log = LoggerFactory.getLogger(ListController.class);

    public ListController(HttpRequest httpRequest) {
        super(httpRequest);
    }

    public byte[] get()  {
        log.info("list controller get method execute ========");
        HttpHeaderInfo headerInfo = new HttpHeaderInfo();

        try {
            headerInfo.addKeyAndValue("Content-Type","text/html;charset=utf-8");

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("user/profile");

            UserList userList = new UserList(new ArrayList<>(DataBase.findAll()));
            String profilePage = template.apply(userList);

            HttpResponse response = new HttpResponse(HttpResponseCode.OK, profilePage.getBytes(), headerInfo);

            return response.makeResponseBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
