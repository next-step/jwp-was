package webserver.http.response.view;

import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public class RedirectViewRenderer extends AbstractViewRenderer {

    private final String prefix;

    public RedirectViewRenderer(String prefix) {
        this.prefix = prefix;
    }

    @Override
    protected byte[] createResponseInfo(ModelAndView modelAndView, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.REDIRECT);
        httpResponse.addHeader("Location", modelAndView.getOriginalViewName(prefix));
        return EMPTY_BODY;
    }

}
