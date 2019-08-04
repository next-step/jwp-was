package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ResponseHandler;
import webserver.StatusCode;
import webserver.request.RequestHeader;
import webserver.request.RequestHolder;

import java.io.DataOutputStream;
import java.io.IOException;

import static java.util.Optional.ofNullable;

public class ResponseSender {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public void sendResponse(StatusCode statusCode, ResponseHolder responseHolder, byte[] body) {
        try {
            responseHeader(statusCode, responseHolder, body.length);
            responseBody(responseHolder.getDos(), body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void sendResponse(StatusCode statusCode, ResponseHolder responseHolder) {
        sendResponse(statusCode, responseHolder, new byte[0]);
    }

    private void responseHeader(StatusCode statusCode, ResponseHolder responseHolder, int lengthOfBodyContent) throws IOException {
        DataOutputStream dos = responseHolder.getDos();
        String contentType = ofNullable(responseHolder.getRequestHolder())
                .map(RequestHolder::getRequestHeader)
                .map(RequestHeader::getAccept)
                .map(accepts -> accepts.split(",")[0])
                .orElse("text/html");

        logger.debug("code: {}, message: {}, contentType: {}", statusCode.getCode(), statusCode.getMessage(), contentType);

        // handle response by status code (responseHolder's dos must be not null)
        statusCode.handleResponse(responseHolder);

        if (lengthOfBodyContent > 0) {
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
        }

        dos.writeBytes("\r\n");
    }

    private void responseBody(DataOutputStream dos, byte[] body) throws IOException {
        if (body.length > 0) {
            dos.write(body, 0, body.length);
        }

        dos.flush();
    }

}
