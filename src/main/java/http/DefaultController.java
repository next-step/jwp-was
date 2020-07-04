package http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DefaultController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);

    @Override
    public void handle(final HttpRequest request, final HttpResponse response) {
        setContentType(request, response);
        if (request.isGet()) {
            doGet(request, response);
            return;
        }
        doPost(request, response);
    }

    void setContentType(final HttpRequest request, final HttpResponse response) {
        final String contentType = ContentType.getContentType(request.getAccept());
        response.setContentType(contentType);
    }

    void doGet(final HttpRequest request, final HttpResponse response) {
        logger.info("DefaultController - doGet");
        response.buildResponseLine(HttpStatus.OK);
        response.setCharset("utf-8");
        response.setResponseBody("/user/form.html");
        response.print();
    }

    void doPost(final HttpRequest request, final HttpResponse response) {
        logger.info("DefaultController - doPost");
        response.buildResponseLine(HttpStatus.BAD_REQUEST);
        response.setCharset("utf-8");
        response.setResponseBody("/error/4xx.html");
        response.print();
    }

}
