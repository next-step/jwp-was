package webserver.template;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandleBarTemplateLoaderTest {

    @DisplayName("템플릿을 로드한다")
    @Test
    void load_template() throws IOException {

        User user = new User("javajigi", "password", "자바지기", "javajigi@gmail.com");

        final List<User> users = List.of(user);
        final Map<String, List<User>> param = Map.of("users", users);

        final String actual = HandleBarTemplateLoader.load("user/list", param);

        assertThat(actual).isEqualTo(
            "\n"
                + "<tr>\n"
                + "  <td>1</td>\n"
                + "  <td>자바지기</td>\n"
                + "</tr>\n"
                + "\n"
        );

    }
}
