import javafx.scene.paint.Color;

/**
 * An Herbivore.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Herbivore extends Entity {
    final int MAX_HEALTH = 5;
    final int MAX_ENERGY = 1;
    
    
    /**
     * Constructs an object of type Herbivore.
     * @param currentCell
     */
    public Herbivore(World.Cell currentCell) {
        super();
        
        health = MAX_HEALTH;
        energy = MAX_ENERGY;
        color = Color.YELLOW;
        this.currentCell = currentCell;
    }
    
    /**
     * @see Entity#act(World.Cell[][])
     * Herbivor moves while it has energy. Loses one energy each move
     * @param grid
     */
    protected void act(World.Cell[][] grid) {
        while (energy > 0) {
        move(grid);
        energy--;
        }
    }
    
    /**
     * @see Entity#refresh()
     * Herbivor loses 1 health each turn. Recovers energy
     */
    protected void refresh() {
        health--;
        if (health < 1) {
            currentCell.entity = null;
        } else {
            energy = MAX_ENERGY;
        }
    }
    
    /**
     * @see Entity#move(World.Cell[][])
     * Herbivore moves in random direction if not blocked.
     * If it moves over something HerbivoreEdible, life restored 
     * @param grid
     */
    protected void move(World.Cell[][] grid) {
        final int currentRow = currentCell.getCurrentRow();
        final int currentColumn = currentCell.getCurrentColumn();
        
        int[] nextCoor = new int[2];

        nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
        
        Entity targetEntity = grid[nextCoor[0]][nextCoor[1]].entity;
        
        if(!checkIfEdible(targetEntity) || targetEntity == null) {
            return;
        } else {
            if(checkIfEdible(targetEntity)) {
                eat();
            }
            grid[currentRow][currentColumn].entity = null;
            grid[nextCoor[0]][nextCoor[1]].entity = this;
            currentCell = grid[nextCoor[0]][nextCoor[1]];

        }
    }
    
    protected void eat() {
        health = MAX_HEALTH;
    }
    
    protected boolean checkIfEdible(Entity entity) {
        if(entity instanceof HerbivoreEdible) {
            return true;
        }
        return false;
    }
    
    

    /**
     * @see Entity#repro(World.Cell[][])
     * Herbivores can't reproduce
     * @param grid
     */
    protected void repro(World.Cell[][] grid) {}



}
