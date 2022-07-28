package endpoint.api;

import application.CreateUserCommand;
import application.CreateUserService;
import endpoint.Endpoint;
import endpoint.HttpRequestEndpointHandler;
import webserver.http.request.HttpRequestMessage;
import webserver.http.request.requestline.HttpMethod;
import webserver.http.request.requestline.HttpQueryStrings;
import webserver.http.response.HttpResponseMessage;

public class CreateUserGetMethodEndpointHandler extends HttpRequestEndpointHandler {
    private static final String ENDPOINT_PATH = "/user/create";

    public CreateUserGetMethodEndpointHandler() {
        super(new Endpoint(HttpMethod.GET, ENDPOINT_PATH));
    }

    @Override
    public HttpResponseMessage handle(HttpRequestMessage httpRequestMessage) {
        HttpQueryStrings httpQueryStrings = httpRequestMessage.getHttpQueryStrings();

        String userId = httpQueryStrings.getQueryValue("userId");
        String password = httpQueryStrings.getQueryValue("password");
        String name = httpQueryStrings.getQueryValue("name");
        String mail = httpQueryStrings.getQueryValue("email");

        CreateUserCommand createUserCommand = new CreateUserCommand(userId, password, name, mail);

        CreateUserService.createUser(createUserCommand);

        return HttpResponseMessage.justOk();
    }
}
