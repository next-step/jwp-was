package http.Response;

import http.HttpHeaderName;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class CSSResponse extends ResourceResponse {

    private static final String CSS_RESOURCE_PREFIX = "/css";
    private static final String CSS_CONTENT_TYPE = "text/css";

    @Override
    byte[] readContents(String path) {
        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), CSS_CONTENT_TYPE);
        return readBytes(path);
    }

    @Override
    public boolean isMyResponse(String path) {
        return path.startsWith(CSS_RESOURCE_PREFIX);
    }
}
