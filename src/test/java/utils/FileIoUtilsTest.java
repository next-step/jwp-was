package utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileIoUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(FileIoUtilsTest.class);

    @Test
    void loadFileFromClasspath() throws Exception {
        byte[] body = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        log.debug("file : {}", new String(body));
    }

    @Test
    void getMimeTypeFromClasspath() throws Exception {
        String filePath = "./static/css/styles.css";

        Path path = Paths.get(FileIoUtils.class.getClassLoader().getResource(filePath).toURI());
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String mimeType = mimeTypesMap.getContentType(path.toFile());

        log.debug("mimeType : {}", mimeType);
    }
}
