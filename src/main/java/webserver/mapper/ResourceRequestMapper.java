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

public class ResourceRequestMapper implements RequestMapper{

    private final Map<String, URL> urlCache;
    private final Map<Pattern, String> resources;

    public ResourceRequestMapper() {
        this.urlCache = new HashMap<>();
        this.resources = new HashMap<>();
    }

    @Override
    public boolean isMatchedRequest(HttpMethod method, String requestUri) {

        if(HttpMethod.GET != method) {
            return false;
        }

        Optional<String> destinationPath = getMatchedDestination(requestUri);

        if(!destinationPath.isPresent()) {
            return false;
        }

        Optional<URL> resourceUrl = FileIoUtils.getResourceUrl(destinationPath.get());

        if(!resourceUrl.isPresent()) {
            return false;
        }

        this.urlCache.put(requestUri, resourceUrl.get());
        return true;

    }

    @Override
    public Void handle(HttpRequest httpRequest, HttpResponse httpResponse) {
        URL resourceUrl = this.urlCache.get(httpRequest.getRequestURI());
        httpResponse.sendResource(resourceUrl);
        return null;
    }

    public ResourceRequestMapper addResourceMapping(String regex, String destination) {
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
