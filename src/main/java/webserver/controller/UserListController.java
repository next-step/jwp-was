package webserver.controller;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import db.DataBase;
import model.User;
import webserver.converter.HttpConverter;
import webserver.converter.HttpFileConverter;
import webserver.domain.HttpResponseEntity;
import webserver.http.AbstractControllerCreator;
import webserver.http.HttpRequest;
import webserver.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class UserListController extends AbstractControllerCreator {

    @Override
    public HttpResponseEntity doPost(HttpRequest httpRequest) {
        return HttpResponseEntity.setStatusResponse(httpRequest,
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    public HttpResponseEntity doGet(HttpRequest httpRequest) {
        try {
            HttpResponseEntity entity = new HttpResponseEntity(httpRequest.getHttpHeader(),
                    HttpStatus.REDIRECT.getHttpStatusCode(),
                    httpRequest.getVersion());

            entity.setUrlPath(httpRequest.getUrlPath() + HttpConverter.HTML_FILE_NAMING);

            String readFileContent = HttpFileConverter.readFile(httpRequest.getHttpEntity());

            Handlebars handlebars = new Handlebars();
            Template template = handlebars.
                    compileInline(readFileContent);
            List<User> userList = DataBase.findAll()
                    .stream().collect(Collectors.toList());
            String remakeContent = template.apply(userList);

            entity.setResultBody(remakeContent);

            return entity;
        }catch (Exception e){
            return HttpResponseEntity.setStatusResponse(httpRequest,
                    HttpStatus.NOT_FOUND);
        }
    }

}
