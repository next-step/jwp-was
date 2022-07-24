package webserver.supporter;

import java.util.HashSet;
import java.util.Set;

public final class SupportTemplates {

    private SupportTemplates() { }

    static Set<String> pathMap;
    static {
        pathMap = new HashSet<>();
        pathMap.add("/qna/form.html");
        pathMap.add("/qna/show.html");
        pathMap.add("/user/form.html");
        pathMap.add("/user/list.html");
        pathMap.add("/user/login.html");
        pathMap.add("/user/login-failed.html");
        pathMap.add("/user/profile.html");
        pathMap.add("/index.html");
    }

    public static boolean isSupported(String path) {
        return pathMap.contains(path);
    }

}
