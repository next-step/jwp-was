package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResourcePathMakerTest {
    @Test
    void makeFilePathTest() {
        PathAndQueryString pathAndQueryString = new PathAndQueryString("/index.html", new QueryString(""));

        assertThat(ResourcePathMaker.makeTemplatePath(pathAndQueryString.getPath())).isEqualTo("./template/index.html");
    }
}
