package models;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionsMismatchException;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Board board;
    private List<Player> players;
    private int nextMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;
    private Player winner;
    private GameState gameState;

    private List<Move> moves;


    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies){
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.nextMovePlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.moves = new ArrayList<>();
        this.board = new Board(dimension);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    public void printBoard(){
        board.printBoard();
    }

    public boolean checkWinner(Board board , Move move){
        for(WinningStrategy winningStrategy : winningStrategies ){
            if(winningStrategy.checkWinner(board , move)){
                return true;
            }
        }
        return false;
    }

    public boolean validateMove(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getColoum();
        if(row >= board.getSize()) {
            return false;
        }
        if(col >= board.getSize()){
            return false;
        }
        if(board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)){
            return true;
        }
        return false;
    }

    public void makeMove(){
        Player currentMovePlayer = players.get(nextMovePlayerIndex);
        System.out.println("It is "+currentMovePlayer.getName() +"'s turn. Please make your move !");
        Move move = currentMovePlayer.makeMove(board);
        System.out.println(currentMovePlayer.getName() + " has mad a move at row : "+ move.getCell().getRow() +" & coloum : "+move.getCell().getColoum());

        // validation of the move
        if(!validateMove(move)){
            System.out.println("Invalid move, please check again");
            return;
        }

        // updating the move
        int row = move.getCell().getRow();
        int col = move.getCell().getColoum();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentMovePlayer);

        Move finalMove = new Move(cellToChange , currentMovePlayer);
        moves.add(finalMove);

        nextMovePlayerIndex += 1;
        nextMovePlayerIndex %= players.size();

        // check game status
        if (checkWinner(board, finalMove)) {
            gameState = GameState.WINNER;
            winner = currentMovePlayer;
        }
        else if(moves.size() == board.getSize() * board.getSize()){
            gameState = GameState.DRAW;
        }

    }

    public  void undo(){
        if (moves.size() == 0) {
            System.out.println("No moves to undo");
            return;
        }
            Move lastMove = moves.get(moves.size()-1);
            moves.remove(lastMove);

            Cell cell = lastMove.getCell();
            cell.setPlayer(null);
            cell.setCellState(CellState.EMPTY);

            for (WinningStrategy winningStrategy : winningStrategies){
                winningStrategy.handleUndo(board, lastMove);
            }

            nextMovePlayerIndex -= 1;
            nextMovePlayerIndex = (nextMovePlayerIndex + players.size()) % players.size();
    }

    public static class Builder {
        private List<Player> players;
        private List<WinningStrategy> winningStrategy;
        private int dimensions;

        private Builder() {
            this.players = new ArrayList<>();
            this.winningStrategy = new ArrayList<>();
            this.dimensions = 0;
        }

        public List<Player> getPlayers() {
            return players;
        }
        // commenting since we used another way of adding list of player ie addPlayer()
        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }
        public Builder addPlayer(Player player){
            this.players.add(player);
            return this;
        }

        public List<WinningStrategy> getWinningStrategy() {
            return winningStrategy;
        }
        // commentig for the same reason as setPlayers()
        public Builder setWinningStrategy(List<WinningStrategy> winningStrategy) {
            this.winningStrategy = winningStrategy;
            return this;
        }

        public Builder addWinningStrategy(WinningStrategy winningStrategy) {
            this.winningStrategy.add(winningStrategy);
            return this;
        }

        public int getDimensions() {
            return dimensions;
        }

        public Builder setDimensions(int dimensions) {
            this.dimensions = dimensions;
            return this;
        }

        public Game build() throws PlayersCountDimensionsMismatchException, DuplicateSymbolException, MoreThanOneBotException {
            validate();
            return new Game(dimensions, players, winningStrategy);
        }

        private void validate() throws DuplicateSymbolException, PlayersCountDimensionsMismatchException, MoreThanOneBotException {
            validateBotCount();
            validateDimensionsAndPlayersCount();
            validateUniqueSymbolsForPlayers();
        }
        private void validateBotCount() throws MoreThanOneBotException {
            int botCount = 0;
            for(Player player: players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount += 1;
                }
            }
            if (botCount > 1){
                throw new MoreThanOneBotException();
            }
        }

        private void validateDimensionsAndPlayersCount() throws PlayersCountDimensionsMismatchException {
            if(players.size() != dimensions -1){
                throw  new PlayersCountDimensionsMismatchException();
            }
        }

        private void validateUniqueSymbolsForPlayers() throws DuplicateSymbolException {
            Map<Character,Integer> symbolCounts = new HashMap<>();
            for(Player player:players){
                if(!symbolCounts.containsKey(player.getSymbol().getaChar())){
                    symbolCounts.put(player.getSymbol().getaChar(),0);
                }
                symbolCounts.put(player.getSymbol().getaChar(),symbolCounts.get(player.getSymbol().getaChar()) + 1 );
                if(symbolCounts.get(player.getSymbol().getaChar()) > 1){
                    throw new DuplicateSymbolException();
                }
            }

        }
    }
}
