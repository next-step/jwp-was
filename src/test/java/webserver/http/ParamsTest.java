package webserver.http;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParamsTest {

    @Test
    void create() {
        Params actual = Params.from("userId=javajigi&password=pass");
        Map<String, String> expect = Map.of("userId", "javajigi", "password", "pass");

        assertThat(actual.getParams()).containsExactlyInAnyOrderEntriesOf(expect);
    }

}
