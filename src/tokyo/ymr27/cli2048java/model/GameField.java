package tokyo.ymr27.cli2048java.model;

import tokyo.ymr27.cli2048java.utils.Direction;
import tokyo.ymr27.cli2048java.model.parts.Grid;

public class GameField {
  private int size;
  private Grid grid;

  public GameField(int size) {
    this.size = size;
    grid = new Grid(size);
  }

  public boolean isTerminated() {
    return grid.isTerminated();
  }
  public boolean isCleared() {
    return grid.isCleared();
  }
  public boolean isFailed() {
    return grid.isFailed();
  }
  public boolean moveTiles(Direction direction) {
    return grid.moveTilesTo(direction);
  }
  public void generateRandomTile() {
    grid.generateRandomTiles(1);
  }
  public Integer[][] getValueArray() {
    return grid.makeValueArray();
  }
}
