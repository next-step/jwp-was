package endpoint;

import org.junit.jupiter.api.Test;
import webserver.http.request.requestline.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

class EndpointTest {

    @Test
    void equals() {
        Endpoint endpoint = new Endpoint(HttpMethod.GET, "/create/user");
        Endpoint endpoint2 = new Endpoint(HttpMethod.GET, "/create/user");

        assertThat(endpoint).isEqualTo(endpoint2);
    }

    @Test
    void notEquals() {
        Endpoint endpoint = new Endpoint(HttpMethod.GET, "/create/user");
        Endpoint endpoint2 = new Endpoint(HttpMethod.POST, "/create/user");

        assertThat(endpoint).isNotEqualTo(endpoint2);
    }
}
