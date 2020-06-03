package http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlParamTest {

    @Test
    void create(){
        UrlParam urlParam = new UrlParam("/users?userId=javajigi&password=password&name=JaeSung");

        assertThat(urlParam.getUrl()).isEqualTo("/users");
        assertThat(urlParam.getParameters()).isEqualTo("userId=javajigi&password=password&name=JaeSung");
//        assertThat(urlParam).isEqualTo(new UrlParam("/users", "userId=javajigi&password=password&name=JaeSung"));
    }

}
