package coordinate;

import java.util.List;

public class Line extends AbstractFigure {

    public static final int POINT_SIZE = 2;
    public static final int DEFAULT_AREA = 0;
    public static final String FIGURE_NAME = "선";

    public Line(List<Point> points) {
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
