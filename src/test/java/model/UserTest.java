package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserTest {
    public final static String TEST_ID = "testId";
    public final static String TEST_PW = "testPw";
    private final static String TEST_NAME = "testName";
    private final static String TEST_EMAIL = "testEmail@email.com";

    public static final User TEST_USER = new User(TEST_ID, TEST_PW, TEST_NAME, TEST_EMAIL);

    @DisplayName("유저 생성 시 비어있는 값이 하나라도 있으면 실패한다.")
    @ParameterizedTest
    @CsvSource(value = {":testPw:testName:testEmail", "testId::testName:testEmail",
        "testId:testPw::testEmail", "testId:testPw:testName:"}, delimiter = ':')
    void createWithNullTest(String id, String pw, String name, String email) {
        assertThatThrownBy(
            () -> new User(id, pw, name, email)
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void createTest() {
        assertThat(new User(TEST_ID, TEST_PW, TEST_NAME, TEST_EMAIL))
            .isEqualTo(TEST_USER);
    }

}
