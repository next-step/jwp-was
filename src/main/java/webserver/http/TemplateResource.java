package webserver.http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class TemplateResource implements Resource {

    @Override
    public boolean match(String url) {
        return url.endsWith(".html") || url.endsWith(".ico");
    }

    @Override
    public byte[] handle(String url, Headers headers) {
        try {
            return FileIoUtils.loadFileFromClasspath("./templates" + url);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
