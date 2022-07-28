package endpoint.api;

import application.CreateUserCommand;
import application.CreateUserService;
import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.response.HttpResponseMessage;

public class CreateUserPostMethodEndpointHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT_PATH = "/user/create";
    private static final String REDIRECT_ENDPOINT_PATH = "/index.html";

    public CreateUserPostMethodEndpointHandler() {
        super(new Endpoint(HttpMethod.POST, ENDPOINT_PATH));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {

        String userId = httpRequestMessage.getBodyValue("userId");
        String password = httpRequestMessage.getBodyValue("password");
        String name = httpRequestMessage.getBodyValue("name");
        String mail = httpRequestMessage.getBodyValue("email");

        CreateUserCommand createUserCommand = new CreateUserCommand(userId, password, name, mail);

        CreateUserService.createUser(createUserCommand);

        return HttpResponseMessage.redirect(REDIRECT_ENDPOINT_PATH);
    }
}
