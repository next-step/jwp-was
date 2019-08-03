package coordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FigureCreators {
    static private Map<Integer, FigureCreator> pointCountFigureCreatorMap = new HashMap<>();

    static {
        pointCountFigureCreatorMap.put(2, Line::new);
        pointCountFigureCreatorMap.put(3, Triangle::new);
        pointCountFigureCreatorMap.put(4, Rectangle::new);
    }

    static public Optional<FigureCreator> get(int pointCount) {
        return Optional.of(pointCountFigureCreatorMap.get(pointCount));
    }
}
