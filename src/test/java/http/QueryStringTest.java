package http;

import model.User;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void isContainValidParameter() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=JaeSung&email=kjs4395@naver.com");
        QueryString notValidQueryString = new QueryString("userId=javajigi&password=password");
        assertTrue(queryString.isContainAllField(User.class));
        assertFalse(notValidQueryString.isContainAllField(User.class));
    }

}
