package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParamsTest {
    @Test
    public void add() throws Exception {
        RequestParams params = new RequestParams();
        params.addQueryString("id=1");
        params.addBody("userId=javajigi&password=password");
        assertThat(params.getParameter("id")).isEqualTo("1");
        assertThat(params.getParameter("userId")).isEqualTo("javajigi");
        assertThat(params.getParameter("password")).isEqualTo("password");
    }
}
