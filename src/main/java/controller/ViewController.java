package controller;

import model.HttpMethod;
import model.HttpRequest;
import model.HttpResponse;
import model.RequestMappingInfo;
import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class ViewController implements Controller{


    private static RequestMappingInfo mappingInfo = new RequestMappingInfo(HttpMethod.GET, "/.*");

    @Override
    public boolean isPath(RequestMappingInfo info) {
        return mappingInfo.equals(info);
    }

    @Override
    public HttpResponse process(HttpRequest request) throws IOException, URISyntaxException {
        final byte[] body = FileIoUtils.loadFileFromClasspath(request.getRequestLine().getRequestPath());
        return HttpResponse.ok(body);
    }
}
