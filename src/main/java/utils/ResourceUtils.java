package utils;

import java.util.HashMap;
import java.util.Map;

public class ResourceUtils {
    public static final Map<String, String> resourcePath = new HashMap<>();

    static {
        resourcePath.put("html", "./templates");
        resourcePath.put("css", "./static");
        resourcePath.put("ico", "./templates");
        resourcePath.put("js", "./static");
    }
}
