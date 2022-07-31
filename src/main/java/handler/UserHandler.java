package handler;

import model.HttpRequestHeader;
import service.UserService;
import utils.FileIoUtils;

public class UserHandler implements PathHandler {
    private static final String USER_PATH = "user";
    private static final String CREATE_REQUEST_PATH = "/user/create";
    private static final UserService userService = new UserService();

    @Override
    public Boolean canHandling(HttpRequestHeader httpRequestHeader) {
        String[] resources = httpRequestHeader.getPath().split(RESOURCE_SEPARATOR);

        if (resources.length == 0) {
            return false;
        }

        return resources[ROOT_RESOURCE_INDEX].equals(USER_PATH);
    }

    @Override
    public byte[] Handle(HttpRequestHeader httpRequestHeader) {
        if (hasTemplateIdentifier(httpRequestHeader)) {

            return FileIoUtils.loadFileFromClasspath(httpRequestHeader.getPath());
        }

        if (httpRequestHeader.isEqualPath(CREATE_REQUEST_PATH)) {
            userService.createUser(httpRequestHeader.getQueryString());
        }

        return new byte[0];
    }
}
