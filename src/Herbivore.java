import javafx.scene.paint.Color;

/**
 * An Herbivore.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Herbivore extends Entity {

    World.Cell currentCell;
    
    /**
     * Constructs an object of type Herbivore.
     * @param currentCell
     */
    public Herbivore(World.Cell currentCell) {
        super();
        
        health = 5;
        energy = 1;
        color = Color.YELLOW;
        this.currentCell = currentCell;
    }
    

    protected void act(World.Cell[][] grid) {
        while (this.energy > 0) {
        move(grid);
        energy--;
        }
        return;
    }
    
    protected void refresh() {
        health--;
        if (health < 1) {
            currentCell.entity = null;
        } else {
            energy = 1;
        }
    }
        
    
    protected void move(World.Cell[][] grid) {
        
        final int currentRow = currentCell.getCurrentRow();
        final int currentColumn = currentCell.getCurrentColumn();
        int[] nextCoor = new int[2];

                
        nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
        
        //System.out.println(grid[currentRow][currentColumn].entity);
        //System.out.println(currentRow + " , " + currentColumn);
        //System.out.println(nextCoor[0] + " , " + nextCoor[1]);
        
        if(grid[nextCoor[0]][nextCoor[1]].entity instanceof Herbivore) {
            return;
        } else {
            if(grid[nextCoor[0]][nextCoor[1]].entity instanceof Plant) {
                health = 5;
            }
            grid[currentRow][currentColumn].entity = null;
            grid[nextCoor[0]][nextCoor[1]].entity = this;
            currentCell = grid[nextCoor[0]][nextCoor[1]];
            System.out.println(currentCell.entity + " moved");

            
        }
    }

    protected int[] targetAdjacentCell(int currentRow, int currentColumn, World.Cell[][] grid) {
        
        final int MAX_ROWS = grid.length - 1;
        final int MAX_COLUMNS = grid[0].length - 1;
        
        int direction = RandomGenerator.nextNumber(8);
        int[] nextCoor = {currentRow, currentColumn};
        
        System.out.println(direction);
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
    
    
    protected void repro() {}
    
    
    
    
    
    
    
    
}
