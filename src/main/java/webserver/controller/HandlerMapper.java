package webserver.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import model.User;
import org.springframework.util.SerializationUtils;
import webserver.request.HttpRequest;

public class HandlerMapper {
    private static final String USER_CONTROLLER_PATH = "/user/create";

    public byte[] handle(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.getPath().equals(USER_CONTROLLER_PATH)) {
            User result = new UserController().execute(httpRequest);
            return convertObjectToByteArray(result);
        }

        return new ViewController().handle(httpRequest);
    }

    private byte[] convertObjectToByteArray(Object input) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(input);
        out.flush();

        return bos.toByteArray();
    }
}
