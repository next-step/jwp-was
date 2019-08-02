package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FigureFactory {

    private static final Map<Integer, FigureCreator> CREATOR_OF_FIGURE = new HashMap<>();
    private static final FigureCreator FIGURE_NOT_FOUND = ignore -> {
        throw new IllegalArgumentException("유효하지 않은 도형입니다.");
    };

    static {
        CREATOR_OF_FIGURE.put(Line.SIZE, Line::new);
        CREATOR_OF_FIGURE.put(Triangle.SIZE, Triangle::new);
        CREATOR_OF_FIGURE.put(Rectangle.SIZE, Rectangle::new);
    }

    static Figure getInstance(final List<Point> points) {
        return CREATOR_OF_FIGURE.getOrDefault(points.size(), FIGURE_NOT_FOUND)
                .create(points);
    }
}
