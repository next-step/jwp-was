package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QueryStringsTest {

    @Test
    public void _test() {
        String source = "1,2,3,4";
        assertThat(source.split(",")).hasSize(4);
    }

    @Test
    public void splitTest() {
        String source = "1";
        assertThat(source.split(",")).hasSize(1);
        assertThat(source.split(",")).isEqualTo(new String[]{"1"});
    }

}
