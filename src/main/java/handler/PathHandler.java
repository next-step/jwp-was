package handler;

import model.request.HttpRequestHeader;

public interface PathHandler {
    String RESOURCE_SEPARATOR = "/";
    String TEMPLATE_IDENTIFIER = ".html";
    int ROOT_RESOURCE_INDEX = 1;

    Boolean canHandling(HttpRequestHeader httpRequestHeader);

    byte[] Handle(HttpRequestHeader httpRequestHeader);

    default Boolean hasTemplateIdentifier(HttpRequestHeader httpRequestHeader) {

        return httpRequestHeader.getPath().contains(TEMPLATE_IDENTIFIER);
    }
}
