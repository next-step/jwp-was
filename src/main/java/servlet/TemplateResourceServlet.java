package servlet;

import utils.FileIoUtils;
import webserver.Request;
import webserver.Response;
import webserver.HttpServlet;

import java.util.Arrays;
import java.util.List;

public class TemplateResourceServlet implements HttpServlet {

    private static final String TEMPLATES_PATH = "./templates";
    private static final List<String> templateResources = Arrays.asList("html", "ico");

    @Override
    public boolean isMapping(Request request) {
        String suffix = getSuffix(request);
        return templateResources.stream()
                .anyMatch(suffix::equals);
    }

    @Override
    public Response service(Request request) throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + request.getPath());
        return Response.ok(body);
    }

    private String getSuffix(Request request) {
        String path = request.getPath();
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
