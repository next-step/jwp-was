package webserver;

import exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;

import java.io.DataOutputStream;
import java.io.IOException;

import static java.util.Optional.*;
import static java.util.stream.Collectors.joining;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    private ResourceHandler resourceHandler;

    public ResponseHandler() {
        this.resourceHandler = new ResourceHandler();
    }

    public void handle(DataOutputStream dos, RequestHolder requestHolder) {
        try {
            String contents = resourceHandler.getContents(requestHolder.getPath());
            byte[] body = contents.getBytes();
            sendResponse(StatusCode.OK, dos, body, requestHolder);
        } catch (HttpException e) {
            logger.error("Http Exception " + e.getStatusCode());
            byte[] errorMessage = e.getMessage().getBytes();
            sendResponse(e.getStatusCode(), dos, errorMessage, requestHolder);
        }
    }

    public void sendResponse(StatusCode statusCode, DataOutputStream dos, byte[] body, RequestHolder requestHolder) {
        responseHeader(statusCode, dos, body.length, requestHolder);
        responseBody(dos, body);
    }

    private void responseHeader(StatusCode statusCode, DataOutputStream dos, int lengthOfBodyContent, RequestHolder requestHolder) {
        String contentType = ofNullable(requestHolder)
                .map(RequestHolder::getRequestHeader)
                .map(RequestHeader::getAccept)
                .map(accepts -> accepts.split(",")[0])
                .orElse("text/html");

        logger.debug("code: {}, message: {}, contentType: {}", statusCode.getCode(), statusCode.getMessage(), contentType);

        try {
            dos.writeBytes("HTTP/1.1 " + statusCode.getCode() + " " + statusCode.getMessage() + " \r\n");
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
