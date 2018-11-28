import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * An Herbivore.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Herbivore extends Entity implements CarnivoreEdible, OmnivoreEdible, Serializable  {
    
    
    /** 
     */
    private static final long serialVersionUID = 1L;


    /**
     * Constructs an object of type Herbivore.
     * @param currentCell
     */
    public Herbivore(World.Cell currentCell) {
        super();
        
        maxHealth = 5;
        maxEnergy = 1;
        
        health = maxHealth;
        energy = maxEnergy;
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
            repro(grid, this.getClass(), 1, 2, 2);
            energy--;
        }
    }
    
    /**
     * @see Entity#checkIfEdible(Entity)
     * @param entity
     * @return whether an entity can be eaten
     */
    protected boolean checkIfEdible(Entity entity) {
        if(entity instanceof HerbivoreEdible) {
            return true;
        }
        return false;
    }

   
    /**
     * @see Entity#cloneSelf(World.Cell)
     * @param cloneCell
     * @return a new Herbivore
     */
    protected Entity cloneSelf(World.Cell cloneCell) {
        return new Herbivore(cloneCell);
    }

    /**
     * @see Entity#restoreColor()
     *
     */
    protected void restoreColor() {
        color = Color.YELLOW;
    }

}
