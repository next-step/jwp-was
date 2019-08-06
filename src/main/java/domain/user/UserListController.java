package domain.user;

import db.DataBase;
import view.ViewCompiler;
import webserver.controller.AbstractController;
import webserver.http.request.Request;
import webserver.http.response.Response;

public class UserListController extends AbstractController {

    public static final String PATH = "/user/list";

    private final ViewCompiler viewCompiler;

    public UserListController(final ViewCompiler viewCompiler) {
        this.viewCompiler = viewCompiler;
    }

    @Override
    protected void doGet(final Request request,
                         final Response response) throws Exception {
        response.ok(viewCompiler.compile(request.getPath(), DataBase.findAll()));
    }
}
