package web.servlet;

import org.junit.jupiter.api.Test;
import web.servlet.view.HandleBarsView;

public class HandleBarsViewResolverTest {
    @Test
    public void initTest() {
        HandleBarsViewResolver handleBarsViewResolver = new HandleBarsViewResolver();
        HandleBarsView handleBarsView = (HandleBarsView) handleBarsViewResolver.resolveViewName("index");
    }
}
