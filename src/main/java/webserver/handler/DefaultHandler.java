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

    public static final String TEMPLATE_PATH = "./templates";
    public static final String STATIC_PATH = "./static";

    private static final DefaultHandler INSTANCE = new DefaultHandler();

    private DefaultHandler() {
    }

    public static DefaultHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) {
        Uri uri = requestMessage.getUri();
        ContentType contentType = ContentType.valueOf(uri.getExtension().toUpperCase());

        String relativePath = toSuffixPath(contentType) + uri.getPath();

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(relativePath);

            responseMessage.setHeader("Content-Type", contentType.getMediaType());
            responseMessage.response200Header(body.length);
            responseMessage.responseBody(body);
        } catch (Exception e) {
            String reason = uri.getPath() + " is not found";
            byte[] body = reason.getBytes();

            responseMessage.response404Header();
            responseMessage.responseBody(body);
        }
    }

    private String toSuffixPath(ContentType contentType) {
        if(contentType == ContentType.HTML || contentType == ContentType.ICO) {
            return TEMPLATE_PATH;
        }
        return STATIC_PATH;
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
    }
}
