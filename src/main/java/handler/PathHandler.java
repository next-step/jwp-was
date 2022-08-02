package handler;

import enums.ResourceIdentifier;
import model.request.HttpRequestHeader;
import model.response.HttpResponseHeader;

public interface PathHandler {
    String RESOURCE_SEPARATOR = "/";
    int ROOT_RESOURCE_INDEX = 1;

    Boolean canHandling(HttpRequestHeader httpRequestHeader);

    HttpResponseHeader Handle(HttpRequestHeader httpRequestHeader);

    default Boolean hasResourceIdentifier(String path) {

        return ResourceIdentifier.anyMatch(path);
    }
}
