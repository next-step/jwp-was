package http.handler;

import com.google.common.collect.Maps;
import db.DataBase;
import http.common.HttpHeaders;
import http.common.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import model.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static http.common.HttpHeader.LOCATION_HEADER_NAME;

public class CreateUserHandler extends AbstractHandler {
    public static final String INDEX_PATH = "/index.html";

    @Override
    public String getPath() {
        return INDEX_PATH;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FOUND;
    }

    @Override
    protected HttpHeaders getHttpHeaders(HttpRequest httpRequest, int length) {
        Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.put(LOCATION_HEADER_NAME, INDEX_PATH);
        return new HttpHeaders(httpHeaders);
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException {
        User user = User.of(request.getParameterMap());
        DataBase.addUser(user);
        super.handle(request, response);
    }
}
