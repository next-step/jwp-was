package db;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class DataBaseTest {
    @DisplayName("DataBase에 User 저장")
    @Test
    void addUser() throws UnsupportedEncodingException {
        //given
        User user = new User("palmseung", "password", "Seunghee", "palmseung@gmail.com");

        //when
        DataBase.addUser(user);
        User palmseung = DataBase.findUserById("palmseung");

        //then
        assertThat(palmseung).isEqualTo(user);
    }
}