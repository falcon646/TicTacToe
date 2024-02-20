package controller;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionsMismatchException;
import models.Game;
import models.GameState;
import models.Player;
import strategies.WinningStrategy;

import java.util.List;

public class GameController { // stateless
    public Game startGame(int dimension , List<Player> players, List<WinningStrategy> winningStrategies) throws PlayersCountDimensionsMismatchException, DuplicateSymbolException, MoreThanOneBotException {
        return Game.getBuilder().setDimensions(dimension).setPlayers(players).setWinningStrategy(winningStrategies).build();
    }

    public void printBoard(Game game){
        game.printBoard();
    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    public void makeMove(Game game){
        game.makeMove();
    }
}
