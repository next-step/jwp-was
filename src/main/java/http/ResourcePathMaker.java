package http;

public class ResourcePathMaker {
    public static String DEFAULT_TEMPLATE_PATH = "./templates";

    public static String DEFAULT_RESOURCE_PATH = "./static";

    public static String makeTemplatePath(String path) {
        return DEFAULT_TEMPLATE_PATH + path;
    }

    public static String makeResourcePath(String path) {
        return DEFAULT_RESOURCE_PATH + path;
    }
}
