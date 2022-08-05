package endpoint;

import endpoint.api.CreateUserGetMethodEndpointHandler;
import endpoint.api.CreateUserPostMethodEndpointHandler;
import endpoint.api.LoginEndpointHandler;
import endpoint.staticresource.StaticResourceRequestHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import webserver.http.request.requestline.HttpMethod;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestEndpointHandlerRegistryTest {

    @BeforeAll
    static void setUp() {
        RequestEndpointHandlerRegistry.initializeEndpointRegistry();
    }

    @ParameterizedTest
    @ArgumentsSource(GetEndPointHandlerTestArguments.class)
    void getEndpointHandler(HttpMethod httpMethod, String endPointPath, Class<? extends HttpRequestHandler> expectedHttpRequestHandlerClass) {
        Endpoint endpoint = new Endpoint(httpMethod, endPointPath);

        HttpRequestHandler httpRequestHandler = RequestEndpointHandlerRegistry.getEndpointHandler(endpoint);

        assertThat(httpRequestHandler.getClass()).isEqualTo(expectedHttpRequestHandlerClass);
    }

    @Test
    void getEndpointHandler_not_found_any_static_resource() {
        Endpoint endpoint = new Endpoint(HttpMethod.GET, "/nothing");

        HttpRequestHandler httpRequestHandler = RequestEndpointHandlerRegistry.getEndpointHandler(endpoint);

        assertThat(httpRequestHandler.getClass()).isEqualTo(StaticResourceRequestHandler.class);
    }

    private static class GetEndPointHandlerTestArguments implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(HttpMethod.GET, "/user/create", CreateUserGetMethodEndpointHandler.class),
                    Arguments.of(HttpMethod.POST, "/user/create", CreateUserPostMethodEndpointHandler.class),
                    Arguments.of(HttpMethod.POST, "/user/login", LoginEndpointHandler.class)
            );
        }
    }
}
