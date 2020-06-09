package webserver;

import http.RequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class ResourceMapper {
    private static final Logger logger = LoggerFactory.getLogger(ResourceMapper.class);

    public static byte[] getResource(RequestMessage requestMessage) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates" + requestMessage.getPath());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "Hello World".getBytes();
    }
}
