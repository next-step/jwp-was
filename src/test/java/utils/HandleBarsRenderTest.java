package utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import model.User;
import org.junit.jupiter.api.Test;

class HandleBarsRenderTest {

  @Test
  void userListRender() {
    try {
      TemplateLoader loader = new ClassPathTemplateLoader();
      loader.setPrefix("/templates");
      loader.setSuffix(".html");
      Handlebars handlebars = new Handlebars(loader);

      Template template = handlebars.compile("/user/list");

      DataBase.addUser(new User("leechangjun", "password", "이창준", "leechang0423@naver.com"));
      DataBase.addUser(new User("leechangjun1", "password1", "이창준1", "leechang0423@naver.com1"));
      Collection<User> users = DataBase.findAll();
      String list = template.apply(Collections.singletonMap("users", users));

      assertThat(list.contains("leechangjun")).isTrue();
      assertThat(list.contains("leechangjun1")).isTrue();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}