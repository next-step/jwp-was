package coordinate;

import java.util.*;

public class FigureFactory {

    private static final Map<Integer, FigureCreator> factory = new HashMap<>();

    static {
        factory.put(2, Line::new);
        factory.put(3, Triangle::new);
        factory.put(4, Rectangle::new);
    }

    static Figure getInstance(List<Point> points) {
        FigureCreator creator = factory.get(points.size());
        if (creator == null) {
            throw new IllegalArgumentException("유효하지 않은 도형입니다.");
        }

        return creator.create(points);
    }
}
