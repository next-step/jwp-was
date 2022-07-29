package webserver.request;

import model.User;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private Map<String, String> body;

    public RequestBody() {
        body = new HashMap<>();
    }

    private RequestBody(String body) {
        this.body = IOUtils.changeStringToMap(body);
    }

    public static RequestBody parsing(BufferedReader br, int contentLength) throws IOException {

        return new RequestBody(IOUtils.readData(br, contentLength));
    }

    public User bodyToUser() {

        return new User(body.get("userId"), body.get("password"), body.get("name"), body.get("email"));
    }

    public Map<String, String> getBody() {
        return body;
    }
}
