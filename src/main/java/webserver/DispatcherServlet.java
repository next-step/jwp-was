package webserver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import utils.FileIoUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.servlet.Servlet;
import webserver.supporter.SupportApis;
import webserver.supporter.SupportTemplates;

/**
 * handlerMapping
 * handlerAdapting
 * viewResolving
 */
public enum DispatcherServlet {
    INSTANCE;

    private static final String PATH_TEMPLATES = "./templates";

    public void serve(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        String requestPath = httpRequest.getPath();

        if (SupportApis.isSupported(requestPath)) {
            Servlet servlet = SupportApis.getServlet(requestPath);
            servlet.serve(httpRequest, httpResponse);
            return;
        }

        if (SupportTemplates.isSupported(requestPath)) {
            responseStatic(httpRequest, httpResponse);
            return;
        }

        responseBodRequest400(httpResponse);
    }

    private void responseStatic(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, URISyntaxException {
        byte[] body = FileIoUtils.loadFileFromClasspath(PATH_TEMPLATES + httpRequest.getPath());
        httpResponse.protocol1_1();
        httpResponse.statusOk();
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.addHeader("Content-Length", Integer.toString(body.length));
        httpResponse.setBody(body);
    }

    private void responseBodRequest400(HttpResponse httpResponse) {
        final String badRequest = "Bod Request";
        byte[] body = badRequest.getBytes(StandardCharsets.UTF_8);
        httpResponse.protocol1_1();
        httpResponse.statusBadRequest();
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.addHeader("Content-Length", Integer.toString(body.length));
        httpResponse.setBody(body);
    }

}
