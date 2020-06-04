package http;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QueryStringTest {

    @Test
    void invalidArgument() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");
        assertThatThrownBy(() -> queryString.getParameter("invalid"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void invalidString() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name");
        assertThatThrownBy(() -> queryString.getParameter("name"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void getParameter() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getParameter("userId")).isEqualTo("javajigi");
        assertThat(queryString.getParameter("password")).isEqualTo("password");
        assertThat(queryString.getParameter("name")).isEqualTo("JaeSung");
    }

}
