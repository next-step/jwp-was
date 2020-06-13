package http.Response;

import http.HttpHeaderName;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class FontResponse extends ResourceResponse {

    private static final String FONT_RESOURCE_PREFIX = "/fonts";
    private static final String FONT_CONTENT_TYPE_PREFIX = "font/";

    @Override
    byte[] readContents(String path) {
        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), FONT_CONTENT_TYPE_PREFIX + getFileExtention(path));
        return readBytes(path);
    }

    @Override
    public boolean isMyResponse(String path) {
        return path.startsWith(FONT_RESOURCE_PREFIX);
    }
}
