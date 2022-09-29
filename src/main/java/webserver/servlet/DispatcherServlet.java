package webserver.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public enum DispatcherServlet {
    INSTANCE;

    public HttpResponse doService(HttpRequest httpRequest) {
        RequestMappingHandler requestMappingHandler = new RequestMappingHandler(ServletConfig.servlets());

        if (requestMappingHandler.match(httpRequest)) {
            return requestMappingHandler.doService(httpRequest);
        }
        return new ResourceServlet().service(httpRequest);
    }
}
