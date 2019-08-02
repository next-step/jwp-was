package coordinate;

import java.util.List;

public class Triangle extends AbstractFigure {

  public static final int SIZE = 3;

  public Triangle(List<Point> points) {
    super(points);
  }

  @Override
  public int size() {
    return SIZE;
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
