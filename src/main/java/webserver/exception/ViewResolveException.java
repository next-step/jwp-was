package webserver.exception;

import webserver.resolvers.Resolver;

public class ViewResolveException extends RuntimeException {
    public static final String DEFAULT_VIEW_RESOLVE_EXCEPTION_MSG = "View 직렬화에 실패했습니다. class=%s, message=%s\r\n";

    public ViewResolveException(Class<? extends Resolver> classType, String message) {
        super(String.format(DEFAULT_VIEW_RESOLVE_EXCEPTION_MSG, classType.getName(), message));
    }


}
