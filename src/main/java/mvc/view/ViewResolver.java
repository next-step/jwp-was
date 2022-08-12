package mvc.view;

import java.io.IOException;

public interface ViewResolver {

    View resolveViewName(String viewName) throws IOException;
}
