package webserver.servlet;

import webserver.ModelAndView;
import webserver.response.ResponseHolder;
import webserver.request.RequestHolder;

public interface Servlet {

    String getName();

    ModelAndView service(RequestHolder requestHolder, ResponseHolder responseHolder);

}
