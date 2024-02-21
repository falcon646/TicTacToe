package strategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{

    private Map<Symbol, Integer> leftDiagonalCount = new HashMap<>();
    private Map<Symbol, Integer> rightDiagonalCount = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        Symbol symbol = move.getPlayer().getSymbol();

        int row = move.getCell().getRow();
        int col = move.getCell().getColoum();

        // left diagonal
        if(row == col){
            leftDiagonalCount.put(symbol,leftDiagonalCount.getOrDefault(symbol,0)+1);
            if(leftDiagonalCount.get(symbol) == board.getSize()){
                return true;
            }
        }

        // right diagonal
        if(row + col == board.getSize()-1){
            rightDiagonalCount.put(symbol,rightDiagonalCount.getOrDefault(symbol,0)+1);
            if(rightDiagonalCount.get(symbol) == board.getSize()){
                return true;
            }
        }
        return false;
    }
}
