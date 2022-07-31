package webserver.handlers;

import webserver.domain.HttpRequest;
import webserver.domain.ResponseEntity;

public interface RequestHandler extends Handler<HttpRequest, ResponseEntity<?>> {
}
