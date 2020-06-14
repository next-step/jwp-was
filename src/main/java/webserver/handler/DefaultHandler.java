package webserver.handler;

import http.RequestMessage;
import http.ResponseMessage;
import utils.FileIoUtils;

public class DefaultHandler implements Handler {

    public static final String TEMPLATE_PATH = "./templates";

    private static final DefaultHandler INSTANCE = new DefaultHandler();

    private DefaultHandler() {
    }

    public static DefaultHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void doGet(RequestMessage requestMessage, ResponseMessage responseMessage) {
        String requestPath = requestMessage.getPath();

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATE_PATH + requestPath);

            responseMessage.response200Header(body.length);
            responseMessage.responseBody(body);
        } catch (Exception e) {
            String reason = requestPath + " is not found";
            byte[] body = reason.getBytes();

            responseMessage.response404Header();
            responseMessage.responseBody(body);
        }
    }

    @Override
    public void doPost(RequestMessage requestMessage, ResponseMessage responseMessage) {
    }
}
