package http;

public class ResourcePathMaker {
    private static final String DEFAULT_TEMPLATE_PATH = "./templates";

    private static final String DEFAULT_RESOURCE_PATH = "./static";

    public static String makeTemplatePath(String path) {
        return DEFAULT_TEMPLATE_PATH + path;
    }

    public static String makeResourcePath(String path) {
        return DEFAULT_RESOURCE_PATH + path;
    }
}
