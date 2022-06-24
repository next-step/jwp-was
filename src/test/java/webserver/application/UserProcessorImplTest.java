package webserver.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import webserver.domain.user.User;
import webserver.domain.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserProcessorImplTest {

    private UserProcessor sut;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        sut = new UserProcessorImpl(userRepository);
    }

    @Test
    void isValidUser() {
        String userId = "javajigi";
        String password = "1234";
        given(userRepository.findUserById(userId)).willReturn(new User(userId,password, "자바지기", ""));

        boolean actual = sut.isValidUser(userId, password);

        assertThat(actual).isTrue();
    }
}
