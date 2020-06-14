package http.controller;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class CSSController extends StaticController {

    private static final String CSS_CONTENT_TYPE = "text/css";

    @Override
    protected String getContentType(String path) {
        return CSS_CONTENT_TYPE;
    }
}
