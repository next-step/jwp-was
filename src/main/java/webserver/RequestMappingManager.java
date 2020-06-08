package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestMappingManager {
    private static final Logger logger = LoggerFactory.getLogger(RequestMappingManager.class);

    private final static String PHYSICAL_DEFAULT_PATH = "./templates";

    private final Map<String, String> requestMap = new HashMap<>();

    public static byte[] fileLoadFromPath(String uri) {
        try {
            String path = PHYSICAL_DEFAULT_PATH.concat(uri);
            return FileIoUtils.loadFileFromClasspath(path);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new byte[0];
    }

}
