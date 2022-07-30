package webserver.config;

import utils.FileIoUtils;

import java.net.URISyntaxException;
import java.util.List;

public class StaticLocationConfig {

    private List<String> staticLocations = List.of("./templates", "./static");

    public StaticLocationConfig() {
    }

    public StaticLocationConfig(List<String> customStaticLocations) {
        this.staticLocations = customStaticLocations;
    }

    public String getStaticLocation(String path) {
        return staticLocations
                .stream()
                .filter(staticLocation -> existsFromClassPath(staticLocation + path))
                .findFirst()
                .orElseThrow();
    }

    private boolean existsFromClassPath(String path) {
        try {
            return FileIoUtils.existsFileFromClassPath(path);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
