package http.handler;

import http.HttpHeaders;
import http.exception.MethodNotAllowedException;
import http.request.HttpRequest;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

@Slf4j
public class LoginHandler extends AbstractHandler {
    @Override
    protected HttpHeaders getHttpHeaders(int length) {
        return null;
    }
}
