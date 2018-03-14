package tokyo.ymr27.cli2048java.model.parts;

public class Tile {
  private int value;
  private Vector position;
  private boolean isMerged;

  public Tile(int value, Vector position, boolean isMerged) {
    this.value = value;
    this.position = position;
    this.isMerged = isMerged;
  }
  public Tile(int value, Vector position) {
    this(value, position, false);
  }

  public int getValue() {
    return value;
  }
  public Vector getPosition() {
    return position;
  }

  public void setToUnMerged() {
    this.isMerged = false;
  }
  public boolean isAbleToMergeWith(Tile other) {
    return (value == other.getValue()) && !isMerged && !other.isMerged;
  }

  public void moveTo(Vector to) {
    this.position = to;
  }
  public void mergeWith(Tile other) {
    this.value *= 2;
    this.isMerged = true;
  }
}
