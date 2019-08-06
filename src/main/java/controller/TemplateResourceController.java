package controller;

import utils.FileIoUtils;
import webserver.Request;
import webserver.Response;

import java.util.Arrays;
import java.util.List;

public class TemplateResourceController extends AbstractController {

    private static final String TEMPLATES_PATH = "./templates";
    private static final List<String> templateResources = Arrays.asList("html", "ico");

    public static boolean isMapping(Request request) {
        String suffix = getSuffix(request);
        return templateResources.stream()
                .anyMatch(suffix::equals);
    }

    @Override
    void doGet(Request request, Response response) throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + request.getPath());
        response.ok(body);
    }

    @Override
    void doPost(Request request, Response response) throws Exception {
        response.notFound();
    }

    private static String getSuffix(Request request) {
        String path = request.getPath();
        return path.substring(path.lastIndexOf(".") + 1);
    }
}
