package http.handler;

import http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateUserHandler extends AbstractHandler {

    @Override
    protected HttpHeaders getHttpHeaders(int length) {
        return null;
    }
}
