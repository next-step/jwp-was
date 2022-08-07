package webserver.filter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GlobalHttpFilterRegistry {
    public static final List<GlobalHttpFilter> GLOBAL_HTTP_FILTERS = orderingScan();

    private static List<GlobalHttpFilter> orderingScan() {
        return GlobalHttpFilterScanner.scan()
                .stream()
                .sorted(Comparator.comparingInt(GlobalHttpFilterRegistry::getOrDefaultOrderValue))
                .collect(Collectors.toUnmodifiableList());
    }

    private static int getOrDefaultOrderValue(GlobalHttpFilter globalHttpFilter) {
        GlobalFilterOrder orderAnnotation = globalHttpFilter.getClass().getDeclaredAnnotation(GlobalFilterOrder.class);
        return orderAnnotation == null ? Integer.MAX_VALUE : orderAnnotation.value();
    }
}
