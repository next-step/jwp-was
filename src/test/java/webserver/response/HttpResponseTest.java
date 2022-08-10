package webserver.response;

import enums.HttpStatusCode;
import java.nio.charset.StandardCharsets;
import model.User;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.Fixtures;
import utils.JsonUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseTest {
    @Test
    @DisplayName("Object가 주어졌을 때 byte array로 변환된다")
    void test() throws Exception {
        User user = Fixtures.createUser();
        String userJsonString = JsonUtils.convertObjectToJsonString(user);

        HttpResponse result = new HttpResponse(HttpStatusCode.OK, user);

        assertEquals(result.getStatusCode(), HttpStatusCode.OK);
        assertArrayEquals(result.getBody(), userJsonString.getBytes(StandardCharsets.UTF_8));
    }
}
