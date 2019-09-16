package webserver.http.mapping;

import com.google.common.collect.Sets;
import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.Set;

public class ResourceMapping {

    private static final Set<String> resourceMapping;

    static {
        resourceMapping = Sets.newHashSet();
        resourceMapping.add("/js");
        resourceMapping.add("/css");
        resourceMapping.add("/fonts");
        resourceMapping.add("/images");
    }

    public static boolean support(String requestUri) {
        for (String resource : resourceMapping) {
            if (requestUri.startsWith(resource)) {
                return true;
            }
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
