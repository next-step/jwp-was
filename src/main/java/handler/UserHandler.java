package handler;

import model.HttpRequestHeader;

public class UserHandler implements PathHandler {
    private static final String USER_PATH = "user";

    @Override
    public Boolean canHandling(HttpRequestHeader httpRequestHeader) {
        String[] resources = httpRequestHeader.getPath().split(RESOURCE_SEPARATOR);
        return resources[ROOT_RESOURCE_INDEX].equals(USER_PATH);
    }

    @Override
    public Byte[] Handle(HttpRequestHeader httpRequestHeader) {
        return new Byte[0];
    }
}
