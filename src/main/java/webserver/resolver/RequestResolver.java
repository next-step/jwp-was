package webserver.resolver;

import webserver.request.HttpRequest;
import webserver.resolver.controller.ControllerResolver;
import webserver.resolver.resource.ResourceResolver;
import webserver.response.HttpResponse;

/**
 * Created by hspark on 2019-08-05.
 */
public class RequestResolver {
    private Resolver resourceResolver = new ResourceResolver();
    private Resolver controllerResolver = new ControllerResolver();



    public HttpResponse resolve(HttpRequest httpRequest) {
        if (httpRequest.getRequestLine().getRequestUrl().isFile()) {
            return resourceResolver.resolve(httpRequest);
        }
        return controllerResolver.resolve(httpRequest);
    }

}
