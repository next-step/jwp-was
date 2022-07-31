package webserver;

import java.io.IOException;
import java.net.URISyntaxException;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.servlet.Controller;
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
            Controller controller = SupportApis.getServlet(requestPath);
            controller.service(httpRequest, httpResponse);
            return;
        }

        if (SupportResources.isSupportedExtension(requestPath)) {
            SupportResources.serve(httpRequest, httpResponse);
            return;
        }

        httpResponse.notFound();
    }

}
