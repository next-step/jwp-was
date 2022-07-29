package webserver.handlers;

import webserver.domain.ResponseEntity;

import java.io.BufferedWriter;
import java.io.Writer;

public interface ResponseHandler extends Handler<ResponseEntity<?>, Void>{
    ResponseHandler changeWriter(BufferedWriter writer);
}
