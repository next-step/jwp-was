package webserver.http.response.view;

import utils.FileIoUtils;
import webserver.http.response.ContentType;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public class StaticResourceViewRenderer extends AbstractViewRenderer {

    private String prefix;

    public StaticResourceViewRenderer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    protected byte[] createResponseInfo(ModelAndView modelAndView, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String resourcePath = modelAndView.getOriginalViewName(prefix);
        byte[] body = FileIoUtils.loadFileFromClasspath("." + resourcePath);

        if (resourcePath.contains(".css")) {
            httpResponse.setContentType(ContentType.CSS);
        }
        httpResponse.setHttpStatus(HttpStatus.OK);

        return body;
    }
}
