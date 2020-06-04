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
    public HttpResponse process(final HttpRequest httpRequest) throws IOException, URISyntaxException {
        String filePath = "./templates" + httpRequest.getPath();
        byte[] body = FileIoUtils.loadFileFromClasspath(filePath);

        return new HttpResponse(200, body);
    }
}
