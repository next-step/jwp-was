package utils;

import http.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FilePathUtils {
    private static final Logger logger = LoggerFactory.getLogger(FilePathUtils.class);

    @Nonnull
    public static String makeFilePath(@Nullable RequestLine requestLine) {
        if (requestLine != null) {
            String path = requestLine.getPath();
            String filePath;
            if ("/".equalsIgnoreCase(requestLine.getPath())) {
                path = "/index.html";
            }

            if (path.endsWith(".html") || path.endsWith(".ico")) {
                filePath = "./templates" + path;
                logger.debug("filePath : " + filePath);
                return filePath;
            }

            filePath = "./static" + path;
            logger.debug("filePath : " + filePath);

            return filePath;
        }

        return "";
    }
}
