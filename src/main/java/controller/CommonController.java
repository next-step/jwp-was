package controller;

import annotations.Controller;
import annotations.RequestMapping;
import http.HttpRequest;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class CommonController {

    @RequestMapping("/index.html")
    public byte[] index(HttpRequest httpRequest) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        return body;
    }
}
