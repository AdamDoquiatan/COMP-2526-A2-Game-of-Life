import javafx.scene.paint.Color;

/**
 * A Plant.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Plant extends Entity {

    World.Cell currentCell;
    
    /**
     * Constructs an object of type Plant.
     * @param currentCell
     */
    public Plant(World.Cell currentCell) {
        super();
        
        health = 1;
        energy = 1;
        color = Color.GREEN;
        this.currentCell = currentCell;
    }
    
    protected void act(World.Cell[][] grid) {
//        while(energy > 0) {
            repro(grid);
            //energy--;
//        }
    }
    
    protected void refresh() {
        energy = 1;
    }

    protected void repro(World.Cell[][] grid) {
        
        final int currentRow = currentCell.getCurrentRow();
        final int currentColumn = currentCell.getCurrentColumn();
        
        World.Cell[] neighbors = new World.Cell[8];
        int plantNeighbors = 0;
        int nullNeighbors = 0;
        
        int[] nextCoor = new int[2];
        
        neighbors = gatherNeighbors(currentRow, currentColumn, grid);
        
        for(int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] != null) {
                if (neighbors[i].entity instanceof Plant) {
                    plantNeighbors++;
                } else if(neighbors[i].entity == null) {
                    nullNeighbors++;
                }
            }
        }
        System.out.println(currentCell.entity + " plantNeigh: " + plantNeighbors + " nullNeigh: " + nullNeighbors);
        System.out.println(currentRow + " " + currentColumn);
        if (plantNeighbors == 3 && nullNeighbors >= 3) {
            while (grid[nextCoor[0]][nextCoor[1]].entity != null) {
               nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
               System.out.println(nextCoor[0] + " " + nextCoor[1]);
            }
            grid[nextCoor[0]][nextCoor[1]].entity = new Plant(grid[nextCoor[0]][nextCoor[1]]);
            grid[nextCoor[0]][nextCoor[1]].entity.setEnergy(0);
            System.out.println(currentCell.entity + " reproed");
        }
        return;
    }
   
    
    
    
    protected void move(World.Cell[][] grid) {}
    
    
    
}
