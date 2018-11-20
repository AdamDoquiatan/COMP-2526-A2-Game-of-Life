import javafx.scene.paint.Color;

/**
 * Represents an Entity.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
abstract class Entity {  
    protected int maxHealth;
    protected int maxEnergy;
    
    
    protected int health;
    protected int energy;
    protected Color color;
    World.Cell currentCell;

    /**
     * An entity acts
     * @param grid
     */
    protected abstract void act(World.Cell[][] grid);
     
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
     * @return Entities Energy
     */
    protected int getEnergy() {
        return energy;
    }
     
    /**
     * Sets entities Energy
     * @param health
     */
    protected void setEnergy(int energy) {
        this.energy = energy;
    }
    
    /**
     * @return Entities color
     */
    protected Color getColor() {
        return color;
    }
    
    protected abstract boolean checkIfEdible(Entity entity);
    
    /**
     * An entity moves
     * @param grid
     */
    protected void move(World.Cell[][] grid) {
        final int currentRow = currentCell.getCurrentRow();
        final int currentColumn = currentCell.getCurrentColumn();
        
        int[] nextCoor = new int[2];

        nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
        
        Entity targetEntity = grid[nextCoor[0]][nextCoor[1]].entity;
        
        if (checkIfEdible(targetEntity) || targetEntity == null) {
            if(checkIfEdible(targetEntity)) {
                eat();
            }
            grid[currentRow][currentColumn].entity = null;
            grid[nextCoor[0]][nextCoor[1]].entity = this;
            currentCell = grid[nextCoor[0]][nextCoor[1]];
        } else {
            return;
        }
    }
    
    protected void eat() {
        health = maxHealth;
    }
    
    /**
     * @see Entity#refresh()
     * Herbivor loses 1 health each turn. Recovers energy
     */
    protected void refresh() {
        health--;
        if (health < 1) {
            currentCell.entity = null;
        } else {
            energy = maxEnergy;
        }
    }
    
    /**
     * @param <T>
     * @param <F>
     * @see Entity#repro(World.Cell[][])
     * Plant creates new plant in adjecent null-entity cell under certain circumstances
     * @param grid
     */
    protected <T, F> void repro(World.Cell[][] grid, Class<T> currentClass, int minFriendNeighbors, int minNullNeighbors, int minFoodNeighbors) {
        final int currentRow = currentCell.getCurrentRow();
        final int currentColumn = currentCell.getCurrentColumn();
       
        
        World.Cell[] neighbors = new World.Cell[8];
        int friendNeighbors = 0;
        int nullNeighbors = 0;
        int foodNeighbors = 0;
        
        int[] nextCoor = new int[2];
        
        neighbors = gatherNeighbors(currentRow, currentColumn, grid);
        
        for(int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] != null) {
                if (currentClass.isInstance(neighbors[i].entity)) {
                    friendNeighbors++;   
                } else if(neighbors[i].entity == null) {
                    nullNeighbors++;
                } else if (checkIfEdible(neighbors[i].entity)) {
                    foodNeighbors++;
                }
            }
        }
        
        if (friendNeighbors >= minFriendNeighbors && nullNeighbors >= minNullNeighbors && foodNeighbors >= minFoodNeighbors) {
            while (grid[nextCoor[0]][nextCoor[1]].entity != null) {
               nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
            }
            grid[nextCoor[0]][nextCoor[1]].entity = cloneSelf(grid[nextCoor[0]][nextCoor[1]]);
            grid[nextCoor[0]][nextCoor[1]].entity.setEnergy(0);
        }
        return;
    }

    protected abstract Entity cloneSelf(World.Cell cloneCell);
    
    
    /**
     * Targets a random adjecent cell
     * @param currentRow
     * @param currentColumn
     * @param grid
     * @return the row and column of an adjecent cell
     */
    protected int[] targetAdjacentCell(int currentRow, int currentColumn, World.Cell[][] grid) {
        final int MAX_ROWS = grid.length - 1;
        final int MAX_COLUMNS = grid[0].length - 1;
        
        int direction = RandomGenerator.nextNumber(8);
        int[] nextCoor = {currentRow, currentColumn};
        
        //System.out.println(direction);
                switch (direction) {
                case 0:
                    if(currentRow > 0) {
                        nextCoor[0] = currentRow - 1;
                    }
                    break;
                case 1:
                    if(currentRow > 0 && currentColumn < MAX_COLUMNS) {
                        nextCoor[0] = currentRow - 1;
                        nextCoor[1] = currentColumn + 1;
                    }
                    break;
                case 2:
                    if(currentColumn < MAX_COLUMNS) {
                      nextCoor[1] = currentColumn + 1;
                    }
                    break;
                case 3:
                    if(currentRow < MAX_ROWS && currentColumn < MAX_COLUMNS) {
                      nextCoor[0] = currentRow + 1;
                      nextCoor[1] = currentColumn + 1;
                    }
                    break;
                case 4:
                    if(currentRow < MAX_ROWS) {
                      nextCoor[0] = currentRow + 1;
                    }
                    break;
                case 5:
                    if(currentRow < MAX_ROWS && currentColumn > 0) {
                      nextCoor[0] = currentRow + 1;
                      nextCoor[1] = currentColumn -1;
                    }
                    break;
                case 6:
                    if(currentColumn > 0) {
                      nextCoor[1] = currentColumn -1;
                    }
                    break;
                case 7:
                    if(currentRow > 0 && currentColumn > 0) {
                      nextCoor[0] = currentRow -1;
                      nextCoor[1] = currentColumn -1;
                    }
                    break;
                
                default:
                    break;
                }
                return nextCoor;
    }
    
    /**
     * Creates an array of each surrounding cell starting at the topmost neighbor, and going clockwise
     * @param currentRow
     * @param currentColumn
     * @param grid
     * @return an array of 8 surrounding cells
     */
    protected World.Cell[] gatherNeighbors(int currentRow, int currentColumn, World.Cell[][] grid) {
        final int MAX_ROWS = grid.length - 1;
        final int MAX_COLUMNS = grid[0].length - 1;
        
        World.Cell[] neighbors = new World.Cell[8];
        
        for(int i = 0; i < neighbors.length; i++) {
            switch(i) {
            case 0:
                if(currentRow > 0) {
                    neighbors[i] = grid[currentRow - 1][currentColumn];
                } 
                break;
            case 1:
                if(currentRow > 0 && currentColumn < MAX_COLUMNS) {
                    neighbors[i] = grid[currentRow - 1][currentColumn + 1];
                }
                break;
            case 2:
                if(currentColumn < MAX_COLUMNS) {
                    neighbors[i] = grid[currentRow][currentColumn +1];
                }
                break;
            case 3:
                if(currentRow < MAX_ROWS && currentColumn < MAX_COLUMNS) {
                    neighbors[i] = grid[currentRow + 1][currentColumn + 1];
                }
                break;
            case 4:
                if(currentRow < MAX_ROWS) {
                    neighbors[i] = grid[currentRow + 1][currentColumn];
                }
                break;
            case 5:
                if(currentRow < MAX_ROWS && currentColumn > 0) {
                    neighbors[i] = grid[currentRow + 1][currentColumn -1];
                }
                break;
            case 6:
                if(currentColumn > 0) {
                    neighbors[i] = grid[currentRow][currentColumn - 1];
                }
                break;
            case 7:
                if(currentRow > 0 && currentColumn > 0) {
                    neighbors[i] = grid[currentRow - 1][currentColumn - 1];
                }
                break;
            default:
                break;
            }

        }
        return neighbors;
    }
}
