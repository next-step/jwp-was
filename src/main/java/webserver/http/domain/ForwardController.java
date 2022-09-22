package webserver.http.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ForwardController.class);

    private final String path;

    public ForwardController(String path) {
        this.path = path;
    }

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.forward(path);
    }
}
