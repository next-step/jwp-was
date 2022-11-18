package webserver.controller;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.httprequest.HttpRequest;
import http.httprequest.requestline.HttpMethod;
import http.httpresponse.HttpResponse;
import utils.AuthUtil;

public class UserLoginController implements Controller {

    private static final String PATH = "/user/login";


    @Override
    public String getPath() {
        return PATH;
    }

    @Override
    public boolean isMatch(HttpMethod httpMethod,
                           String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("[Controller] path must not be empty");
        }

        if (httpMethod == null) {
            throw new IllegalArgumentException("[Controller] path must not be empty");
        }

        return httpMethod.equals(HttpMethod.POST) && path.equals(PATH);
    }

    @Override
    public HttpResponse execute(HttpRequest httpRequest) {
        return AuthUtil.login(httpRequest);
    }
}
