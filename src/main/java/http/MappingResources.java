package http;

import resource.*;

import java.util.HashMap;
import java.util.Map;

public class MappingResources {
    private static final String CSS_EXTENSION = "css";
    private static final String IMAGE_PNG_EXTENSION = "png";
    private static final String JAVASCRIPT_EXTENSION = "js";
    private static final String FONT_EOT_EXTENSION = "eot";
    private static final String FONT_SVG_EXTENSION = "svg";
    private static final String FONT_TTF_EXTENSION = "ttf";
    private static final String FONT_WOFF_EXTENSION = "woff";
    private static final String FONT_WOFF2_EXTENSION = "woff2";

    private static final Map<String, ResourceController> mappingResources;

    static {
        mappingResources = new HashMap<>();
        mappingResources.put(CSS_EXTENSION, new ResourceController());
        mappingResources.put(IMAGE_PNG_EXTENSION, new ResourceController());
        mappingResources.put(JAVASCRIPT_EXTENSION, new ResourceController());
        mappingResources.put(FONT_EOT_EXTENSION, new ResourceController());
        mappingResources.put(FONT_SVG_EXTENSION, new ResourceController());
        mappingResources.put(FONT_TTF_EXTENSION, new ResourceController());
        mappingResources.put(FONT_WOFF2_EXTENSION, new ResourceController());
        mappingResources.put(FONT_WOFF_EXTENSION, new ResourceController());
    }

    public static ResourceController getResourceController(String path) {
        return mappingResources.get(getExtension(path));
    }

    private static String getExtension(String path) {
        String[] names = path.split("\\.");
        int size = names.length;
        if (size > 0) {
            System.out.println(names[size - 1]);
            return names[size - 1];
        }
        return path;
    }
}
