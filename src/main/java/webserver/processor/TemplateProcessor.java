package webserver.processor;

import http.HttpRequest;
import http.HttpResponse;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateProcessor implements Processor {

    @Override
    public boolean isMatch(final HttpRequest httpRequest) {
        return httpRequest.getPath()
                .endsWith(".html");
    }

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        String filePath = "./templates" + httpRequest.getPath();
        byte[] body = new byte[0];
        try {
            body = FileIoUtils.loadFileFromClasspath(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new HttpResponse(200, body);
    }
}
