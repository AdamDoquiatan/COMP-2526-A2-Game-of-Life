import java.io.Serializable;

/**
 * A world for entities to exist.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class World implements Serializable {
    /** 
     */
    private static final long serialVersionUID = 1L;
    private Game game;
    private Cell[][] grid;
    
    /**
     * Constructs an object of type World.
     * Sets up class variables.
     * @param game
     */
    public World(Game game) {
        this.game = game;
        grid = new Cell[game.MAX_ROWS][game.MAX_COLUMNS];
        populateGrid();
    }
    
    /**
     * Populates the grid with cells.
     */
    private void populateGrid() {
        for(int i = 0; i < game.MAX_ROWS; i++) {
            for(int j = 0; j < game.MAX_COLUMNS; j++) {
                Cell cell = new Cell(i, j);
                populateCell(cell);
                
                //!!
                grid[i][j] = cell; //Swap j and i to flip grid.
                //!!
                
               //System.out.print("row, column: " + i + " , " + j + " entity:" + cell.entity + "\n");
            }
        }
    }
    
    
    /**
     * Prompts the entity in each cell to take an action
     */
    public void updateWorld() {
        for(int i = 0; i < game.MAX_ROWS; i++) {
            for(int j = 0; j < game.MAX_COLUMNS; j++) {
                grid[i][j].entityAction();
            }
        }
        
        for(int i = 0; i < game.MAX_ROWS; i++) {
            for(int j = 0; j < game.MAX_COLUMNS; j++) {
                grid[i][j].entityRefresh();
            }
        } 
    }
    
    /**
     * @return the grid
     */
    public Cell[][] getGrid() {
        return grid;
    }
    
    /**
     * Populates a cell with an entity.
     * @param cell
     * @return potentially inhabited cell
     */
    private void populateCell(Cell cell) {
        int entityDeterminer = RandomGenerator.nextNumber(99);
        
        if(entityDeterminer >= 80) {
            cell.entity = new Herbivore(cell);
        } else if (entityDeterminer >= 60) {
            cell.entity = new Plant(cell);
        } else if (entityDeterminer >= 50) {
            cell.entity = new Carnivore(cell);
        } else if (entityDeterminer >= 45) {
            cell.entity = new Omnivore(cell);
        }
    }
    
    /**
     * Inner Cell class. Makes up the World.
     *
     * @author Adam Doquiatan
     * @version 2018
     */
    public class Cell implements Serializable {
        /** 
         */
        private static final long serialVersionUID = 1L;
        Entity entity = null;
        private final int row;
        private final int column;
        
        /**
         * Constructs an object of type Cell.
         * @param row
         * @param column
         */
        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
        
        /**
         * An entity acts
         */
        public void entityAction() {
            if(entity == null) {
                return;
            } else {
                entity.act(grid);
                return;
            }
        }
        
        /**
         * Prompts entity to enter its end of turn refresh phase.
         */
        public void entityRefresh() {
            if(entity == null ) {
                return;
            } else {
                entity.refresh();
                return;
            }
        }

        /**
         * @return the cell's current row
         */
        //!!
        //Return column for getCurrentRow, row for getCurrentColumn to flip grid.
        public int getCurrentRow() {
            return row;
        }
        
        /**
         * @return the cell's current column
         */
        public int getCurrentColumn() {
            return column;
        }
        //!!
    }
    
    
    
    
}
