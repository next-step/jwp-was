package http.controller;

import http.HttpRequest;
import http.ResourcePathMaker;
import http.enums.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created By kjs4395 on 2020-06-05
 */
public abstract class PathController {
    private static final Logger log = LoggerFactory.getLogger(PathController.class);

    HttpRequest httpRequest;

    public PathController(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public byte[] execute() {
        if(this.httpRequest.getRequestLine().getMethod().equals(Method.GET)) {
            log.info("get method execute ---------");
            return this.get();
        }

        if(this.httpRequest.getRequestLine().getMethod().equals(Method.POST)) {
            log.info("post method execute ---------");
            return this.post();
        }

        return new byte[0];
    }


    public byte[] get()  {
        log.info("default controller get method execute ---------");
        String resourcePath = ResourcePathMaker.makeTemplatePath(httpRequest.getRequestLine().getPath());

        try {
            return FileIoUtils.loadFileFromClasspath(resourcePath);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
            return new byte[0];
        }
    }

    public byte[] post() {
        log.info("default controller post method execute ---------");
        return new byte[0];
    }
}
