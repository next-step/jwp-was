package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.converter.HttpFileConverter;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class UserListController extends AbstractControllerCreator {

    @Override
    public HttpResponse doPost(HttpRequest httpRequest) {
        return HttpResponse.setStatusResponse(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public HttpResponse doGet(HttpRequest httpRequest) {
        try {
            HttpResponse response = HttpResponse.ok(httpRequest);

            response.setUrlPath(httpRequest.getUrlPath());
            String readFileContent = HttpFileConverter.readFile(httpRequest.getUrlPath() + HttpConverter.HTML_FILE_NAMING);

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.
                    compileInline(readFileContent);
            List<User> userList = DataBase.findAll()
                    .stream().collect(Collectors.toList());
            String remakeContent = template.apply(userList);

            response.setResultBody(remakeContent);

            return response;
        }catch (Exception e){
            return HttpResponse.setStatusResponse(HttpStatus.NOT_FOUND);
        }
    }

}
