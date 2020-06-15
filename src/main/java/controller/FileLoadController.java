package controller;

import http.FileExtension;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;

public class FileLoadController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(FileLoadController.class);

    @Override
    public String getPath() {
        return "";
    }


    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        final String path = request.getPath();
        FileExtension fileExtension = getExtension(path);
        String filePath = fileExtension.getPhysicalPath().concat(path);
        byte[] body = fileLoadFromPath(filePath);

        if (body.length == 0) {
            response.response400Header();
            return;
        }

        response.addHeader("Content-Type", fileExtension.getMimeType() + ";charset=utf-8");
        response.response200Header(body.length);
        response.responseBody(body);
    }

    public static byte[] fileLoadFromPath(String path) {
        try {
            if (path.endsWith("/")) return new byte[0];
            return FileIoUtils.loadFileFromClasspath(path);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return new byte[0];
    }

    private static FileExtension getExtension(final String filePath) {
        if (filePath.contains("\\.")) return FileExtension.NONE;
        String[] split = filePath.split("\\.");
        String extension = split[split.length - 1];
        return FileExtension.of(extension);
    }
}
