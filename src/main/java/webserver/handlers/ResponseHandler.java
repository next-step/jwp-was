package webserver.handlers;

import webserver.domain.ResponseEntity;

import java.net.Socket;

public interface ResponseHandler extends Handler<ResponseEntity<?>, Void> {
    ResponseHandler changeConnection(Socket conn);
}
