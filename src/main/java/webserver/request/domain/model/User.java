package webserver.request.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Builder
@EqualsAndHashCode
public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public static User create(Map<String, String> dataPairs) {
        return User.builder()
                .userId(dataPairs.get("userId"))
                .password(dataPairs.get("password"))
                .name(dataPairs.get("name"))
                .email(dataPairs.get("email"))
                .build();
    }
}
