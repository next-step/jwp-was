package http.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TemplateModel {

    public final Map<String, Object> model = new HashMap<>();

    public void add(String name, Object value) {
        model.put(name, value);
    }

    Map<String, Object> getModel() {
        return new HashMap<>(model);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemplateModel that = (TemplateModel) o;
        return Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }
}
