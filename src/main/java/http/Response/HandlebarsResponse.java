package http.Response;

import http.HttpHeaderName;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class HandlebarsResponse extends HTMLResponse {

    private static final String HANDLEBAR_PATH = "/user/list";

    @Override
    byte[] readContents(String bodyString) {
        addHeaders(HttpHeaderName.CONTENT_TYPE.toString(), HTML_CONTENT_TYPE);
        return bodyString.getBytes();
    }

    @Override
    public boolean isMyResponse(String path) {
        return path.equals(HANDLEBAR_PATH);
    }
}
