package http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("View 를 그릴때 사용될 model 을 모아놓은 클래스")
class ModelsTest {

    @Test
    @DisplayName("모델 추가")
    void addModels() {
        Models models = Models.init();

        models.addModel("key", "value");

        assertThat(models.getModels()).hasSize(1);
    }
}
