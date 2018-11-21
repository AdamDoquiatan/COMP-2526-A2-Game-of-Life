import javafx.scene.paint.Color;

/**
 * An Omnivore.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Omnivore extends Entity implements CarnivoreEdible {
    
    
    /**
     * Constructs an object of type Omnivore.
     * @param currentCell
     */
    public Omnivore(World.Cell currentCell) {
        super();
        
        maxHealth = 5;
        maxEnergy = 1;
        
        health = maxHealth;
        energy = maxEnergy;
        color = Color.BLUE;
        this.currentCell = currentCell;
    }
    
    /**
     * @see Entity#act(World.Cell[][])
     * Omnivore moves while it has energy. Loses one energy each move
     * @param grid
     */
    protected void act(World.Cell[][] grid) {
        
        while (energy > 0) {
            move(grid);
            repro(grid, this.getClass(), 1, 3, 1);
            energy--;
        }
    }
    
    /**
     * @see Entity#checkIfEdible(Entity)
     * @param entity
     * @return whether an entity is edible
     */
    protected boolean checkIfEdible(Entity entity) {
        if(entity instanceof OmnivoreEdible) {
            return true;
        }
        return false;
    }

    /**
     * @see Entity#cloneSelf(World.Cell)
     * @param cloneCell
     * @return a new Omnivore
     */
    protected Entity cloneSelf(World.Cell cloneCell) {
        return new Omnivore(cloneCell);
    }



}
