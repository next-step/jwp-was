package webserver.request.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.domain.request.QueryString;

public class UserTest {

    @Test
    @DisplayName("User 생성 테스트")
    public void t() {
        QueryString queryString = new QueryString("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");

        User user1 = User.create(queryString.getDataPairs());

        User user2 = User.builder()
                .userId("javajigi")
                .password("password")
                .name("%EB%B0%95%EC%9E%AC%EC%84%B1")
                .email("javajigi%40slipp.net")
                .build();

        Assertions.assertThat(user1).isEqualTo(user2);
    }
}
