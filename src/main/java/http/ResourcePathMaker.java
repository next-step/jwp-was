package http;

public class ResourcePathMaker {
    public static String DEFAULT_TEMPLATE_PATH = "./templates";

    public static String makeTemplatePath(String path) {
        return DEFAULT_TEMPLATE_PATH + path;
    }
}
