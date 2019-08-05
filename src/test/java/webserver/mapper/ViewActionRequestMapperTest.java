package webserver.mapper;

import actions.user.UserListAction;
import enums.HttpMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.resolvers.view.HandlebarViewResolver;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewActionRequestMapperTest {

    private static final HandlebarViewResolver handlebarViewResolver = HandlebarViewResolver.of("/templates", ".html");
    private static final ViewActionRequestMapper viewActionRequestMapper = new ViewActionRequestMapper(
            handlebarViewResolver
            , new ActionRequestMapper(
                    "/user/list"
                    , new UserListAction()
                    , new HashSet<>(Arrays.asList(new HttpMethod[]{HttpMethod.GET}))
            )
    );

    @DisplayName("ViewActionRequestMapperTest 매칭 테스트 : 매칭 케이스")
    @Test
    public void matchedPathTest(){
        assertThat(viewActionRequestMapper.isMatchedRequest(HttpMethod.GET, "/user/list"))
                .isTrue();
    }

    @DisplayName("ViewActionRequestMapperTest 매칭 테스트 : 비매칭 케이스")
    @Test
    public void notMatchedPathTest(){
        assertThat(viewActionRequestMapper.isMatchedRequest(HttpMethod.POST, "/user/list"))
                .isFalse();
        assertThat(viewActionRequestMapper.isMatchedRequest(HttpMethod.POST, "/user/list.html"))
                .isFalse();
    }
}
