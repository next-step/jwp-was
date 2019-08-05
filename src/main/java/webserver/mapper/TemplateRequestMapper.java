package webserver.mapper;

import enums.HttpMethod;
import utils.FileIoUtils;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class TemplateRequestMapper implements RequestMapper{

    private final Map<String, URL> urlCache;
    private final Map<Pattern, String> resources;
    private final String templatePath;

    public TemplateRequestMapper(String templatePath) {
        this.urlCache = new HashMap<>();
        this.resources = new HashMap<>();
        this.templatePath = templatePath;
    }

    @Override
    public boolean isMatchedRequest(HttpMethod method, String requestUri) {

        if(HttpMethod.GET != method) {
            return false;
        }

        Optional<URL> resourceUrl = FileIoUtils.getResourceUrl(this.templatePath + requestUri);

        if(!resourceUrl.isPresent()) {
            return false;
        }

        this.urlCache.put(requestUri, resourceUrl.get());
        return true;

    }

    @Override
    public Object handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        URL resourceUrl = this.urlCache.get(httpRequest.getRequestURI());
        httpResponse.sendResource(resourceUrl);
        return null;
    }

    public TemplateRequestMapper addResourceMapping(String regex, String destination) {
        Pattern pattern = Pattern.compile(regex);
        this.resources.put(pattern, destination);
        return this;
    }

    private Optional<String> getMatchedDestination(String path) {

        return this.resources.entrySet().stream()
                .filter(entry -> entry.getKey().matcher(path).find())
                .map(entry -> entry.getValue() + path)
                .findFirst();

    }
}
