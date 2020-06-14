package controller.staticfile;

import controller.AbstractController;
import http.request.HttpRequest;
import http.response.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
public class StaticFileController extends AbstractController {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.hasPathFileExtension()) {
            readStaticFile(httpRequest, httpResponse);
            return;
        }

        throw new IllegalArgumentException("Illegal Request Mapping");
    }

    private void readStaticFile(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            byte[] responseBody = FileIoUtils.loadFileFromClasspath(httpRequest.getFilePath());
            httpResponse.updateResponseBodyContent(responseBody);
        } catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }
}
