package http.Response;

import http.HttpHeaderName;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HTMLResponse extends HttpResponse {

    protected static final String TEMPLATE_CONTENT_PATH = "./templates";
    protected static final String HTML_CONTENT_TYPE = "text/html;charset=utf-8";

    @Override
    byte[] readContents(String path) {
        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), HTML_CONTENT_TYPE);
        return readBytes(path);
    }

    @Override
    String getFilePath(String path) {
        return TEMPLATE_CONTENT_PATH + path;
    }

    @Override
    public boolean isMyResponse(String path) {
        return true;
    }
}
