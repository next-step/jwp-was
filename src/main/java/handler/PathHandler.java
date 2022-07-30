package handler;

import model.HttpRequestHeader;

public interface PathHandler {
    String RESOURCE_SEPARATOR = "/";
    String FILE_SEPARATOR = ".";
    int ROOT_RESOURCE_INDEX = 1;

    Boolean canHandling(HttpRequestHeader httpRequestHeader);

    Byte[] Handle(HttpRequestHeader httpRequestHeader);

    default Boolean hasFileSeparator(HttpRequestHeader httpRequestHeader) {

        return httpRequestHeader.getPath().contains(FILE_SEPARATOR);
    }
}
