package webserver.http.request.requestline;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {

    @Test
    void getPathComponents() {
        HttpPath httpPath = new HttpPath("/users/1/2/3/4");

        assertThat(httpPath.getPathComponents()).containsExactly("users", "1", "2", "3", "4");
    }

    @Test
    void getPathComponentsImmutable() {
        HttpPath httpPath = new HttpPath("/users/1/2/3/4");
        List<String> httpPathComponents = httpPath.getPathComponents();
        httpPathComponents.add("5");
        httpPathComponents.add("6");

        assertThat(httpPath.getPathComponents()).isNotEqualTo(httpPathComponents);
    }

    @Test
    void getFullPath() {
        HttpPath httpPath = new HttpPath("/users/1/2/3/4");

        assertThat(httpPath.getFullPath()).isEqualTo("/users/1/2/3/4");
    }


    @Test
    void getFullPathWithQueryStrings() {
        HttpPath httpPath = new HttpPath("/users/1/2/3/4?queryName=queryValue&queryName2=queryValue2");

        assertThat(httpPath.getHttpQueryStrings().get(0)).isEqualTo(HttpQueryString.from("queryName=queryValue"));
        assertThat(httpPath.getHttpQueryStrings().get(1)).isEqualTo(HttpQueryString.from("queryName2=queryValue2"));
        assertThat(httpPath.getHttpQueryStrings().getFullQueryString()).isEqualTo("?queryName=queryValue&queryName2=queryValue2");
    }

    @Test
    void getFullPathWithQueryStrings_urlDecode() {
        HttpPath httpPath = new HttpPath("/create?userId=thxwelchs&password=password&name=%EC%9D%B4%ED%83%9C%ED%9B%88&email=thxwelchs%40gmail.com");

        assertThat(httpPath.getHttpQueryStrings().get(0)).isEqualTo(HttpQueryString.from("userId=thxwelchs"));
        assertThat(httpPath.getHttpQueryStrings().get(1)).isEqualTo(HttpQueryString.from("password=password"));
        assertThat(httpPath.getHttpQueryStrings().get(2)).isEqualTo(HttpQueryString.from("name=이태훈"));
        assertThat(httpPath.getHttpQueryStrings().get(3)).isEqualTo(HttpQueryString.from("email=thxwelchs@gmail.com"));
        assertThat(httpPath.getHttpQueryStrings().getFullQueryString()).isEqualTo("?userId=thxwelchs&password=password&name=이태훈&email=thxwelchs@gmail.com");
    }

}
