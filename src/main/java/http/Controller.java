package http;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Controller {

    String STATIC_PREFIX = "./templates/";

    void handle(HttpRequest request, HttpResponse response) throws IOException, URISyntaxException, NoSuchMethodException;

}
