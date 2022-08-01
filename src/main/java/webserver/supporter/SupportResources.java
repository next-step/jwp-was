package webserver.supporter;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.domain.ContentType;
import webserver.domain.HttpHeader;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public final class SupportResources {

    public static final Logger logger = LoggerFactory.getLogger(SupportResources.class);

    public static final String PATH_STATIC = "./static";

    private SupportResources() {
    }

    private static Set<String> resourceSet;

    static {
        resourceSet = Sets.newHashSet();
        Arrays.stream(ContentType.values())
            .forEach(contentType -> resourceSet.add(contentType.extension()));
    }

    public static boolean isSupportedExtension(String path) {
        return resourceSet.contains(path.substring(path.lastIndexOf('.') + 1));
    }

    public static void serve(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();

        String extension = path.substring(path.lastIndexOf('.') + 1);
        ContentType contentType = ContentType.valueOf(extension.toUpperCase());

        if (contentType == ContentType.HTML) {
            SupportTemplates.execute(httpRequest, httpResponse);
            return;
        }

        httpResponse.addHeader(HttpHeader.CONTENT_TYPE, contentType.type());
        httpResponse.forward(PATH_STATIC + path);
    }

}
