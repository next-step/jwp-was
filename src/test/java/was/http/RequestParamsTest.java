package was.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParamsTest {
    @Test
    public void add() throws Exception {
        DefaultRequestParams params = new DefaultRequestParams("id=1", "userId=javajigi&password=password");
        assertThat(params.getParameter("id")).isEqualTo("1");
        assertThat(params.getParameter("userId")).isEqualTo("javajigi");
        assertThat(params.getParameter("password")).isEqualTo("password");
    }
}
