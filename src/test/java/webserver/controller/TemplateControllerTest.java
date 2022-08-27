package webserver.controller;

import http.httprequest.HttpRequest;
import http.httprequest.requestbody.RequestBody;
import http.httprequest.requestheader.RequestHeader;
import http.httprequest.requestline.HttpMethod;
import http.httprequest.requestline.Path;
import http.httprequest.requestline.Protocol;
import http.httprequest.requestline.ProtocolType;
import http.httprequest.requestline.RequestLine;
import http.httprequest.requestline.RequestParams;
import http.httprequest.requestline.Version;
import http.httpresponse.HttpResponse;
import http.httpresponse.HttpStatusCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TemplateControllerTest {
    @Test
    @DisplayName("경로가 잘 설정됐는지 확인")
    void getPath() {
        Controller controller = new TemplateController();
        assertThat(controller.getPath()).isEqualTo("./templates");
    }

    @Test
    @DisplayName("execute")
    void execute() throws IOException, URISyntaxException {
        Controller controller = new TemplateController();
        HttpRequest httpRequest = new HttpRequest(
                new RequestLine(
                        HttpMethod.GET,
                        new Path("/index.html", new RequestParams(Collections.emptyMap())),
                        new Protocol(ProtocolType.HTTP, new Version("1.1"))
                ),
                RequestHeader.from(Collections.emptyList()),
                RequestBody.empty()
        );

        HttpResponse httpResponse = controller.serve(httpRequest);

        assertAll(
                () -> assertThat(httpResponse.getHttpStatusCode()).isEqualTo(HttpStatusCode.OK),
                () -> assertThat(httpResponse.getResponseHeader().getHeaderEntries().contains(Map.entry("Content-Type", "text/css;charset=utf-8")))
        );
    }
}