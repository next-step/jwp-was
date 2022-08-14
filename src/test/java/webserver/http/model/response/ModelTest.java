package webserver.http.model.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ModelTest {

    @DisplayName("model 안에 데이터를 설정하면 정상적으로 model 객체가 생성되는지 확인")
    @Test
    void construct() {
        String path = "index.html";
        Map<String, Object> modelMap = new LinkedHashMap<>();
        modelMap.put("data", "data");
        Model model = new Model(path, modelMap);

        assertThat(model.getPath()).isEqualTo("index.html");
        assertThat(model.getModelMap().get("data")).isEqualTo("data");
    }
}