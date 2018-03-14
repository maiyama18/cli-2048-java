package tokyo.ymr27.cli2048java.controller;

import tokyo.ymr27.cli2048java.utils.Direction;
import tokyo.ymr27.cli2048java.view.GameView;
import tokyo.ymr27.cli2048java.utils.InputManager;
import tokyo.ymr27.cli2048java.model.GameField;

public class GameController {
  private GameField gameField;
  private GameView gameView;
  private InputManager inputManager;

  public GameController(GameField gameField, GameView gameView, InputManager inputManager) {
    this.gameField = gameField;
    this.gameView = gameView;
    this.inputManager = inputManager;
  }

  public void start() {
    gameView.showField(gameField);

    while (!gameField.isTerminated()) {

      while (true) {
        Direction direction = inputManager.getDirectionFromInput();
        if (gameField.moveTiles(direction)) {
          gameView.showInputtedDirection(direction, true);
          break;
        }
        gameView.showInputtedDirection(direction, false);
      }

      gameField.generateRandomTile();
      gameView.showField(gameField);
    }

    if (gameField.isCleared()) {
      gameView.showClearMessage();
    } else if (gameField.isFailed()) {
      gameView.showLoseMessage();
    }
  }
}
