package webserver.mapper;

import enums.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourceRequestMapperTest {

    private static final ResourceRequestMapper resourceRequestMapper = new ResourceRequestMapper()
            .addResourceMapping("/css/.*", "./static" )
            .addResourceMapping("/fonts/.*", "./static" )
            .addResourceMapping("/images/.*", "./static" )
            .addResourceMapping("/js/.*", "./static" );

    @DisplayName("ResourceRequestMapper 매칭 테스트 : 매칭 케이스")
    @Test
    public void matchedPathTest(){
        assertThat(resourceRequestMapper.isMatchedRequest(HttpMethod.GET, "/css/styles.css"))
                .isTrue();
    }

    @DisplayName("ResourceRequestMapper 매칭 테스트 : 비매칭 케이스")
    @Test
    public void notMatchedPathTest(){
        assertThat(resourceRequestMapper.isMatchedRequest(HttpMethod.POST, "/css/styles.css"))
                .isFalse();
        assertThat(resourceRequestMapper.isMatchedRequest(HttpMethod.GET, "/logs/test.css"))
                .isFalse();
    }
}
