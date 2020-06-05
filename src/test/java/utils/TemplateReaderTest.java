package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("resource/template 아래의 친구들을 읽는 객체 테스트")
class TemplateReaderTest {

    @DisplayName("path의 경로로 템플릿을 잘 읽어 오는지 테스트")
    @Test
    void test_read_template() throws Exception {
        final byte[] thisWillBeBody = TemplateReader.read("/index.html");
        assertThat(thisWillBeBody).isNotNull();
    }

    @DisplayName("없는 경우 화를 잘 내는지 테스트")
    @Test
    void test_read_invalid_template() throws Exception {
        assertThatThrownBy(() -> {
            final byte[] thisWillBeBody = TemplateReader.read("/아_배고프고_밥먹고_싶고_이것만_추가하고_먹자.html");
        }).isInstanceOf(FileNotFoundException.class);
    }
}