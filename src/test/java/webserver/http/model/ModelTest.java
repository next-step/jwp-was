package webserver.http.model;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ModelTest {

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