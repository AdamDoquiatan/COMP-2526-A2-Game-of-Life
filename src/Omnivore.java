import javafx.scene.paint.Color;

/**
 * An Herbivore.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Omnivore extends Entity implements CarnivoreEdible, OmnivoreEdible {
    
    
    /**
     * Constructs an object of type Herbivore.
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
     * Herbivor moves while it has energy. Loses one energy each move
     * @param grid
     */
    protected void act(World.Cell[][] grid) {
        
        while (energy > 0) {
            move(grid);
            repro(grid, this.getClass(), 1, 3, 1);
            energy--;
        }
    }
    
    protected boolean checkIfEdible(Entity entity) {
        if(entity instanceof OmnivoreEdible) {
            return true;
        }
        return false;
    }

   
    protected Entity cloneSelf(World.Cell cloneCell) {
        return new Omnivore(cloneCell);
    }



}
