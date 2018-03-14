package tokyo.ymr27.cli2048java.model.parts;

import tokyo.ymr27.cli2048java.utils.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Grid {
  private static final int NUM_INITIAL_TILES = 2;
  private static final double PROB_OF_4_GENERATED = 0.1;
  private static final Random generator = new Random();

  private final int size;
  private List<Tile> tiles;

  public Grid(int size) {
    this.size = size;
    this.tiles = new ArrayList<>();

    generateRandomTiles(NUM_INITIAL_TILES);
  }

  public void generateRandomTiles(int numTiles) {
    List<Vector> positions = getRandomEmptyPositions(numTiles);
    for (Vector position : positions) {
      int value = getValueOfNewTile();
      generateTileAt(position, value);
    }
  }

  public boolean moveTilesTo(Direction direction) {
    Vector moveDirection = Vector.unit(direction);
    Traversal traversal = new Traversal(direction, size);
    boolean isAnyMoved = false;

    for (int row : traversal.getRowIndices()) {
      for (int col : traversal.getColIndices()) {
        Vector position = new Vector(row, col);
        if (!isPositionEmpty(position)) {
          isAnyMoved = moveTile(position, moveDirection) || isAnyMoved;
        }
      }
    }
    setAllTilesToUnMerged();

    return isAnyMoved;
  }

  public boolean isTerminated() {
    return isCleared() || isFailed();
  }
  public boolean isCleared() {
    return getMaxValue() == 2048;
  }
  public boolean isFailed() {
    return !isAnyEmptyPosition() && !isAbleToMoveWhenThereIsNoEmptyPosition();
  }

  public Integer[][] makeValueArray() {
    Integer[][] valueArray = new Integer[size][size];
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        Vector position = new Vector(row, col);
        Tile tile = getTileAt(position);
        valueArray[row][col] = (tile == null) ? null : tile.getValue();
      }
    }

    return valueArray;
  }

  private boolean isAbleToMoveWhenThereIsNoEmptyPosition() {
    Integer[][] valueArray = makeValueArray();
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size - 1; col++) {
        if (valueArray[row][col].equals(valueArray[row][col+1])) {
          return true;
        }
      }
    }
    for (int col = 0; col < size; col++) {
      for (int row = 0; row < size - 1; row++) {
        if (valueArray[row][col].equals(valueArray[row+1][col])) {
          return true;
        }
      }
    }

    return false;
  }
  private boolean moveTile(Vector position, Vector moveDirection) {
    Vector nextPosition = new Vector(position.getRow(), position.getCol());
    Tile tile = getTileAt(position);

    while (true) {
      nextPosition = nextPosition.plus(moveDirection);

      if (isPositionOutOfBound(nextPosition)) {
        nextPosition = nextPosition.minus(moveDirection);
        if (!nextPosition.equals(position)) {
          tile.moveTo(nextPosition);
          return true;
        } else {
          return false;
        }
      }

      if (!isPositionEmpty(nextPosition)) {
        Tile other = getTileAt(nextPosition);
        if (tile.isAbleToMergeWith(other)) {
          removeTileAt(position);
          other.mergeWith(tile);
          return true;
        } else {
          nextPosition = nextPosition.minus(moveDirection);
          if (!nextPosition.equals(position)) {
            tile.moveTo(nextPosition);
            return true;
          } else {
            return false;
          }
        }
      }
    }
  }

  private boolean isPositionOutOfBound(Vector position) {
    int row = position.getRow();
    int col = position.getCol();

    return (row < 0 || row >= size) || (col < 0 || col >= size);
  }

  private int getMaxValue() {
    return tiles.stream()
            .mapToInt(Tile::getValue)
            .max().orElse(0);
  }
  private void setAllTilesToUnMerged() {
    tiles.forEach(Tile::setToUnMerged);
  }

  private Tile getTileAt(Vector position) {
    return tiles.stream()
            .filter(tile -> tile.getPosition().equals(position))
            .findFirst().orElse(null);
  }
  private void generateTileAt(Vector position, int value) {
    tiles.add(new Tile(value, position));
  }
  private void removeTileAt(Vector position) {
    tiles.removeIf(tile -> tile.getPosition().equals(position));
  }

  private int getValueOfNewTile() {
    return (Math.random() < PROB_OF_4_GENERATED) ? 4 : 2;
  }

  private boolean isAnyEmptyPosition() {
    return numEmptyPositions() > 0;
  }
  private int numEmptyPositions() {
    return getAllEmptyPositions().size();
  }
  private List<Vector> getRandomEmptyPositions(int numPositions) {
    List<Vector> candidates = getAllEmptyPositions();
    Collections.shuffle(candidates);

    return candidates.stream()
            .limit(numPositions)
            .collect(Collectors.toList());
  }
  private List<Vector> getAllEmptyPositions() {
    List<Vector> emptyPositions = new ArrayList<>();
    for (int row = 0; row < size; row++) {
      for (int col = 0; col < size; col++) {
        Vector position = new Vector(row, col);
        if (isPositionEmpty(position)) {
          emptyPositions.add(position);
        }
      }
    }

    return emptyPositions;
  }
  private boolean isPositionEmpty(Vector position) {
    return getTileAt(position) == null;
  }
}
