package http.Response;

import http.HttpHeaderName;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class JSResponse extends ResourceResponse {

    private static final String JS_RESOURCE_PREFIX = "/js";
    private static final String JS_CONTENT_TYPE = "application/javascript";

    @Override
    byte[] readContents(String path) {
        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), JS_CONTENT_TYPE);
        return readBytes(path);
    }

    @Override
    public boolean isMyResponse(String path) {
        return path.startsWith(JS_RESOURCE_PREFIX);
    }
}
