package coordinate;

import java.util.List;

public class Triangle extends AbstractFigure {

    public static final int POINT_SIZE = 3;
    public static final String FIGURE_NAME = "삼각형";
    public static final int DEFAULT_AREA = 0;

    public Triangle(List<Point> points) {
        super(points);
    }

    @Override
    public int size() {
        return POINT_SIZE;
    }

    @Override
    public String getName() {
        return FIGURE_NAME;
    }

    @Override
    public double area() {
        return DEFAULT_AREA;
    }
}
