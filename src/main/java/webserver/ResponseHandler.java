package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;

import java.io.DataOutputStream;
import java.io.IOException;

import static java.util.Optional.of;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private ResourceHandler resourceHandler;

    public ResponseHandler() {
        this.resourceHandler = new ResourceHandler();
    }

    public void handle(DataOutputStream dos, RequestHolder requestHolder) {
        String contents = resourceHandler.getContents(requestHolder.getPath());
        byte[] body = contents.getBytes();
        response200Header(dos, body.length, requestHolder);
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, RequestHolder requestHolder) {
        String contentType = of(requestHolder)
                .map(RequestHolder::getRequestHeader)
                .map(RequestHeader::getAccept)
                .orElse("text/html");
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
