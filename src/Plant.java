import javafx.scene.paint.Color;

/**
 * A Plant.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Plant extends Entity implements HerbivoreEdible {
    final int MAX_HEALTH = 1;
    final int MAX_ENERGY = 1;
    
   
    
    /**
     * Constructs an object of type Plant.
     * @param currentCell
     */
    public Plant(World.Cell currentCell) {
        super();
        
        health = MAX_HEALTH;
        energy = MAX_ENERGY;
        color = Color.GREEN;
        this.currentCell = currentCell;
    }
    
    /**
     * @see Entity#act(World.Cell[][])
     * @param grid
     */
    protected void act(World.Cell[][] grid) {
//        while(energy > 0) {
            repro(grid);
            //energy--;
//        }
    }
    
    /**
     * @see Entity#refresh()
     * Plant recovers energy
     */
    protected void refresh() {
        energy = MAX_ENERGY;
    }

    /**
     * @see Entity#repro(World.Cell[][])
     * Plant creates new plant in adjecent null-entity cell under certain circumstances
     * @param grid
     */
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
//        System.out.println(currentCell.entity + " plantNeigh: " + plantNeighbors + " nullNeigh: " + nullNeighbors);
//        System.out.println(currentRow + " " + currentColumn);
        if (plantNeighbors >= 2 && nullNeighbors >= 3) {
            while (grid[nextCoor[0]][nextCoor[1]].entity != null) {
               nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
//               System.out.println(nextCoor[0] + " " + nextCoor[1]);
            }
            grid[nextCoor[0]][nextCoor[1]].entity = new Plant(grid[nextCoor[0]][nextCoor[1]]);
            grid[nextCoor[0]][nextCoor[1]].entity.setEnergy(0);
//            System.out.println(currentCell.entity + " reproed");
        }
        return;
    }
   
    
    
    /**
     * @see Entity#move(World.Cell[][])
     * Plants can't move
     * @param grid
     */
    protected void move(World.Cell[][] grid) {}



    @Override
    protected boolean checkIfEdible(Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void eat() {
        // TODO Auto-generated method stub
        
    }
    
    
    
}
