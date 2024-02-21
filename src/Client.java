import controller.GameController;
import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionsMismatchException;
import models.*;
import strategies.ColWinningstrategy;
import strategies.DiagonalWinningStrategy;
import strategies.RowWinningStrategy;
import strategies.WinningStrategy;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws PlayersCountDimensionsMismatchException, DuplicateSymbolException, MoreThanOneBotException {
        Scanner scanner = new Scanner(System.in);
        int dimensions = 3;
        List<Player> players = new ArrayList<>();

        players.add(new Player(1L,"Ashwin", new Symbol('x'), PlayerType.HUMAN));
        players.add(new Bot(2L,"Munna", new Symbol('o'), BotDifficultyLevel.EASY));
        List<WinningStrategy> winningStrategies = List.of(new RowWinningStrategy(),new ColWinningstrategy(),new DiagonalWinningStrategy());


        GameController gameController = new GameController();
        Game game = gameController.startGame(dimensions, players,winningStrategies);

        while(gameController.checkState(game).equals(GameState.IN_PROGRESS)){
            gameController.printBoard(game);

            System.out.println("Do you want to undo ? (y/n)");
            String undoAnswer = scanner.next();
            if(undoAnswer.equalsIgnoreCase("y")){
                gameController.undo(game);
                continue;
            }

            gameController.makeMove(game);
        }

        System.out.println("Game Finished");
        gameController.printBoard(game);
        GameState gameState = gameController.checkState(game);

        if(gameState.equals(GameState.DRAW)){
            System.out.println("Game has drawn");
        }
        else{
            System.out.println("We have a winner");
        }

    }
}
