package domain.user;

import db.DataBase;
import view.ViewCompiler;
import webserver.http.request.Request;
import webserver.handler.SecureHandler;
import webserver.http.response.Response;

public class UserListHandler extends SecureHandler {

    private final ViewCompiler viewCompiler;

    public UserListHandler(final ViewCompiler viewCompiler) {
        this.viewCompiler = viewCompiler;
    }

    @Override
    protected void secureHandle(final Request request,
                                final Response response) throws Exception {
        response.ok(viewCompiler.compile(request.getPath(), DataBase.findAll()));
    }
}
