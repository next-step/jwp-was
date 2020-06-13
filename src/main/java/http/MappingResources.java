package http;

import resource.*;

import java.util.HashMap;
import java.util.Map;

public class MappingResources {

    private static final Map<String, ResourceController> mappingResources;

    static {
        mappingResources = new HashMap<>();
        mappingResources.put("css", new CssController());
        mappingResources.put("png", new ImageController());
        mappingResources.put("js", new JsController());
        mappingResources.put("eot", new FontController());
        mappingResources.put("svg", new FontController());
        mappingResources.put("ttf", new FontController());
        mappingResources.put("woff", new FontController());
        mappingResources.put("woff2", new FontController());
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
