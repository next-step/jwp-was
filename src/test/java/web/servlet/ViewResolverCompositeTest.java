package web.servlet;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class ViewResolverCompositeTest {

    @Test
    public void initTest() {
        HandleBarsViewResolver handleBarsViewResolver = new HandleBarsViewResolver();
        Set<ViewResolver> viewResolvers = new LinkedHashSet<>();
        viewResolvers.add(handleBarsViewResolver);

        ViewResolverComposite viewResolverComposite = new ViewResolverComposite(viewResolvers);

        View view = viewResolverComposite.resolveViewName("index");
    }
}
