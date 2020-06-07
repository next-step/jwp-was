package web;

import http.HttpHeaders;
import http.HttpRequest;
import http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceHttpRequestHandler implements HttpRequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResourceHttpRequestHandler.class);

    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        byte[] body;
        try {
            body = FileIoUtils.loadFileFromClasspath("./static/" + httpRequest.getPath());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
            return;
        }

        String accept = httpRequest.getHeader(HttpHeaders.ACCEPT);
        httpResponse.setHeader(HttpHeaders.ACCEPT, accept);

        httpResponse.writeHeader();
        DataOutputStream dataOutputStream = httpResponse.getDataOutputStream();

        dataOutputStream.write(body, 0, body.length);
        dataOutputStream.flush();
        httpResponse.responseDone();
    }
}
