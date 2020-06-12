package http.controller;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class JSController extends StaticController {

    private static final String JS_CONTENT_TYPE = "application/javascript";

    @Override
    protected String getContentType(String path) {
        return JS_CONTENT_TYPE;
    }
}
