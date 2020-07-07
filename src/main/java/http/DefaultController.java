package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DefaultController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        if (request.isGet()) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    void doGet(final HttpRequest request, final HttpResponse response) {
        logger.info("DefaultController - doGet");
        response.badRequest();
    }

    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("DefaultController - doPost");
        response.badRequest();
    }

}
