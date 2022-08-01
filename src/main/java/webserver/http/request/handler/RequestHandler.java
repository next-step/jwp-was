package webserver.http.request.handler;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RequestHandler {

    byte[] execute() throws IOException, URISyntaxException;
}
