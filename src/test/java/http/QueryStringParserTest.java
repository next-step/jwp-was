package http;

import http.request.QueryString;
import http.request.QueryStringParser;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class QueryStringParserTest {

    @Test
    void getParameter() {
        QueryString queryString = QueryStringParser.parse("userId=javajigi&password=password&name=JaeSung");
        assertThat(queryString.getParameter("userId")).isEqualTo("javajigi");
        assertThat(queryString.getParameter("password")).isEqualTo("password");
        assertThat(queryString.getParameter("name")).isEqualTo("JaeSung");
    }

    @Test
    void valid1() {
        QueryString queryString = QueryStringParser.parse("userId=javajigi&password=password&name=");
        assertThat(queryString.getParameter("userId")).isEqualTo("javajigi");
        assertThat(queryString.getParameter("password")).isEqualTo("password");
        assertThat(queryString.getParameter("name")).isEqualTo("");
    }


    @Test
    void valid2() {
        QueryString queryString = QueryStringParser.parse("userId=javajigi&password=password&name");
        assertThat(queryString.getParameter("userId")).isEqualTo("javajigi");
        assertThat(queryString.getParameter("password")).isEqualTo("password");
        assertThat(queryString.getParameter("name")).isEqualTo(null);
    }

    @Test
    void valid3() {
        QueryString queryString = QueryStringParser.parse("userId=javajigi&password=password&");
        assertThat(queryString.getParameter("userId")).isEqualTo("javajigi");
        assertThat(queryString.getParameter("password")).isEqualTo("password");
        assertThat(queryString.getParameter("name")).isEqualTo(null);
    }

    @Test
    void valid4() {
        QueryString queryString = QueryStringParser.parse("");
        assertThat(queryString.getParameter("userId")).isEqualTo(null);
        assertThat(queryString.getParameter("password")).isEqualTo(null);
        assertThat(queryString.getParameter("name")).isEqualTo(null);
    }

    @Test
    void valid5() {
        assertThatThrownBy(() -> {
            QueryString queryString = QueryStringParser.parse(null);
        }).isInstanceOf(InvalidParameterException.class);
    }
}
