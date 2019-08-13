package webserver.http.request.controller;

import webserver.http.common.session.HttpSession;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.view.ModelAndView;
import webserver.http.response.view.ViewType;

/**
 * @author : yusik
 * @date : 2019-08-13
 */
public class LogoutController extends AbstractController {

    public LogoutController(boolean allowAll) {
        super(allowAll);
    }

    @Override
    public ModelAndView getProcess(HttpRequest httpRequest, HttpResponse httpResponse) {
        HttpSession httpSession = httpRequest.getHttpSession();
        httpSession.invalidate();
        return new ModelAndView(ViewType.REDIRECT.getPrefix() + "/index.html");
    }
}
