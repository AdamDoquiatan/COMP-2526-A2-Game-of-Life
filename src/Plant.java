import javafx.scene.paint.Color;

/**
 * A Plant.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Plant extends Entity implements HerbivoreEdible {
    
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
            repro(grid, this.getClass(), null, 2, 3, 0);
            energy--;
        }
    }
    
    protected Entity cloneSelf(World.Cell cloneCell) {
        return new Plant(cloneCell);
    }
    
    @Override
    protected void refresh() {
        energy = maxEnergy;
    }
    
    protected void move(World.Cell[][] grid) {}

    protected boolean checkIfEdible(Entity entity) {return false;}
   
}
