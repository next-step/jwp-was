package webserver.http.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.resource.ResourceResolver;

import java.io.IOException;
import java.net.URISyntaxException;

public class ResourceController extends AbstractController<HttpRequest, HttpResponse> {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    private ResourceResolver resourceResolver;

    public ResourceController(ResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        try {
            byte[] resource = resourceResolver.getResource(request.getPath());
            response.writeHeader(HttpStatus.OK, request.getAccept(), resource.length);
            response.writeBody(resource);
        } catch (IOException | URISyntaxException e) {
            logger.error("[PROCESS][RESOURCE] failed. {}", e);
        }
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        try {
            response.error(HttpStatus.METHOD_NOT_ALLOWED);
        } catch (IOException e) {
            logger.error("[PROCESS][RESOURCE] failed. {}", e);
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        if(request.isGetRequest()) {
            doGet(request, response);
        }

        if(request.isPostRequest()) {
            doPost(request, response);
        }
    }
}
