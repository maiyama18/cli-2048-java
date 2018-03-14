package tokyo.ymr27.cli2048java;

import tokyo.ymr27.cli2048java.controller.GameController;
import tokyo.ymr27.cli2048java.model.GameField;
import tokyo.ymr27.cli2048java.utils.InputManager;
import tokyo.ymr27.cli2048java.view.GameView;

class GameApp {
  private int size;

  public GameApp(int size) {
    this.size = size;
  }

  void start() {
    GameField gameField = new GameField(size);
    GameView gameView = new GameView(size);
    InputManager inputManager = new InputManager();

    GameController gameController = new GameController(gameField, gameView, inputManager);
    gameController.start();
  }
}
