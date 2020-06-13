package http.Response;

/**
 * Created by iltaek on 2020/06/10 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public abstract class ResourceResponse extends HttpResponse {

    protected static final String STATIC_CONTENT_PATH = "./static";

    @Override
    String getFilePath(String path) {
        return STATIC_CONTENT_PATH + path;
    }
}
