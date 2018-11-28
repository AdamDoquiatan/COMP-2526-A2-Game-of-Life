import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * A Carnivore.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Carnivore extends Entity implements OmnivoreEdible, Serializable{
    
    
    /** 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an object of type Canivore.
     * @param currentCell
     */
    public Carnivore(World.Cell currentCell) {
        super();
        
        maxHealth = 5;
        maxEnergy = 1;
        
        health = maxHealth;
        energy = maxEnergy;
        color = Color.RED;
        this.currentCell = currentCell;
    }
    
    /**
     * @see Entity#act(World.Cell[][])
     * Carnivore moves while it has energy. Loses one energy each move
     * @param grid
     */
    protected void act(World.Cell[][] grid) {
        while (energy > 0) {
            move(grid);
            repro(grid, this.getClass(), 1, 3, 2);
            energy--;
        }
    }
    
    /**
     * @see Entity#checkIfEdible(Entity)
     * @param entity
     * @return whether an entity can be eaten
     */
    protected boolean checkIfEdible(Entity entity) {
        if(entity instanceof CarnivoreEdible) {
            return true;
        }
        return false;
    }

   /**
    * @see Entity#cloneSelf(World.Cell)
    * @param cloneCell
    * @return a new Carnivore
    */
    protected Entity cloneSelf(World.Cell cloneCell) {
        return new Carnivore(cloneCell);
    }

    /**
     * @see Entity#restoreColor()
     *
     */
    protected void restoreColor() {
        color = Color.RED;
    }

}
