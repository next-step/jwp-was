package webserver.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContentTypeTest {

    @Test
    void FORM_URL_ENCODED_ContentType() {
        assertThat(ContentType.X_WWW_FORM_URLENCODED.getType())
                .isEqualTo("application/x-www-form-urlencoded");

        HttpParameter to = ContentType.X_WWW_FORM_URLENCODED.to("userId=jun&password=password");
        assertThat(to.getParameter("userId")).isEqualTo("jun");
        assertThat(to.getParameter("password")).isEqualTo("password");
    }

}
