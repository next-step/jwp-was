package controller;

import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.RequestMappingInfo;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ViewController implements Controller{

    @Override
    public HttpResponse process(HttpRequest request) throws IOException, URISyntaxException {
        final byte[] body = FileIoUtils.loadFileFromClasspath(request.getRequestLine().getRequestPath());
        return HttpResponse.success(body);
    }
}
