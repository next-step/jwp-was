package handler;

import enums.ResourceIdentifier;
import model.request.HttpRequestMessage;
import model.response.HttpResponseMessage;

public interface PathHandler {
    String RESOURCE_SEPARATOR = "/";
    int ROOT_RESOURCE_INDEX = 1;

    Boolean canHandling(HttpRequestMessage httpRequestMessage);

    HttpResponseMessage Handle(HttpRequestMessage httpRequestMessage);

    default Boolean hasResourceIdentifier(String path) {

        return ResourceIdentifier.anyMatch(path);
    }
}
