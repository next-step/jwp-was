package db;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DataBaseTest {


    @DisplayName("해당 계정이 없는 경우 Null 반환한다")
    @Test
    void findById_notExistUser() {
        // given
        String noUser = "noUser";

        // when
        User userById = DataBase.findUserById(noUser);

        // then
        assertThat(userById).isNull();
    }
}