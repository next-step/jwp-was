package webserver.mapper;

import enums.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateRequestMapperTest {

    private static final TemplateRequestMapper templateRequestMapper = new TemplateRequestMapper("./templates");

    @DisplayName("TemplateRequestMapper 매칭 테스트 : 매칭 케이스")
    @Test
    public void matchedPathTest(){
        assertThat(templateRequestMapper.isMatchedRequest(HttpMethod.GET, "/index.html"))
                .isTrue();
    }

    @DisplayName("TemplateRequestMapper 매칭 테스트 : 비매칭 케이스")
    @Test
    public void notMatchedPathTest(){
        assertThat(templateRequestMapper.isMatchedRequest(HttpMethod.POST, "/options.html"))
                .isFalse();
        assertThat(templateRequestMapper.isMatchedRequest(HttpMethod.GET, "/index"))
                .isFalse();
    }
}
