package coordinate;

import java.util.*;

public class FigureFactory {
    private static final Map<Integer, FigureCreator> figures = new HashMap<>();

    static {
        figures.put(Line.NUMBER_OF_POINTS, Line::new);
        figures.put(Triangle.NUMBER_OF_POINTS, Triangle::new);
        figures.put(Rectangle.NUMBER_OF_POINTS, Rectangle::new);
    }

    static Figure getInstance(final List<Point> points) {
        return Optional.ofNullable(points)
                .map(Collection::size)
                .map(figures::get)
                .map(creator -> creator.create(points))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."))
                ;
    }
}
