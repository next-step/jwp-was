package http.handler;

import com.google.common.collect.Maps;
import db.DataBase;
import http.HttpEntity;
import http.HttpHeaders;
import http.HttpMethod;
import http.HttpStatus;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.StatusLine;
import lombok.extern.slf4j.Slf4j;
import model.User;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Slf4j
public class CreateUserHandler extends AbstractHandler {
    private static final String HEADER_LOCATION_NAME = "Location";
    private static final String INDEX_FILE_PATH = "/index.html";

    @Override
    public HttpResponse getResponse(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte [] httpBody = getHttpBody(httpRequest.getPath());

        HttpResponse httpResponse = new HttpResponse(
                StatusLine.of(httpRequest.getProtocol(), HttpStatus.FOUND),
                new HttpEntity(getHttpHeaders(httpBody.length), new String(httpBody))
        );

        return httpResponse;
    }

    @Override
    protected HttpHeaders getHttpHeaders(int length) {
        Map<String, String> httpHeaders = Maps.newHashMap();
        httpHeaders.put(HEADER_LOCATION_NAME, INDEX_FILE_PATH);
        return new HttpHeaders(httpHeaders);
    }

    @Override
    public byte[] getHttpBody(String path) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + INDEX_FILE_PATH);
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (HttpMethod.GET.equals(httpRequest.getMethod())) {
            User user = User.of(httpRequest.getQueryString());
            log.debug("created User: {}", user);
            DataBase.addUser(user);
        }
        else if (HttpMethod.POST.equals(httpRequest.getMethod())) {
            User user = User.of(httpRequest.getBodyMap());
            log.debug("created User: {}", user);
            DataBase.addUser(user);
        }
    }
}
