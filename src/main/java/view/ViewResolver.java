package view;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ViewResolver {

  View resolve(String viewName) throws IOException, URISyntaxException;
}
