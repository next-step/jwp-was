package webserver.handler;

import http.*;
import org.slf4j.Logger;
import utils.FileIoUtils;


import static org.slf4j.LoggerFactory.getLogger;

public class DefaultHandler extends AbstractHandler {

    private static final Logger logger = getLogger(DefaultHandler.class);

    private static final DefaultHandler INSTANCE = new DefaultHandler();

    private DefaultHandler() {
    }

    public static Handler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) {
        Uri uri = requestMessage.getUri();

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(ResourceFormat.toRelativePath(uri));

            responseMessage.responseWith(HttpStatus.OK, body, ResourceFormat.from(uri));
        } catch (Exception e) {
            String reason = uri.getPath() + " is not found";
            byte[] body = reason.getBytes();

            responseMessage.responseWith(HttpStatus.NOT_FOUND, body, ResourceFormat.PLAIN);
        }
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
    }
}
