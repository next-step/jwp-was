package coordinate;

import java.util.List;

public class Rectangle extends AbstractFigure {

    public static final int POINT_SIZE = 4;
    public static final String FIGURE_NAME = "사각형";
    public static final int DEFAULT_AREA = 0;

    public Rectangle(List<Point> points) {
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
