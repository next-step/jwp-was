package webserver.mapper;

import actions.user.UserCreateAction;
import enums.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionRequestMapperTest {

    private static final ActionRequestMapper userCreatectionRequestMapper = new ActionRequestMapper("/user/create"
            , new UserCreateAction()
            , new HashSet<>(Arrays.asList(new HttpMethod[]{HttpMethod.GET}))
    );

    @DisplayName("ActionRequestMapperTest 매칭 테스트 : 매칭 케이스")
    @Test
    public void matchedPathTest(){
        assertThat(userCreatectionRequestMapper.isMatchedRequest(HttpMethod.GET, "/user/create"))
                .isTrue();
    }

    @DisplayName("ActionRequestMapperTest 매칭 테스트 : 비매칭 케이스")
    @Test
    public void notMatchedPathTest(){
        assertThat(userCreatectionRequestMapper.isMatchedRequest(HttpMethod.POST, "/user/create.html"))
                .isFalse();
        assertThat(userCreatectionRequestMapper.isMatchedRequest(HttpMethod.POST, "/user/create"))
                .isFalse();
    }
}
