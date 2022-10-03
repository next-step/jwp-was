package webserver.servlet;

import webserver.http.MediaType;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class ResourceServlet extends HttpServlet {

    private static final String VIEW_RESOURCES_PATH = "./templates";
    private static final String STATIC_RESOURCES_PATH = "./static";

    @Override
    protected HttpResponse doGet(HttpRequest request) {
        String fileName = request.getRequestLine().getPathValue();
        String staticResourceExtension = request.getRequestLine().getStaticResourceExtension();

        MediaType mediaType = MediaType.from(staticResourceExtension);
        String resourcePath = getResourcePath(mediaType);

        return HttpResponse.forward(mediaType, resourcePath, fileName);
    }

    private String getResourcePath(MediaType mediaType) {
        if (mediaType.isHtml() || mediaType.isIco()) {
            return VIEW_RESOURCES_PATH;
        }
        return STATIC_RESOURCES_PATH;
    }
}
