package utils;

import com.google.common.collect.Maps;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class MapUtilTest {
    @ParameterizedTest
    @NullAndEmptySource
    void buildParameters_forNullAndEmpty(String queryString) {
        Map<String, String> map = MapUtil.buildParameters(queryString);

        assertThat(map).isNotNull();
        assertThat(map.get("userId")).isNull();
        assertThat(map.get("password")).isNull();
        assertThat(map.get("name")).isNull();
        assertThat(map.get("email")).isNull();
    }

    @Test
    void buildParameters() {
        String queryString = "userId=ninjasul&password=1234&name=%EB%B0%95%EB%8F%99%EC%97%BD&email=ninjasul%40gmail.com";
        Map<String, String> map = MapUtil.buildParameters(queryString);

        assertThat(map).isNotNull();
        assertThat(map.get("userId")).isEqualTo("ninjasul");
        assertThat(map.get("password")).isEqualTo("1234");
        assertThat(map.get("name")).isEqualTo("박동엽");
        assertThat(map.get("email")).isEqualTo("ninjasul@gmail.com");
    }

    @Test
    void convertToObject_forNull() {
        User user = MapUtil.convertToObject(null, User.class);

        assertThat(user).isNull();
    }

    @Test
    void convertToObject_forEmpty() {
        Map<String, String> userMap = Maps.newHashMap();

        User user = MapUtil.convertToObject(userMap, User.class);

        assertThat(user).isNotNull();
        assertThat(user.getUserId()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getPassword()).isNull();
        assertThat(user.getPassword()).isNull();
    }

    @Test
    void convertToObject() {
        Map<String, String> userMap = Maps.newHashMap();
        userMap.put("userId", "javajigi");
        userMap.put("password", "password");
        userMap.put("name", "JaeSung");
        userMap.put("email", "javajigi@gmail.com");

        User user = MapUtil.convertToObject(userMap, User.class);

        assertThat(user.getUserId()).isEqualTo(userMap.get("userId"));
        assertThat(user.getPassword()).isEqualTo(userMap.get("password"));
        assertThat(user.getName()).isEqualTo(userMap.get("name"));
        assertThat(user.getEmail()).isEqualTo(userMap.get("email"));
    }
}