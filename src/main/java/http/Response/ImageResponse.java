package http.Response;

import http.HttpHeaderName;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ImageResponse extends ResourceResponse {

    private static final String IMAGES_RESOURCE_PREFIX = "/images";
    private static final String IMAGES_CONTENT_TYPE_PREFIX = "image/";

    @Override
    byte[] readContents(String path) {
        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), IMAGES_CONTENT_TYPE_PREFIX + getFileExtention(path));
        return readBytes(path);
    }

    @Override
    public boolean isMyResponse(String path) {
        return path.startsWith(IMAGES_RESOURCE_PREFIX);
    }
}
