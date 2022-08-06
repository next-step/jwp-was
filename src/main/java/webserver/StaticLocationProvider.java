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

    public String getStaticResourcePath(String path) {
        return staticLocations
                .stream()
                .map(staticLocation -> staticLocation + path)
                .filter(this::existsFromClassPath)
                .findFirst()
                .orElseThrow(() -> new NotFoundResourceException("not found " + path));
    }

    private boolean existsFromClassPath(String path) {
        try {
            return FileIoUtils.existsFileFromClassPath(path);
        } catch (URISyntaxException e) {
            throw new NotFoundResourceException("not found " + path, e);
        }
    }

}
