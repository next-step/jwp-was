package webserver.http.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentTypeTest {

    @Test
    public void jsContentType(){
        String fileExtension = "js";

        ContentType contentType = ContentType.from(fileExtension);

        assertEquals(ContentType.JS, contentType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"pa", "wa"})
    public void notSupportFileExtension(String input){

        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> ContentType.from(input));
    }
}
