package view;

public class StaticViewRevolver {

  public View resolve(String path) {
    return new StaticView();
  }

}
