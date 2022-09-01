package model.request;

import java.io.BufferedReader;
import java.io.IOException;
import model.HttpHeaders;
import utils.IOUtils;

public class RequestBody {
    private final String value;

    public RequestBody(BufferedReader br, HttpHeaders httpHeaders) throws IOException {
        value = IOUtils.readData(br, toInt(httpHeaders.get("Content-Length")));
    }

    private int toInt(String value) {
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public String getValue() {
        return value;
    }
}
