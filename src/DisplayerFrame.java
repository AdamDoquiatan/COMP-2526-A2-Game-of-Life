import javafx.scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.application.*;
import javafx.event.ActionEvent;
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
public class DisplayerFrame extends Application implements Serializable
{
	
    /** 
     */
    private static final long serialVersionUID = 1L;
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
    
    /**
     * Starts the GUI
     * @see javafx.application.Application#start(javafx.stage.Stage)
     * @param stage
     */
    public void start(Stage stage){
	   
        VBox globalPane = new VBox();
        
        buildMenu(globalPane, stage);
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
    

    /**
     * Builds a menu at the top of the viewport
     * @param globalPane
     * @param stage
     */
    private void buildMenu(VBox globalPane, Stage stage) {
        menuBar = new MenuBar();
        
        Menu menu = new Menu("Save/Load");
        menuBar.getMenus().add(menu);
        
        MenuItem save = new MenuItem("Save State");
        MenuItem load = new MenuItem("Load State");
        
        
        // Save button functionality
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save file");
                fileChooser.setInitialFileName("GAME_OF_LIFE_SAVE");
                File savedFile = fileChooser.showSaveDialog(stage);

                if(savedFile != null) {
                    try {
                        FileOutputStream fileOutput = new FileOutputStream(savedFile.getAbsolutePath());
                        ObjectOutput out = new ObjectOutputStream(fileOutput);
                        out.writeObject(game);
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println("Game Saved");
                }
            }
        }
        );
        
        // Load button functionality
        load.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load file");
                File target = fileChooser.showOpenDialog(null);
                
                if(target != null) {
                    try {
                        FileInputStream fileInput = new FileInputStream(target.getAbsolutePath());
                        ObjectInput in = new ObjectInputStream(fileInput);
                        game = (Game) in.readObject();
                        in.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    
                    gameGrid = game.world.getGrid();
                    
                    //Color isn't serializable
                    //This loop restores colors to all entities in the grid
                    for(int row = 0; row < MAX_ROWS; row++) {
                        for(int col = 0; col < MAX_COLUMNS; col++) {
                            if(gameGrid[row][col].entity != null) {
                                gameGrid[row][col].entity.restoreColor();
                            }
                        }
                    }
                    
                    updateGUI();
                    System.out.println("Game Loaded");
                }
            }
        }
        );

        menu.getItems().add(save);
        menu.getItems().add(load);
        
        globalPane.getChildren().add(menuBar);
    }
    
    /**
     * Generates boxes(cells) on a grid
     * @param globalPane
     */
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
    
    /**
     * Updates the GUI to match the gameGrid
     */
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
