package http.controller;

/**
 * Created by iltaek on 2020/06/12 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class ImageController extends StaticController {

    private static final String IMG_CONTENT_TYPE = "image/png";

    @Override
    protected String getContentType(String path) {
        return IMG_CONTENT_TYPE + getExtension(path);
    }
}
