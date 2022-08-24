package model.request;

import java.io.BufferedReader;
import java.io.IOException;
import model.HttpHeaders;
import utils.IOUtils;

public class RequestBody {
    private final String value;

    public RequestBody(BufferedReader br, HttpHeaders httpHeaders) throws IOException {
        value = IOUtils.readData(br, Integer.parseInt(httpHeaders.get("Content-Length")));
    }

    public String getValue() {
        return value;
    }
}
