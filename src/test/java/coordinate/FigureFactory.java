package coordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FigureFactory {

    LINE(2, Line::new),
    TRIANGLE(3, Triangle::new),
    RECTANGLE(4, Rectangle::new);

    private int typeSize;
    private FigureCreator creator;

    FigureFactory(int typeSize, FigureCreator creator) {
        this.typeSize = typeSize;
        this.creator = creator;
    }

    private static Map<Integer, FigureFactory> figureFactoryByType = new HashMap<>();

    static {
        for (FigureFactory factory : values()) {
            figureFactoryByType.put(factory.typeSize, factory);
        }
    }

    public static Figure getInstance(List<Point> points) {

        if (!figureFactoryByType.containsKey(points.size())) {
            throw new IllegalArgumentException("유효하지 않은 도형입니다.");
        }

        return figureFactoryByType.get(points.size()).creator.create(points);
    }

}
