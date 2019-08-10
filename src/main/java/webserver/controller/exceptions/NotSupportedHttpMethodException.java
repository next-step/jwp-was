package webserver.controller.exceptions;

import webserver.request.enums.HttpMethod;

/**
 * Created by hspark on 2019-08-11.
 */
public class NotSupportedHttpMethodException extends RuntimeException {

    public NotSupportedHttpMethodException(HttpMethod httpMethod) {
        super("Not Supported HttpMethod :" + httpMethod);
    }
}
