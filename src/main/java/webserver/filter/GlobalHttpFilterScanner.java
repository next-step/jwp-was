package webserver.filter;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GlobalHttpFilterScanner {
    private static final String GLOBAL_FILTER_PACKAGE_NAME = "webserver.filter";


    public static List<GlobalHttpFilter> scan() {
        Reflections reflections = new Reflections(GLOBAL_FILTER_PACKAGE_NAME);
        Set<Class<? extends GlobalHttpFilter>> globalHttpFilterClasses = reflections.getSubTypesOf(GlobalHttpFilter.class);

        List<GlobalHttpFilter> globalHttpFilterInstances;

        try {
            globalHttpFilterInstances = new ArrayList<>();

            addGlobalHttpFilterInstances(globalHttpFilterClasses, globalHttpFilterInstances);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ignored) {
            globalHttpFilterInstances = Collections.emptyList();
        }

        return globalHttpFilterInstances;
    }

    private static void addGlobalHttpFilterInstances(Set<Class<? extends GlobalHttpFilter>> globalHttpFilterClasses, List<GlobalHttpFilter> globalHttpFilterInstances) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (Class<? extends GlobalHttpFilter> globalHttpFilterClass : globalHttpFilterClasses) {
            GlobalHttpFilter globalHttpFilterInstance = globalHttpFilterClass.getConstructor().newInstance();

            globalHttpFilterInstances.add(globalHttpFilterInstance);
        }
    }

}
