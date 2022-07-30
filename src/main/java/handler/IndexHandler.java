package handler;

import model.HttpRequestHeader;

public class IndexHandler implements PathHandler {
    @Override
    public Boolean canHandling(HttpRequestHeader httpRequestHeader) {
        String[] resources = httpRequestHeader.getPath().split(RESOURCE_SEPARATOR);
        return resources[ROOT_RESOURCE_INDEX].contains(FILE_SEPARATOR);
    }

    @Override
    public Byte[] Handle(HttpRequestHeader httpRequestHeader) {
        return new Byte[0];
    }
}
