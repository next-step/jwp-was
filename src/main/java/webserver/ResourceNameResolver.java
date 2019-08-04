package webserver;

import java.util.List;

import static java.util.Arrays.asList;

public class ResourceNameResolver {

    private final static List<String> staticExts = asList("js", "css", "woff", "svg", "ttf", "eot", "svg", "png");

    public String resolveName(String path) {
        return getResourceName(path);
    }

    private String getResourceName(String path) {
        String ext = path.substring(path.lastIndexOf('.') + 1);

        if (staticExts.contains(ext)) {
            return "static" + path;
        }

        return "templates" + path;
    }
}
