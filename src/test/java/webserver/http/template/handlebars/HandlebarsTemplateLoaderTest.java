package webserver.http.template.handlebars;

import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlebarsTemplateLoaderTest {
    @Test
    void handlebars로_템플릿_페이지를_읽어온다() throws IOException {
        // given
        List<User> users = List.of(
                new User("tester1", "pw1", "테스터1", "tester1@gmail.com"),
                new User("tester2", "pw2", "테스터2", "tester2@gmail.com")
        );
        final Map<String, List<User>> params = Map.of("users", users);

        // when
        final HandlebarsTemplateLoader handlebarsTemplateLoader = new HandlebarsTemplateLoader();
        final String page = handlebarsTemplateLoader.load("handlebars_list", params);

        // then
        assertThat(page)
                .isEqualTo("\n" +
                        "<tr>\n" +
                        "    <th scope=\"row\">1</th>\n" +
                        "    <td>tester1</td>\n" +
                        "    <td>테스터1</td>\n" +
                        "    <td>tester1@gmail.com</td>\n" +
                        "</tr>\n" +
                        "\n" +
                        "<tr>\n" +
                        "    <th scope=\"row\">2</th>\n" +
                        "    <td>tester2</td>\n" +
                        "    <td>테스터2</td>\n" +
                        "    <td>tester2@gmail.com</td>\n" +
                        "</tr>\n\n"
                );
    }
}
