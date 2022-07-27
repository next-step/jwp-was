package webserver.supporter;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import utils.FileIoUtils;
import webserver.domain.ContentType;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public final class SupportResources {
    public static final String PATH_STATIC = "./static";

    private SupportResources() { }

    private static Set<String> resourceMap;
    static {
        resourceMap = Sets.newHashSet();
        resourceMap.add("/css/bootstrap.min.css");
        resourceMap.add("/css/styles.css");
        resourceMap.add("/fonts/glyphicons-halflings-regular.eot");
        resourceMap.add("/fonts/glyphicons-halflings-regular.svg");
        resourceMap.add("/fonts/glyphicons-halflings-regular.ttf");
        resourceMap.add("/fonts/glyphicons-halflings-regular.woff");
        resourceMap.add("/fonts/glyphicons-halflings-regular.woff2");
        resourceMap.add("/images/80-text.png");
        resourceMap.add("/js/bootstrap.min.js");
        resourceMap.add("/js/jquery-2.2.0.min.js");
        resourceMap.add("/js/scripts.js");
    }

    public static boolean isSupported(String path) {
        return resourceMap.contains(path);
    }

    public static void responseResource(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String path = httpRequest.getPath();

        String extend = path.substring(path.lastIndexOf('.')+1);
        ContentType contentType = ContentType.valueOf(extend.toUpperCase());

        byte[] body = FileIoUtils.loadFileFromClasspath(PATH_STATIC + path);
        httpResponse.ok();
        httpResponse.addHeader("Content-Type", contentType.type());
        httpResponse.addHeader("Content-Length", Integer.toString(body.length));
        httpResponse.setBody(body);
    }

}
