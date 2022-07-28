package webserver.supporter;

import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import webserver.domain.ContentType;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public final class SupportTemplates {
    private static final Logger logger = LoggerFactory.getLogger(SupportTemplates.class);
    public static final String PATH_TEMPLATES = "./templates";

    private SupportTemplates() { }

    public static void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            byte[] body = FileIoUtils.loadFileFromClasspath(PATH_TEMPLATES + httpRequest.getPath());
            httpResponse.okWithBody(body, ContentType.HTML.type());
        } catch (IOException  e) {
            httpResponse.notFound();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

}
