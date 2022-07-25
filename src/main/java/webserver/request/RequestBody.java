package webserver.request;

import model.User;
import utils.IOUtils;
import utils.RequestUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBody {

    private String body;

    private RequestBody() {
    }

    private RequestBody(String body) {
        this.body = body;
    }

    private static class InnerInstanceRequestLine {
        private static final RequestBody instance = new RequestBody();
    }

    public static RequestBody getInstance() {
        return RequestBody.InnerInstanceRequestLine.instance;
    }

    public RequestBody parsing(BufferedReader br, int contentLength) throws IOException {

        return new RequestBody(IOUtils.readData(br, contentLength));
    }

    public User bodyToUser() {
        User user = RequestUtils.convertToUser(body);
        return user;
    }

    public String getBody() {
        return body;
    }
}
