package http;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public abstract class UserController extends DefaultController {
    private static final Logger logger = getLogger(UserController.class);

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        super.handle(request, response);
    }

    @Override
    void doGet(final HttpRequest request, final HttpResponse response) {
        logger.info("UserController - doGet");
        super.doGet(request, response);
    }

    @Override
    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("UserController - doPost");
        super.doPost(request, response);
        return;
    }

}
