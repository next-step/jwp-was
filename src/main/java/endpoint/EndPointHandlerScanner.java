package endpoint;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class EndPointHandlerScanner {
    private static final String ENDPOINT_PACKAGE_NAME = "endpoint";

    private EndPointHandlerScanner() {
    }

    public static HttpRequestEndpointHandlers scan() {

        Reflections reflections = new Reflections(ENDPOINT_PACKAGE_NAME);
        Set<Class<? extends HttpRequestEndpointHandler>> endpointHandlerClasses = reflections.getSubTypesOf(HttpRequestEndpointHandler.class);

        List<HttpRequestEndpointHandler> endpointHandlers;

        try {
            endpointHandlers = createEndPointHandlerInstances(endpointHandlerClasses);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ignored) {
            endpointHandlers = Collections.emptyList();
        }

        return new HttpRequestEndpointHandlers(endpointHandlers);
    }

    private static List<HttpRequestEndpointHandler> createEndPointHandlerInstances(
            Set<Class<? extends HttpRequestEndpointHandler>> endpointHandlerClasses
    ) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<HttpRequestEndpointHandler> endpointHandlerInstances = new ArrayList<>();
        for (Class<? extends HttpRequestEndpointHandler> endpointHandlerClass : endpointHandlerClasses) {
            addEndPointHandlerInstance(endpointHandlerInstances, endpointHandlerClass);
        }

        return endpointHandlerInstances;
    }

    private static void addEndPointHandlerInstance(
            List<HttpRequestEndpointHandler> endpointHandlers,
            Class<? extends HttpRequestEndpointHandler> endpointHandlerClass
    ) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        HttpRequestEndpointHandler endpointHandler = endpointHandlerClass.getConstructor().newInstance();

        endpointHandlers.add(endpointHandler);
    }
}
