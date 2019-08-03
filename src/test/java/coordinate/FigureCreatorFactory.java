package coordinate;

import coordinate.createor.LineCreator;
import coordinate.createor.RectangleCreator;
import coordinate.createor.TriangleCreator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FigureCreatorFactory{
    private static final Map<Integer, FigureCreator> figureType;
    static {
        figureType = new HashMap<>();
        figureType.put(2, new LineCreator());
        figureType.put(3, new TriangleCreator());
        figureType.put(4, new RectangleCreator());
    }

    public static FigureCreator create(List<Point> points) {
        return Optional.ofNullable(figureType.get(points.size()))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
    }
}