package handler;

import model.request.HttpRequestHeader;
import utils.FileIoUtils;

public class IndexHandler implements PathHandler {
    @Override
    public Boolean canHandling(HttpRequestHeader httpRequestHeader) {
        String[] resources = httpRequestHeader.getPath().split(RESOURCE_SEPARATOR);

        if (resources.length == 0) {
            return false;
        }

        return resources[ROOT_RESOURCE_INDEX].contains(TEMPLATE_IDENTIFIER);
    }

    @Override
    public byte[] Handle(HttpRequestHeader httpRequestHeader) {
        if (hasTemplateIdentifier(httpRequestHeader)) {

            return FileIoUtils.loadFileFromClasspath(httpRequestHeader.getPath());
        }

        return new byte[0];
    }
}
