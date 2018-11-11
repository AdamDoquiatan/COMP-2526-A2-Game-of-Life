
/**
 * A world for entities to exist.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class World {
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
                grid[i][j] = cell; //Swap j and i to Make it look like Dennis'.
                //!!
                
               System.out.print("I,j: " + i + " , " + j + " entity:" + cell.entity + "\n");
            }
        }
    }
    
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
        
        if(entityDeterminer >= 85) {
            cell.entity = new Herbivore(cell);
        } else if (entityDeterminer >= 65) {
            cell.entity = new Plant(cell);
        }
    }
    
    /**
     * Inner Cell class. Makes up the World.
     *
     * @author Adam Doquiatan
     * @version 2018
     */
    public class Cell {
        Entity entity = null;
        private final int row;
        private final int column;
        
        public Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
        
        public void entityAction() {
            if(entity == null || entity instanceof Plant) {
                //System.out.println("null");
                return;
            } else {
                //System.out.println(entity);
                entity.act(grid);
                return;
            }
        }
        
        public void entityRefresh() {
            if(entity == null || entity instanceof Plant) {
                //System.out.println("null");
                return;
            } else {
                //System.out.println(entity);
                entity.refresh();
                return;
            }
        }

        //!!
        //Return column for getCurrentRow, row for getCurrentColumn to make it look like Dennis'.
        public int getCurrentRow() {
            return row;
        }
        
        public int getCurrentColumn() {
            return column;
        }
        //!!
        
    }
    
    
    
    
}
