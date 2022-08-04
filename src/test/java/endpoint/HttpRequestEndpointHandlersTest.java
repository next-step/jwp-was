package endpoint;

import endpoint.api.CreateUserGetMethodEndpointHandler;
import endpoint.api.CreateUserPostMethodEndpointHandler;
import endpoint.api.LoginEndpointHandler;
import endpoint.page.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestEndpointHandlersTest {

    @Test
    void create_and_not_empty_endpointPath() {
        HttpRequestEndpointHandlers httpRequestEndpointHandlers = new HttpRequestEndpointHandlers(List.of(
                HttpRequestEndpointHandler.NONE,
                new MainPageHandlerHomeHttpRequestHandler(),
                new CreateUserPageHandlerHomeHttpRequestHandler(),
                new CreateUserGetMethodEndpointHandler(),
                new CreateUserPostMethodEndpointHandler(),
                new LoginPageHandlerHomeHttpRequestHandler(),
                new LoginFailedPageHandlerHomeHttpRequestHandler(),
                new LoginEndpointHandler(),
                new UserListPageHandlerHomeHttpRequestHandler()
        ));

        assertThat(httpRequestEndpointHandlers).doesNotContain(HttpRequestEndpointHandler.NONE);
    }
}
