package webserver.http;

import utils.FileIoUtils;

import java.io.IOException;
import java.net.URISyntaxException;

public class StaticResource implements Resource {

    @Override
    public boolean match(String url) {
        return url.endsWith(".css") || url.endsWith(".js") || url.startsWith("/fonts");
    }

    @Override
    public byte[] handle(String url, Headers headers) {
        putHeader(url, headers);

        try {
            return FileIoUtils.loadFileFromClasspath("./static" + url);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private void putHeader(String url, Headers headers) {
        if (url.endsWith(".css")) {
            headers.put("Content-Type", "text/css");
        }
        if (url.endsWith(".js")) {
            headers.put("Content-Type", "application/javascript");
        }
    }
}
