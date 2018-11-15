import javafx.scene.paint.Color;

public class Carnivore extends Entity {
    final int MAX_HEALTH = 5;
    final int MAX_ENERGY = 1;
    
    public Carnivore(World.Cell currentCell) {
        super();
        
        health = MAX_HEALTH;
        energy = MAX_ENERGY;
        color = Color.RED;
        this.currentCell = currentCell;
        
    }

    protected void act(World.Cell[][] grid) {

    }


    protected void move(World.Cell[][] grid) {

    }


    protected void repro(World.Cell[][] grid) {

    }


    protected void refresh() {

    }

    @Override
    protected boolean checkIfEdible(Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void eat() {
        // TODO Auto-generated method stub
        
    }




}
