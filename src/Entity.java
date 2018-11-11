import javafx.scene.paint.Color;

/**
 * Represents an Entity.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
abstract class Entity {
    
    protected int health;
    protected int energy;
    protected Color color;
    World.Cell currentCell;

    protected abstract void act(World.Cell[][] grid);
    protected abstract void move(World.Cell[][] grid);
    protected abstract void repro();
    protected abstract void refresh();
        
    
     
    /**
     * @return Entities Health
     */
    protected int getHealth() {
        return health;
    }
     
    /**
     * Sets entities health
     * @param health
     */
    protected void setHealth(int health) {
        this.health = health;
    }
    
    /**
     * @return Entities color
     */
    protected Color getColor() {
        return color;
    }
}
