package mvc.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileIoUtils;
import was.http.HttpRequest;
import was.http.HttpResponse;

import java.util.Optional;

public class StaticResourceView implements View {
    private static final Logger logger = LoggerFactory.getLogger(StaticResourceView.class);
    private static final String DEFAULT_STATIC_PATH = "./static";

    @Override
    public void render(HttpRequest request, HttpResponse response) throws Exception {
        String path = request.getPath();
        Optional<byte[]> maybeResource = FileIoUtils.loadFileFromClasspath(DEFAULT_STATIC_PATH + path);
        if (maybeResource.isPresent()) {
            byte[] body = maybeResource.get();
            setContentType(response, path);
            response.responseBody(body);
        } else {
            response.response404();
        }
    }

    private void setContentType(HttpResponse response, String path) {
        if (path.endsWith(".css")) {
            response.addHeader("Content-Type", "text/css");
        } else if (path.endsWith(".js")) {
            response.addHeader("Content-Type", "application/javascript");
        }
    }
}
