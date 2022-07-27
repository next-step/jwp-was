package webserver.config;

import java.util.Map;

public class StaticLocationConfig {

    private final static Map<String, String> STATIC_PATH_BY_EXT = Map.of(
            "html", "./templates",
            "ico", "./static/images"
    );

    public String getStaticLocation(String ext) {
        return STATIC_PATH_BY_EXT.getOrDefault(ext, "./static");
    }

}
