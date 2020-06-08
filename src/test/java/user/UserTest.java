package user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author KingCjy
 */
public class UserTest {

    @Test
    public void verifyPasswordTest() {
        final String password = "1234";
        User user = new User("KingCjy" ,password, "최재용", "tlssycks@gmail.com");

        assertThat(user.verifyPassword(password)).isTrue();
    }
}
