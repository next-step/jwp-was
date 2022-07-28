package webserver.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import utils.FileIoUtils;
import webserver.request.HttpRequest;

public class ViewController {
    private static final String VIEW_PATH = "./templates";

    public byte[] handle(HttpRequest httpRequest) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(VIEW_PATH + httpRequest.getPath());
    }
}
