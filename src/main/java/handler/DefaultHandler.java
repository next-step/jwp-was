package handler;

import http.request.RequestMessage;
import http.request.Uri;
import http.response.ContentType;
import http.response.HttpStatus;
import http.response.ResponseMessage;
import org.slf4j.Logger;
import webserver.StaticResourceLoader;


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
            byte[] body = StaticResourceLoader.loadResource(uri.getPath());

            responseMessage.forward(HttpStatus.OK, body, ContentType.from(uri.getExtension()));
        } catch (Exception e) {
            String reason = uri.getPath() + " is not found";
            byte[] body = reason.getBytes();

            responseMessage.forward(HttpStatus.NOT_FOUND, body, ContentType.PLAIN);
        }
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
    }
}
