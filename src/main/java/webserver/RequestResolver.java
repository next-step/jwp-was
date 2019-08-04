package webserver;

import webserver.request.HttpRequest;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by hspark on 2019-08-05.
 */
public class RequestResolver {
    private FileResolver fileResolver = new FileResolver();


    public byte[] resolve(HttpRequest httpRequest) throws IOException, URISyntaxException {
        if (httpRequest.getRequestLine().getRequestUrl().isFile()) {
            return fileResolver.resolve(httpRequest);
        }
        return null;
    }

}
