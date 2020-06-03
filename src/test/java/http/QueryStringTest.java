package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class QueryStringTest {

    @Test
    void getParameterTest() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getPrameter("userId")).isEqualTo("javajigi");
        assertThat(queryString.getPrameter("password")).isEqualTo("password");
        assertThat(queryString.getPrameter("name")).isEqualTo("JaeSung");
    }
}
