package webserver.ui;

import model.dto.UserSaveRequest;
import org.springframework.http.HttpMethod;
import webserver.application.UserService;
import webserver.domain.HttpRequest;
import webserver.domain.HttpResponse;
import webserver.domain.HttpStatus;
import webserver.domain.Parameters;
import webserver.domain.RequestMapping;

public class UserController implements Controller {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/form.html", method = HttpMethod.GET)
    public HttpResponse getUsers(HttpRequest httpRequest) {

        return HttpResponse.templateResponse("/user/form.html");
    }

    @RequestMapping(value = "/user/create", method = {HttpMethod.GET})
    public HttpResponse createUser(HttpRequest httpRequest) {
        HttpResponse response = new HttpResponse(HttpStatus.CREATED, null, null);
        Parameters parameters = httpRequest.getRequestParameters();

        String userId = userService.createUser(UserSaveRequest.from(parameters));
        response.addHeader("Location", userId);

        return response;
    }


}
