import javafx.scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.*;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

/**
 * Displays Game of Life
 *
 * @author Adam Doquiatan
 * @version 2018
 */
public class DisplayerFrame extends Application
{
	
    public static int MAX_ROWS;
    public static int MAX_COLUMNS;
    
    //50x50 GUI
    public static final int CELL_HEIGHT = 9;
    public static final int CELL_WIDTH = 9;
    public static final double STROKE_WIDTH = 0.5;
    public static final double SPACING = 0.1;
    
    //TEST GUI
//    public static final int CELL_HEIGHT = 40;
//    public static final int CELL_WIDTH = 40;
//    public static final double STROKE_WIDTH = 1.5;
//    public static final int SPACING = 4;

    static Game game;
    static World.Cell[][] gameGrid;
    private Rectangle[][] rectangles = new Rectangle[MAX_ROWS][MAX_COLUMNS];
    static GridPane myGridPane;
    static MenuBar menuBar;
    
    public void start(Stage stage){
	   
        VBox globalPane = new VBox();
        
        buildMenu(globalPane);
		buildGUI(globalPane);

		// Sets box and outer gridpane padding
        myGridPane.setHgap(SPACING);
        myGridPane.setVgap(SPACING);
        myGridPane.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
        
        myGridPane.addEventHandler(MouseEvent.MOUSE_CLICKED, updateGame);
		
        // Sets up the scene
		Scene scene = new Scene(globalPane);
		stage.setScene(scene);
		stage.setTitle("Game of Life");
		stage.sizeToScene(); //Fits window to contents!!!
		stage.setResizable(true);
		stage.show();
    }
    

    private void buildMenu(VBox globalPane) {
        menuBar = new MenuBar();
        
        Menu menu = new Menu("Save/Load");
        menuBar.getMenus().add(menu);
        
        MenuItem save = new MenuItem("Save State");
        MenuItem load = new MenuItem("Load State");
        menu.getItems().add(save);
        menu.getItems().add(load);
        
        globalPane.getChildren().add(menuBar);
    }
    
    // Generates boxes(cells) on a grid
    private void buildGUI(VBox globalPane) {
        myGridPane = new GridPane();
        
        for(int row = 0; row < MAX_ROWS; row++) {
            for(int col = 0; col < MAX_COLUMNS; col++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(CELL_WIDTH);
                rec.setHeight(CELL_HEIGHT);
                rec.setStroke(Color.BLACK);
                rec.setStrokeWidth(STROKE_WIDTH);
                
                if(gameGrid[row][col].entity == null) { 
                    rec.setFill(Color.WHITE);
                } else {
                    rec.setFill(gameGrid[row][col].entity.getColor());
                }
                
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
               
                rectangles[row][col] = rec;
                
                myGridPane.getChildren().addAll(rec);
            }
        }
        globalPane.getChildren().add(myGridPane);
    }
    
    private void updateGUI() {
        for(int row = 0; row < MAX_ROWS; row++) {
            for(int col = 0; col < MAX_COLUMNS; col++) {
                if(gameGrid[row][col].entity == null) { 
                    rectangles[row][col].setFill(Color.WHITE);
                } else {
                    rectangles[row][col].setFill(gameGrid[row][col].entity.getColor());
                }
            }
        }
    }
    
    /**
     * Updates game on click
     */
    EventHandler<MouseEvent> updateGame = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            game.nextTurn();
            updateGUI();
        }
    };
    
   
    
    

}
