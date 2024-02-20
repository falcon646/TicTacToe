import controller.GameController;
import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionsMismatchException;
import models.Game;
import models.GameState;

import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws PlayersCountDimensionsMismatchException, DuplicateSymbolException, MoreThanOneBotException {
        GameController gameController = new GameController();
        Game game = gameController.startGame(3, new ArrayList<>(),new ArrayList<>());

        while(gameController.checkState(game).equals(GameState.IN_PROGRESS)){
            gameController.printBoard(game);
//            gameController.makeMove();
        }

    }
}
