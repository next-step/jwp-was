package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.http.ContentType;
import webserver.http.request.Request;
import webserver.http.response.Response;

import java.io.IOException;
import java.net.URISyntaxException;

public class DefaultController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    private static final String RESOURCES_TEMPLATES = "./templates";
    private static final String RESOURCES_STATIC = "./static";
    public final static String EXTENSION_SPERATOR = ".";

    @Override
    public void doPost(Request request, Response response) {
    }

    @Override
    public void doGet(Request request, Response response) throws IOException {
        if (isTemplateExtention(request.getRequestPath())) {
            readBody(request, response);
            return;
        }
    }

    public void readBody(Request request, Response response) {
        try {
            byte[] responseBody = FileIoUtils.loadFileFromClasspath(request.getRequestPath());
            response.setBody(responseBody);


        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean isTemplateExtention(String path) {
        String extension = getFilePath(path);

        return ContentType.isFileExtension(extension);
    }

    private String getFilePath(String path) {
        return path.substring(path.lastIndexOf(EXTENSION_SPERATOR) + 1);
    }
}
