package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FigureFactory {

    private static final Map<Integer, FigureCreator> creatorMap = new HashMap<>();

    {
        creatorMap.put(2, (points) -> new Line(points));
        creatorMap.put(3, (points) -> new Triangle(points));
        creatorMap.put(3, (points) -> new Rectangle(points));
    }

    static Figure getInstance(List<Point> points) {

        return Optional.ofNullable(creatorMap.get(points.size()))
                .map(creator -> creator.create(points))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 도형입니다."));
    }
}
