package webserver;

import http.RequestMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.FileIoUtils;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceMapperTest {

    @DisplayName("요청 uri가 정적자원을 의미하는 경우 해당되는 정적자원을 byte 형태로 반환")
    @Test
    void get_resource_service() throws IOException, URISyntaxException {
        // given
        RequestMessage requestMessage = RequestMessage.of(Arrays.asList("GET /index.html HTTP/1.1"));

        byte[] bytes = FileIoUtils.loadFileFromClasspath("./templates/index.html");
        // when
        byte[] resource = ResourceMapper.getResource(requestMessage);
        // then
        assertThat(resource).isEqualTo(bytes);
    }

    @DisplayName("요청 uri에 매핑되는 정적자원이 없는 경우 Hello World를 byte 형태로 반환")
    @Test
    void get_nonexistent_resource() throws IOException, URISyntaxException {
        // given
        RequestMessage requestMessage = RequestMessage.of(Arrays.asList("GET /ho.html HTTP/1.1"));

        byte[] bytes = "Hello World".getBytes();
        // when
        byte[] resource = ResourceMapper.getResource(requestMessage);
        // then
        assertThat(resource).isEqualTo(bytes);
    }
}