package webserver;

import utils.FileIoUtils;

import java.net.URISyntaxException;
import java.util.List;

public class StaticLocationProvider {

    private static final List<String> DEFAULT_STATIC_LOCATIONS = List.of("./templates", "./static");

    private final List<String> staticLocations;

    public StaticLocationProvider(List<String> customStaticLocations) {
        this.staticLocations = customStaticLocations;
    }

    public StaticLocationProvider() {
        this(DEFAULT_STATIC_LOCATIONS);
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
