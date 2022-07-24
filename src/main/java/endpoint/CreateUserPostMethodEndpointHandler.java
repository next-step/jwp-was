package endpoint;

import application.CreateUserCommand;
import application.CreateUserService;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;

public class CreateUserPostMethodEndpointHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT_PATH = "/user/create";

    public CreateUserPostMethodEndpointHandler() {
        super(new Endpoint(HttpMethod.POST, ENDPOINT_PATH));
    }

    @Override
    public void handle(HttpRequestMessage httpRequestMessage) {

        String userId = httpRequestMessage.getBodyValue("userId");
        String password = httpRequestMessage.getBodyValue("password");
        String name = httpRequestMessage.getBodyValue("name");
        String mail = httpRequestMessage.getBodyValue("email");

        CreateUserCommand createUserCommand = new CreateUserCommand(userId, password, name, mail);

        CreateUserService.createUser(createUserCommand);
    }
}
