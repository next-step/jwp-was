package view;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

@Getter
public class ModelAndView {

    private final String path;
    private final Map model;

    public ModelAndView(final String path) {
        this.path = path;
        this.model = Collections.emptyMap();
    }

    public ModelAndView(final String path, final Map model) {
        this.path = path;
        this.model = model;
    }
}
