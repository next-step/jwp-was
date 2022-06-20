package webserver.http;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import webserver.http.request.Uri;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UriTest {

    @DisplayName("Path 를 가지는 Uri 생성")
    @Test
    void create() {
        String value = "/users";

        Uri uri = Uri.valueOf(value);

        assertThat(uri.getPath()).isEqualTo(value);
        assertThat(uri.getQueryString()).isNull();
    }

    @DisplayName("Path와 QueryString을 가지는 Uri 생성")
    @Test
    void createPathAndQueryString() {
        String path = "/Users";
        String queryString = "name=dean";
        String value = path + "?" + queryString;

        Uri uri = Uri.valueOf(value);

        assertThat(uri.getPath()).isEqualTo(path);
        assertThat(uri.getQueryString()).isEqualTo(queryString);
    }

    @DisplayName("문자열이 null이거나 공백인경우 Exception을 던진다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createNullAndEmptyString(String value) {
        ThrowableAssert.ThrowingCallable throwingCallable = () -> Uri.valueOf(value);
        assertThatIllegalArgumentException().isThrownBy(throwingCallable);
    }
}
