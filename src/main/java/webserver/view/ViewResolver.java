package webserver.view;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ViewResolver {
    byte[] render(String vieName) throws IOException, URISyntaxException;
}
