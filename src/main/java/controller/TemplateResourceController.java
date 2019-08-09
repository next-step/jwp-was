package controller;

import utils.FileIoUtils;
import webserver.Request;
import webserver.Response;
import webserver.response.HttpResponse;

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

    private static String getSuffix(Request request) {
        String path = request.getPath();
        return path.substring(path.lastIndexOf(".") + 1);
    }

    @Override
    Response doGet(Request request) throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath(TEMPLATES_PATH + request.getPath());
        return HttpResponse.ok(body);
    }

    @Override
    Response doPost(Request request) {
        return HttpResponse.notFound();
    }
}
