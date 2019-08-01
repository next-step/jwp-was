package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FigureFactory {

    private static final Map<Integer, FigureCreator> figures = new HashMap<>();

    static {
        figures.put(Line.COUNT_OF_POINTS, Line::new);
        figures.put(Triangle.COUNT_OF_POINTS, Triangle::new);
        figures.put(Rectangle.COUNT_OF_POINTS, Rectangle::new);
    }

    static Figure getInstance(List<Point> points) {
        int countOPoint = points.size();
        checkConstraints(countOPoint);

        return figures.get(countOPoint).create(points);
    }

    private static void checkConstraints(int countOPoint) {
        if (!figures.containsKey(countOPoint)) {
            throw new IllegalArgumentException("유효하지 않은 도형입니다.");
        }
    }
}
