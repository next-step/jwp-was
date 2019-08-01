package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FigureFactory {
    private static Map<Integer, Function<List<Point>, Figure>> figureMap;
    static {
        figureMap = new HashMap<>();
        figureMap.put(2, Line::new);
        figureMap.put(3, Triangle::new);
        figureMap.put(4, Rectangle::new);
    }

    static Figure getInstance(List<Point> points) {
        return Optional.ofNullable(figureMap.get(points.size()))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."))
                .apply(points);
    }
}
