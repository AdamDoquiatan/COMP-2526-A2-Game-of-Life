import java.io.Serializable;

import javafx.application.Application;

/**
 * Creates the world, controls the game, calls displayer
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class Game implements Serializable {
    
   /** 
     */
    private static final long serialVersionUID = 1L;
/** 
     */
    final int MAX_ROWS = 50;
    final int MAX_COLUMNS = 50; 
    
   World world;
   
   /**
    * Constructs an object of type Game.
    * Handles initial GUI display
    */
   public Game() {
       world = new World(this);
       
       DisplayerFrame.MAX_ROWS = MAX_ROWS;
       DisplayerFrame.MAX_COLUMNS = MAX_COLUMNS;
       DisplayerFrame.gameGrid = world.getGrid();
       DisplayerFrame.game = this;
       Application.launch(DisplayerFrame.class);
       
   }
   
   /**
    * Updates the game. Reloads GUI
    */
   void nextTurn() {
       world.updateWorld();
       DisplayerFrame.gameGrid = world.getGrid();
       
   }
}
