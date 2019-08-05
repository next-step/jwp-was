package coordinate;

import java.util.List;

public class Triangle extends AbstractFigure {
    public static final int NUMBER_OF_POINTS = 3;

    public Triangle(List<Point> points) {
        super(points);
    }

    @Override
    public int size() {
        return NUMBER_OF_POINTS;
    }

    @Override
    public String getName() {
        return "삼각형";
    }

    @Override
    public double area() {
        return 0;
    }
}
