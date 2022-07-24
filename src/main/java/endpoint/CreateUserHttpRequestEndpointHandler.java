package endpoint;

import application.CreateUserCommand;
import application.CreateUserService;
import webserver.http.HttpRequestMessage;
import webserver.http.request.requestline.HttpQueryStrings;

public class CreateUserHttpRequestEndpointHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT = "/user/create";

    public CreateUserHttpRequestEndpointHandler() {
        super(ENDPOINT);
    }

    @Override
    public void handle(HttpRequestMessage httpRequestMessage) {
        HttpQueryStrings httpQueryStrings = httpRequestMessage.getHttpQueryStrings();

        String userId = httpQueryStrings.getQueryValue("userId");
        String password = httpQueryStrings.getQueryValue("password");
        String name = httpQueryStrings.getQueryValue("name");
        String mail = httpQueryStrings.getQueryValue("email");

        CreateUserCommand createUserCommand = new CreateUserCommand(userId, password, name, mail);

        CreateUserService.createUser(createUserCommand);
    }
}
