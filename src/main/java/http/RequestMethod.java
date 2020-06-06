package http;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RequestMethod {
    String getPath();

    String getMethodName();

    byte[] readFile() throws IOException, URISyntaxException;
}
