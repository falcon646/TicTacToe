package strategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColWinningstrategy  implements WinningStrategy{

    private Map<Integer, Map<Symbol,Integer>> countMap = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getColoum();
        Symbol symbol = move.getCell().getPlayer().getSymbol();
        if(!countMap.containsKey(col)){
            countMap.put(col, new HashMap<>());
        }
        Map<Symbol, Integer> colMap = countMap.get(col);
        colMap.put(symbol, colMap.getOrDefault(symbol,0)+1);

        if (colMap.get(symbol) == board.getSize()) {
            return true;
        }
        return false;
    }
}
