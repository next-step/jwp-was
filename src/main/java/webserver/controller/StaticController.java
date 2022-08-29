package webserver.controller;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import http.httprequest.HttpRequest;
import http.httprequest.requestline.HttpMethod;
import http.httpresponse.HttpHeaders;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import http.httpresponse.ResponseHeader;
import http.httpresponse.StatusLine;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

public class StaticController implements Controller {
    private static final String PATH = "./static";

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
                && this.getClass().getClassLoader().getResource(getStaticPath(path)) != null;
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return new HttpResponse(
                new StatusLine(HttpStatusCode.OK),
                new ResponseHeader(Collections.singletonMap(HttpHeaders.CONTENT_TYPE, String.format("text/%s;charset=utf-8", fileExtension(httpRequest.getPath())))),
                FileIoUtils.loadFileFromClasspath(getStaticPath(httpRequest.getPath()))
        );
    }

    private String getStaticPath(String path) {
        return PATH + path;
    }

    private String fileExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }
}
