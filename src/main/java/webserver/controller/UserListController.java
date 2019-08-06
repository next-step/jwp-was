package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.converter.HttpFileConverter;
import webserver.converter.HttpFileResource;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class UserListController extends AbstractControllerCreator {

    @Override
    public void doPost(HttpRequest httpRequest) {

    }

    @Override
    public void doGet(HttpRequest httpRequest) {
        try {
            httpRequest.setUrlPath(httpRequest.getUrlPath() + HttpConverter.HTML_FILE_NAMING);
            HttpFileConverter.readFile(httpRequest.getHttpEntity());

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.
                    compileInline(httpRequest.getReturnContent());
            List<User> userList = DataBase.findAll()
                    .stream().collect(Collectors.toList());
            String remakeContent = template.apply(userList);
            httpRequest.setReturnContent(remakeContent);
        }catch (Exception e){
            HttpResponse.setPageNotFond(httpRequest);
        }
    }

}
