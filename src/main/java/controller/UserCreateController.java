package controller;

import db.DataBase;
import model.*;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserCreateController implements Controller{


    public static final String PATH = "/user/create";
    public static final String REDIRECT_PATH = "/index.html";
    private RequestMappingInfo mappingInfo = new RequestMappingInfo(HttpMethod.POST, PATH);

    @Override
    public boolean isPath(RequestMappingInfo info) {
        return mappingInfo.equals(info);
    }

    @Override
    public HttpResponse process(HttpRequest request) throws IOException, URISyntaxException {
        DataBase.addUser(User.createUser(request.getBody()));

        return HttpResponse.redirect(REDIRECT_PATH);
    }
}
