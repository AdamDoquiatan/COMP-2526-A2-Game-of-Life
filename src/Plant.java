import javafx.scene.paint.Color;

/**
 * A Plant.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Plant extends Entity {

    World.Cell currentCell;
    
    /**
     * Constructs an object of type Plant.
     * @param currentCell
     */
    public Plant(World.Cell currentCell) {
        super();
        
        health = 1;
        energy = 1;
        color = Color.GREEN;
        this.currentCell = currentCell;
    }
    
    protected void act(World.Cell[][] grid) {
        repro();
    }
    
    protected void move(World.Cell[][] grid) {
        
    }
    
    protected void refreshEntity() {}

    protected void repro() {
       // System.out.println(currentCell + " repro-ed");
    }

    protected void refresh() {}
    
}
