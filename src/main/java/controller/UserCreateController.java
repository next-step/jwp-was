package controller;

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
        User.createUser(request.getBody());
        final byte[] responseBody = FileIoUtils.loadFileFromClasspath(REDIRECT_PATH);

        return HttpResponse.redirect(responseBody, REDIRECT_PATH);
    }
}
