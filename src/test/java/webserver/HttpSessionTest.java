package webserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class HttpSessionTest {

    @Test
    void getId() {
        HttpSession httpSession = new HttpSession();

        assertThat(httpSession.getId()).isNotEqualTo(UUID.randomUUID().toString());
    }


    @Test
    @DisplayName("set,getAttribute")
    void set_get_Attribute() {
        HttpSession httpSession = new HttpSession();

        String testName = "testName";
        Object testValue = "testObject";
        httpSession.setAttribute(testName, testValue);

        assertThat(httpSession.getAttribute(testName)).isEqualTo(testValue);
    }

    @Test
    @DisplayName("removeAttribute")
    void remove_Attribute() {
        HttpSession httpSession = new HttpSession();

        String testName = "testName";
        Object testValue = "testObject";
        httpSession.setAttribute(testName, testValue);
        httpSession.removeAttribute(testName);

        assertThat(httpSession.getAttribute(testName)).isNotEqualTo(testValue);
    }

    @Test
    @DisplayName("invalidate")
    void invalidate() {
        HttpSession httpSession = new HttpSession();

        String testName = "testName";
        Object testValue = "testObject";
        httpSession.setAttribute(testName, testValue);

        String testName2 = "testName2";
        Object testValue2 = "testObject2";
        httpSession.setAttribute(testName2, testValue2);

        HttpSessionStorage httpSessionStorage = new HttpSessionStorage();
        httpSessionStorage.addSession(httpSession);

        httpSession.invalidate();

        Assertions.assertAll(
                () -> assertThat(httpSession.getAttribute(testName)).isNotEqualTo(testValue),
                () -> assertThat(httpSession.getAttribute(testName)).isEqualTo(null),
                () -> assertThat(httpSession.getAttribute(testName2)).isNotEqualTo(testValue2),
                () -> assertThat(httpSession.getAttribute(testName)).isEqualTo(null)

        );
    }
}
