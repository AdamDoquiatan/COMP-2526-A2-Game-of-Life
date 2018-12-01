import java.io.Serializable;

import javafx.scene.paint.Color;

/**
 * Represents an Entity.
 *
 * @author Adam Doquiatan
 * @version 2018
 */
abstract class Entity implements Serializable {  
    /** 
     */
    private static final long serialVersionUID = 1L;
    protected int maxHealth;
    protected int maxEnergy;
    
    
    protected int health;
    protected int energy;
    protected transient Color color;
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
        World.Cell[] neighbors = new World.Cell[8];

        neighbors = gatherNeighbors(currentRow, currentColumn, grid);
        
        World.Cell[] edibles = new World.Cell[8];
       
        //Picks out adjecent cells containing food.
        boolean ediblesCheck = false;
        
        for(int i = 0; i < neighbors.length; i++) {
            if(neighbors[i] != null) {
                if(checkIfEdible(neighbors[i].entity)) {
                    edibles[i] = neighbors[i];
                    ediblesCheck = true;
                }
            }
        }
        
        //If there's food in an adjecent cell, entity moves to random cell conatining food.
        if(ediblesCheck == true) {
            boolean moved = false;
            
            while (moved != true) {
                int nextCellNum = RandomGenerator.nextNumber(8);
                
                if(edibles[nextCellNum] != null && edibles[nextCellNum].entity != null) {
                    nextCoor[0] = edibles[nextCellNum].getCurrentRow();
                    nextCoor[1] = edibles[nextCellNum].getCurrentColumn();
                    eat();
                    grid[currentRow][currentColumn].entity = null;
                    grid[nextCoor[0]][nextCoor[1]].entity = this;
                    currentCell = grid[nextCoor[0]][nextCoor[1]];
                    moved = true;
                }
            }
            return;
        }

        //If there's no food in adjecent cells, entity moves to random empty cell.
        nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
        
        Entity targetEntity = grid[nextCoor[0]][nextCoor[1]].entity;
        
        if (targetEntity == null) {
            grid[currentRow][currentColumn].entity = null;
            grid[nextCoor[0]][nextCoor[1]].entity = this;
            currentCell = grid[nextCoor[0]][nextCoor[1]];
        } else {
            return;
        }
    }
    
    /**
     * An entity eats another entity, replenishing its health
     */
    protected void eat() {
        health = maxHealth;
    }
    
    /**
     * An entity loses 1 health each turn. If health reaches 0 it dies.
     * Otherwise, energy is replenished.
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
     * An entity creates an entity of the same type as itself in 
     * adjecent null-entity cell under specified circumstances
     * 
     * @param grid
     * @param currentClass
     * @param minFriendNeighbors
     * @param minNullNeighbors
     * @param minFoodNeighbors
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
        
        //Tallies neighbor types
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
        
        // Decides if/how to reproduce
        if (friendNeighbors >= minFriendNeighbors && nullNeighbors >= minNullNeighbors && foodNeighbors >= minFoodNeighbors) {
            while (grid[nextCoor[0]][nextCoor[1]].entity != null) {
               nextCoor = targetAdjacentCell(currentRow, currentColumn, grid);
            }
            grid[nextCoor[0]][nextCoor[1]].entity = cloneSelf(grid[nextCoor[0]][nextCoor[1]]);
            grid[nextCoor[0]][nextCoor[1]].entity.setEnergy(0);
        }
        return;
    }

    /**
     * @param cloneCell
     *          location of new entity
     * @return a new instance of the type of entity calling function
     */
    protected abstract Entity cloneSelf(World.Cell cloneCell);
    
    /**
     * Restores color to the entity (used after a game is loaded)
     */
    protected abstract void restoreColor();
    
    
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
