package webserver;

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

        assertThat(httpPath.getHttpQueryStrings().get(0)).isEqualTo(new HttpQueryString("queryName=queryValue"));
        assertThat(httpPath.getHttpQueryStrings().get(1)).isEqualTo(new HttpQueryString("queryName2=queryValue2"));
        assertThat(httpPath.getHttpQueryStrings().getFullQueryString()).isEqualTo("?queryName=queryValue&queryName2=queryValue2");
    }

}
