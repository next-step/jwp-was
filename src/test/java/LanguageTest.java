import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LanguageTest {

    @Test
    public void splitTest() {
        String cookieString = "userId=123";

        String[] values = cookieString.split("; ");

        assertThat(values.length).isEqualTo(1);
        assertThat(values[0]).isEqualTo(cookieString);
    }
}
