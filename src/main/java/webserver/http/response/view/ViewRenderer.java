package webserver.http.response.view;

import webserver.http.response.HttpResponse;

/**
 * @author : yusik
 * @date : 2019-08-06
 */
public interface ViewRenderer {
    void render(ModelAndView modelAndView, HttpResponse httpResponse);
}
