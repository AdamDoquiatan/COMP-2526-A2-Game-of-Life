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
        while (energy > 0) {
        move(grid);
        energy--;
        }
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
//            System.out.println(currentCell.entity + " moved");
        }
    }

    protected void repro(World.Cell[][] grid) {}
    
    
    
    
    
    
    
    
}
