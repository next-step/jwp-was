package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FigureFactory {

    private static Map<Integer, FigureType> figureCache = new HashMap<>();

    static {
        for (FigureType feature : FigureType.values()) {
            figureCache.put(feature.getSize(), feature);
        }
    }

    static Figure getInstance(List<Point> points) {
        return Optional.ofNullable(figureCache.get(points.size()))
                .map(creator -> creator.create(points))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
    }
}
