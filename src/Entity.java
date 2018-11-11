import javafx.scene.paint.Color;

/**
 * Represents an Entity.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
abstract class Entity {  
    
    protected int health;
    protected int energy;
    protected Color color;
    World.Cell currentCell;

    /**
     * An entity acts
     * @param grid
     */
    protected abstract void act(World.Cell[][] grid);
    
    /**
     * An entity moves
     * @param grid
     */
    protected abstract void move(World.Cell[][] grid);
    
    /**
     * An entity reproduces
     * @param grid
     */
    protected abstract void repro(World.Cell[][] grid);
    
    /**
     * An entity refreshes refreshes its stats at the end of its turn
     */
    protected abstract void refresh();
     
    /**
     * @return Entities Health
     */
    protected int getHealth() {
        return health;
    }
     
    /**
     * Sets entities health
     * @param health
     */
    protected void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * @return Entities Energy
     */
    protected int getEnergy() {
        return energy;
    }
     
    /**
     * Sets entities Energy
     * @param health
     */
    protected void setEnergy(int energy) {
        this.energy = energy;
    }
    
    /**
     * @return Entities color
     */
    protected Color getColor() {
        return color;
    }
    
    /**
     * Targets a random adjecent cell
     * @param currentRow
     * @param currentColumn
     * @param grid
     * @return the row and column of an adjecent cell
     */
    protected int[] targetAdjacentCell(int currentRow, int currentColumn, World.Cell[][] grid) {
        final int MAX_ROWS = grid.length - 1;
        final int MAX_COLUMNS = grid[0].length - 1;
        
        int direction = RandomGenerator.nextNumber(8);
        int[] nextCoor = {currentRow, currentColumn};
        
        //System.out.println(direction);
                switch (direction) {
                case 0:
                    if(currentRow > 0) {
                        nextCoor[0] = currentRow - 1;
                    }
                    break;
                case 1:
                    if(currentRow > 0 && currentColumn < MAX_COLUMNS) {
                        nextCoor[0] = currentRow - 1;
                        nextCoor[1] = currentColumn + 1;
                    }
                    break;
                case 2:
                    if(currentColumn < MAX_COLUMNS) {
                      nextCoor[1] = currentColumn + 1;
                    }
                    break;
                case 3:
                    if(currentRow < MAX_ROWS && currentColumn < MAX_COLUMNS) {
                      nextCoor[0] = currentRow + 1;
                      nextCoor[1] = currentColumn + 1;
                    }
                    break;
                case 4:
                    if(currentRow < MAX_ROWS) {
                      nextCoor[0] = currentRow + 1;
                    }
                    break;
                case 5:
                    if(currentRow < MAX_ROWS && currentColumn > 0) {
                      nextCoor[0] = currentRow + 1;
                      nextCoor[1] = currentColumn -1;
                    }
                    break;
                case 6:
                    if(currentColumn > 0) {
                      nextCoor[1] = currentColumn -1;
                    }
                    break;
                case 7:
                    if(currentRow > 0 && currentColumn > 0) {
                      nextCoor[0] = currentRow -1;
                      nextCoor[1] = currentColumn -1;
                    }
                    break;
                
                default:
                    break;
                }
                return nextCoor;
    }
    
    /**
     * Creates an array of each surrounding cell starting at the topmost neighbor, and going clockwise
     * @param currentRow
     * @param currentColumn
     * @param grid
     * @return an array of 8 surrounding cells
     */
    protected World.Cell[] gatherNeighbors(int currentRow, int currentColumn, World.Cell[][] grid) {
        final int MAX_ROWS = grid.length - 1;
        final int MAX_COLUMNS = grid[0].length - 1;
        
        World.Cell[] neighbors = new World.Cell[8];
        
        for(int i = 0; i < neighbors.length; i++) {
            switch(i) {
            case 0:
                if(currentRow > 0) {
                    neighbors[i] = grid[currentRow - 1][currentColumn];
                } 
                break;
            case 1:
                if(currentRow > 0 && currentColumn < MAX_COLUMNS) {
                    neighbors[i] = grid[currentRow - 1][currentColumn + 1];
                }
                break;
            case 2:
                if(currentColumn < MAX_COLUMNS) {
                    neighbors[i] = grid[currentRow][currentColumn +1];
                }
                break;
            case 3:
                if(currentRow < MAX_ROWS && currentColumn < MAX_COLUMNS) {
                    neighbors[i] = grid[currentRow + 1][currentColumn + 1];
                }
                break;
            case 4:
                if(currentRow < MAX_ROWS) {
                    neighbors[i] = grid[currentRow + 1][currentColumn];
                }
                break;
            case 5:
                if(currentRow < MAX_ROWS && currentColumn > 0) {
                    neighbors[i] = grid[currentRow + 1][currentColumn -1];
                }
                break;
            case 6:
                if(currentColumn > 0) {
                    neighbors[i] = grid[currentRow][currentColumn - 1];
                }
                break;
            case 7:
                if(currentRow > 0 && currentColumn > 0) {
                    neighbors[i] = grid[currentRow - 1][currentColumn - 1];
                }
                break;
            default:
                break;
            }

        }
        return neighbors;
    }
}
