package web.servlet;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelAndViewTest {

    @Test
    public void initTest() {
        ModelAndView modelAndView = ModelAndView.from("test_view");
        modelAndView.addAttribute("message", "Hello World");

        assertThat(modelAndView.getViewName()).isEqualTo("test_view");
        assertThat(modelAndView.getModel()).contains(new AbstractMap.SimpleEntry("message", "Hello World"));
    }
}
