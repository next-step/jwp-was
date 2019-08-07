package webserver.http.dispatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resource.ResourceResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceDispatcher extends AbstractDispatcher<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ResourceDispatcher.class);

    private ResourceResolver resourceResolver;

    public ResourceDispatcher(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            byte[] resource = resourceResolver.getResource(request.getPath());
            response.writeHeader(request.getAccept(), resource.length);
            response.writeBody(resource);
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][RESOURCE] failed. {}", e);
        }
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        doGet(request, response);
    }
}
