package http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Created by iltaek on 2020/06/03 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public class URLTest {

    @Test
    void createURLTest() {
        URL url = new URL("/users?userId=javajigi&password=password&name=JaeSung");
        assertThat(url).isEqualTo(new URL("/users?userId=javajigi&password=password&name=JaeSung"));
    }
}
