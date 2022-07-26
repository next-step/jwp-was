package webserver.supporter;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

public final class SupportTemplates {
    public static final String PATH_TEMPLATES = "./templates";

    private SupportTemplates() { }

    private static Set<String> pathMap;
    static {
        pathMap = Sets.newHashSet();
        pathMap.add("/qna/form.html");
        pathMap.add("/qna/show.html");
        pathMap.add("/user/form.html");
        pathMap.add("/user/login.html");
        pathMap.add("/user/login_failed.html");
        pathMap.add("/user/profile.html");
        pathMap.add("/index.html");
    }

    public static boolean isSupported(String path) {
        return pathMap.contains(path);
    }

    public static void responseStatic(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(PATH_TEMPLATES + httpRequest.getPath());
        httpResponse.protocol1_1();
        httpResponse.statusOk();
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.addHeader("Content-Length", Integer.toString(body.length));
        httpResponse.setBody(body);
    }
}
