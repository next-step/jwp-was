package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceController extends DefaultController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        super.handle(request, response);
    }

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        logger.info("ResourceController - doGet");
        response.forward(request.getPath());
    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("ResourceController - doPost");
        super.doPost(request, response);
    }
}
