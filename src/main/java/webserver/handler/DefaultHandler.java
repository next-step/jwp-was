package webserver.handler;

import http.ContentType;
import http.RequestMessage;
import http.ResponseMessage;
import http.Uri;
import org.slf4j.Logger;
import utils.FileIoUtils;


import static org.slf4j.LoggerFactory.getLogger;

public class DefaultHandler implements Handler {

    private static final Logger logger = getLogger(DefaultHandler.class);

    private static final DefaultHandler INSTANCE = new DefaultHandler();

    private DefaultHandler() {
    }

    public static DefaultHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) {
        Uri uri = requestMessage.getUri();

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(ContentType.toRelativePath(uri));

            responseMessage.setHeader("Content-Type", ContentType.toMediaTypeFrom(uri));
            responseMessage.response200Header(body.length);
            responseMessage.responseBody(body);
        } catch (Exception e) {
            String reason = uri.getPath() + " is not found";
            byte[] body = reason.getBytes();

            responseMessage.response404Header();
            responseMessage.responseBody(body);
        }
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
    }
}
