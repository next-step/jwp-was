package webserver;

import java.io.IOException;
import java.net.URISyntaxException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.servlet.Servlet;
import webserver.supporter.SupportApis;
import webserver.supporter.SupportResources;

/**
 * handlerMapping
 * handlerAdapting
 * viewResolving
 */
public enum DispatcherServlet {
    INSTANCE;

    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String requestPath = httpRequest.getPath();

        if (SupportApis.isSupported(requestPath)) {
            Servlet servlet = SupportApis.getServlet(requestPath);
            servlet.serve(httpRequest, httpResponse);
            return;
        }

        if (SupportResources.isSupportedExtension(requestPath)) {
            SupportResources.serve(httpRequest, httpResponse);
            return;
        }

        httpResponse.notFound();
    }

}
