package coordinate;

import java.util.List;

public class Rectangle extends AbstractFigure {

  public static final int SIZE = 4;

  public Rectangle(List<Point> points) {
    super(points);
  }

  @Override
  public int size() {
    return SIZE;
  }

  @Override
  public String getName() {
    return "사각형";
  }

  @Override
  public double area() {
    return 0;
  }
}
