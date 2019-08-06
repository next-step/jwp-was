package servlet;

import utils.FileIoUtils;
import webserver.HttpServlet;
import webserver.Request;
import webserver.Response;

import java.util.Arrays;
import java.util.List;

public class TemplateResourceServlet implements HttpServlet {

    private static final String TEMPLATES_PATH = "./templates";
    private static final List<String> templateResources = Arrays.asList("html", "ico");

    public static boolean isMapping(Request request) {
        String suffix = getSuffix(request);
        return templateResources.stream()
                .anyMatch(suffix::equals);
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + request.getPath());
        response.ok(body);
    }

    private static String getSuffix(Request request) {
        String path = request.getPath();
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
