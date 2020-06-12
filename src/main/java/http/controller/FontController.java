package http.controller;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class FontController extends StaticController {

    private static final String FONT_CONTENT_TYPE = "font/";

    @Override
    protected String getContentType(String path) {
        return FONT_CONTENT_TYPE + getExtension(path);
    }
}
