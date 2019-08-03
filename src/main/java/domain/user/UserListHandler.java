package domain.user;

import view.ViewCompiler;
import webserver.http.response.HttpResponse;
import webserver.handler.SecureHandler;
import webserver.http.request.HttpRequest;

public class UserListHandler extends SecureHandler {

    private final ViewCompiler viewCompiler;

    public UserListHandler(final ViewCompiler viewCompiler) {
        this.viewCompiler = viewCompiler;
    }

    @Override
    protected void secureHandle(final HttpRequest request,
                                final HttpResponse response) throws Exception {
        response.ok(viewCompiler.compile(request.getPath(), null));
    }
}
