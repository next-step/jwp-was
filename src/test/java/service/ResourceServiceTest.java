package service;

import org.junit.jupiter.api.Test;
import webserver.request.RequestLine;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceServiceTest {

    @Test
    void canServe() {
        RequestLine requestLine = RequestLine.from("GET /user/login.html HTTP/1.1");

        ResourceService resourceService = new ResourceService();
        boolean b = resourceService.canServe(requestLine);

        assertThat(b).isTrue();
    }
}