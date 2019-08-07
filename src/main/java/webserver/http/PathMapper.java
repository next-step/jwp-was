package webserver.http;

import utils.HttpStringType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PathMapper {
    private final static Map<String, String> pathMapper;

    static {
        pathMapper = new HashMap<>();
        pathMapper.put("/", "/index.html");
        pathMapper.put("/user/create", "/index.html");
        pathMapper.put("/user/login/success", "/index.html");
        pathMapper.put("/user/login/fail", "/user/login_failed.html");
        pathMapper.put("/user/list/fail", "/user/login.html");
    }

    private static boolean isMatchPath(String path) {
        return pathMapper.containsKey(path);
    }

    public static String getFilePath(String path) {
        if (isMatchPath(path)) {
            return HttpStringType.FILE_PATH_PREFIX.getType() + pathMapper.get(path);
        }

        if (path.endsWith(".css") || path.endsWith(".js")) {
            return "./static" + path;
        }

        String filePath = HttpStringType.FILE_PATH_PREFIX.getType() + path;

        if (!Pattern.matches("^.+(.html)$", path)) {
            filePath = filePath + HttpStringType.FILE_PATH_EXT.getType();
        }

        return filePath;
    }

    public static String findByKey(String key) {
        return pathMapper.get(key);
    }
}