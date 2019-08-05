package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ModelAndView;
import webserver.View;
import webserver.provider.ServiceInstanceProvider;
import webserver.request.RequestHolder;
import webserver.servlet.Servlet;

import java.util.Map;

public class ServletResponseHandler implements ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServletResponseHandler.class);

    private Map<String, Servlet> servletMappings;
    private View view;

    public ServletResponseHandler() {
        servletMappings = ServiceInstanceProvider.getDefaultServlets();
        view = new View();
    }

    @Override
    public boolean supports(RequestHolder requestHolder) {
        return servletMappings.containsKey(requestHolder.getPath());
    }

    @Override
    public void handle(RequestHolder requestHolder, ResponseHolder responseHolder) {
        Servlet servlet = servletMappings.get(requestHolder.getPath());
        ModelAndView mav = servlet.service(requestHolder, responseHolder);
        view.render(requestHolder, responseHolder, mav);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
