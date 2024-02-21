package models;

public class Cell {
    private int row;
    private int coloum;

    private CellState cellState;

    private Player player;

    public  Cell(int row , int coloumn){
        this.row = row;
        this.coloum = coloumn;
        this.cellState = CellState.EMPTY;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColoum() {
        return coloum;
    }

    public void setColoum(int coloum) {
        this.coloum = coloum;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void display(){
        if(player == null){
            System.out.print("| - |");
        }
        else{
            System.out.print("| "+player.getSymbol().getaChar()+" |");
        }
    }
}
