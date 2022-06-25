package controller;

import org.junit.jupiter.api.Test;
import utils.IOUtils;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceServiceTest {

    @Test
    void canServe() throws IOException {
        String reqeustStr = "GET /user/login.html HTTP/1.1";
        HttpRequest httpRequest = HttpRequest.from(IOUtils.toBufferedReader(reqeustStr));

        ResourceController resourceService = new ResourceController();
        boolean canServe = resourceService.canServe(httpRequest);

        assertThat(canServe).isTrue();
    }

    @Test
    void get_ResourceException() throws IOException {
        String reqeustStr = "GET /user/login.html HTTP/1.1";
        HttpRequest httpRequest = HttpRequest.from(IOUtils.toBufferedReader(reqeustStr));

        ResourceController resourceService = new ResourceController();
        HttpResponse httpResponse = resourceService.doGet(httpRequest);

        assertThat(httpResponse.getCode()).isEqualTo("202");
    }
}