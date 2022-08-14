package webserver.controller;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.httprequest.HttpRequest;
import http.httprequest.requestline.HttpMethod;
import http.httpresponse.HttpHeaders;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import http.httpresponse.ResponseHeader;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class TemplateController implements Controller {

    private static final String PATH = "./templates";

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

        return httpMethod.equals(HttpMethod.GET)
                && this.getClass().getClassLoader().getResource(getTemplatePath(path)) != null;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return new HttpResponse(
                HttpStatusCode.OK,
                new ResponseHeader(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, "text/html;charset=utf-8")),
                FileIoUtils.loadFileFromClasspath((getTemplatePath(httpRequest.getRequestLine().getPath())))
        );
    }

    private String getTemplatePath(String path) {
        return PATH + path;
    }
}
