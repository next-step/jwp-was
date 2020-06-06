package http.controller;

import http.HttpHeaderInfo;
import http.HttpRequest;
import http.HttpResponse;
import http.ResourcePathMaker;
import http.enums.HttpResponseCode;
import http.enums.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
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
            log.info("post method execute ========");
            return this.post();
        }

        return new byte[0];
    }


    public byte[] get()  {
        log.info("default controller get method execute ========");
        HttpHeaderInfo headerInfo = new HttpHeaderInfo();
        String resourcePath = ResourcePathMaker.makeTemplatePath(httpRequest.getRequestLine().getPath());
        headerInfo.addKeyAndValue("Content-Type","text/html;charset=utf-8");

        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(resourcePath);

            if(body.length > 0) {
                headerInfo.addKeyAndValue("Content-Length", String.valueOf(body.length));
            }

            HttpResponse httpResponse = new HttpResponse(HttpResponseCode.OK, body, headerInfo);
            return httpResponse.makeResponseBody();

        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage());
            return new byte[0];
        }
    }

    public byte[] post() {
        log.info("default controller post method execute ========");
        return new byte[0];
    }

    private String addHeaderContentLength(String header, int length) {
        return header + "Content-Type: text/html;charset=utf-8" + System.lineSeparator() +
                "Content-Length : "+ length + System.lineSeparator() + System.lineSeparator();
    }

}
