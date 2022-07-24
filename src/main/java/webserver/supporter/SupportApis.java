package webserver.supporter;

import java.util.HashSet;
import java.util.Set;

public class SupportApis {
    private SupportApis() { }

    static Set<String> apiMap;
    static {
        apiMap = new HashSet<>();
        apiMap.add("/user/create");
    }

    public static boolean isSupported(String path) {
        return apiMap.contains(path);
    }

}
