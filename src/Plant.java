import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * A Plant.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Plant extends Entity implements HerbivoreEdible, OmnivoreEdible, Serializable { 
    
    /**
     * Constructs an object of type Plant.
     * @param currentCell
     */
    public Plant(World.Cell currentCell) {
        super();
        
        maxHealth = 5;
        maxEnergy = 1;
        
        health = maxHealth;
        energy = maxEnergy;
        color = Color.GREEN;
        this.currentCell = currentCell;
    }
    
    /**
     * @see Entity#act(World.Cell[][])
     * @param grid
     */
    protected void act(World.Cell[][] grid) {
        while(energy > 0) {
            repro(grid, this.getClass(), 2, 3, 0);
            energy--;
        }
    }
    
    /**
     * @see Entity#cloneSelf(World.Cell)
     * @param cloneCell
     * @return a Plant
     */
    protected Entity cloneSelf(World.Cell cloneCell) {
        return new Plant(cloneCell);
    }
    
    @Override
    /**
     * Only refreshes Plant energy
     * @see Entity#refresh()
     */
    protected void refresh() {
        energy = maxEnergy;
    }
    
    // Unused inherited methods
    protected void move(World.Cell[][] grid) {}
    protected boolean checkIfEdible(Entity entity) {return false;}
   
}
