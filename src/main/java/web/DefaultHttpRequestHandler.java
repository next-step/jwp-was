package web;

import http.HttpRequest;
import http.HttpResponse;
import web.method.InvocableHandlerMethod;
import web.servlet.ModelAndView;
import web.servlet.View;
import web.servlet.ViewResolver;

import java.io.IOException;

public class DefaultHttpRequestHandler implements HttpRequestHandler {

    private AnnotationHandlerMapping annotationHandlerMapping;
    private ViewResolver viewResolver;

    public DefaultHttpRequestHandler(AnnotationHandlerMapping annotationHandlerMapping, ViewResolver viewResolver) {
        this.annotationHandlerMapping = annotationHandlerMapping;
        this.viewResolver = viewResolver;
    }

    @Override
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        InvocableHandlerMethod handlerMethod = annotationHandlerMapping.getHandler(httpRequest);

        if(handlerMethod != null) {

            Object object = handlerMethod.invoke(httpRequest, httpResponse);

            if(object instanceof ModelAndView) {
                render((ModelAndView) object, httpRequest, httpResponse);
                return;
            }
        }
    }

    private void render(ModelAndView modelAndView, HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        View view = this.viewResolver.resolveViewName(modelAndView.getViewName());
        view.render(modelAndView.getModel(), httpRequest, httpResponse);
        httpResponse.responseDone();
    }
}
