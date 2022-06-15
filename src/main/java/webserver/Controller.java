package webserver;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {
    Response serving(Request request) throws IOException, URISyntaxException;
}
