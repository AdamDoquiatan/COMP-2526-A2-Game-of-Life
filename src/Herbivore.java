import javafx.scene.paint.Color;

/**
 * An Herbivore.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Herbivore extends Entity {
    
    
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
        
        Plant food = new Plant(null);
        Class<?>[] interfaces = food.getClass().getInterfaces();
        
        while (energy > 0) {
        move(grid);
        repro(grid, this.getClass(), interfaces[0], 1, 2, 2);
        energy--;
        }
    }
    
    protected boolean checkIfEdible(Entity entity) {
        if(entity instanceof HerbivoreEdible) {
            return true;
        }
        return false;
    }

   
    protected Entity cloneSelf(World.Cell cloneCell) {
        return new Herbivore(cloneCell);
    }



}
