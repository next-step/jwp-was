package webserver.view;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class HtmlViewResolver implements ViewResolver {

    private static final String PREFIX = "./templates";

    @Override
    public byte[] render(String vieName) throws IOException, URISyntaxException {
        return FileIoUtils.loadFileFromClasspath(PREFIX + vieName);
    }
}
