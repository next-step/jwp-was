package webserver.http.mapping;

import utils.HttpStringType;
import utils.HttpStringUtils;

public class ResourceMapping {
    public static boolean support(String requestUri) {
        if (requestUri.startsWith("/js")) {
            return true;
        }

        if (requestUri.startsWith("/css")) {
            return true;
        }

        if (requestUri.startsWith("/fonts")) {
            return true;
        }

        if (requestUri.startsWith("/images")) {
            return true;
        }

        return false;
    }

    public static String getFilePath(String requestUri) {
        return HttpStringType.STATIC_PREFIX.getType() + requestUri;
    }

    public static String getContentType(String requestUri) {
        return "text/" + HttpStringUtils.splitAndFindByIndex(requestUri, "/", 1);
    }
}
