package core;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.geometry.Pos;
import javafx.scene.text.Font; 
import javafx.scene.text.Text; 
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GUI extends Application
{
	public static final String image_dir = "src/main/resources/core/images/";
	private int screenWidth;
	private int screenHeight;
	private Map<String, Image> deck;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	/*
	 * Prototype: start()
	 * 	 Purpose: Called first when GUI is launched
	 * */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{	
		setPanePos();	
		Pane root = new Pane();
		Scene scene = new Scene(root, screenWidth, screenHeight);
		deck = new HashMap<String, Image>();
		initUI(root, primaryStage, scene);
		handleStage(primaryStage, scene);
	}
	
	/*
	 * Prototype: handleStage()
	 * 	 Purpose: Set scene for stage and configure stage
	 * */
	private void handleStage(Stage primaryStage, Scene scene) 
	{
		primaryStage.setTitle("Rummy");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/*
	 * Prototype: initUI(Pane root, Stage primaryStage, Scene scene) 
	 * 	 Purpose: Instantiated scene and create / place UI elements.
	 * */
	@SuppressWarnings("restriction")
	private void initUI(Pane root, Stage primaryStage, Scene scene) 
	{		
		handleButtonEvents(root);
		handleImages(root);
				
		//Set style for nodes
		root.setStyle("-fx-background-color: green; -fx-text-fill: white;");
	}
	
	/*
	 * Prototype: handleImages(Pane root) 
	 * 	 Purpose: Create, add and position images.
	 * */
	private void handleImages(Pane root) 
	{
		loadBackgroundImages(root);		    
	}

	/*
	 * Prototype: loadBackgroundImages() 
	 * 	 Purpose: Read in background images and add to scene.
	 * */
	private boolean loadBackgroundImages(Pane root) 
	{
		Image deckImage;
		Image player1BoardImage;
		Image player2BoardImage;
		Image player3BoardImage;
		Image player4BoardImage;
		
		ImageView imgDeckView;
		ImageView imgPlayer1View;
		ImageView imgPlayer2View;
		ImageView imgPlayer3View;
		ImageView imgPlayer4View;
		
		try 
		{
			deckImage = new Image(new FileInputStream(image_dir + "deckImage.jpg"));
			player1BoardImage = new Image(new FileInputStream(image_dir + "playerBoard.jpg"));
			player2BoardImage = new Image(new FileInputStream(image_dir + "sideBoard.jpg"));
			player3BoardImage = new Image(new FileInputStream(image_dir + "oppositeSideBoard.jpg"));
			player4BoardImage = new Image(new FileInputStream(image_dir + "sideBoard.jpg"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		imgDeckView = new ImageView(deckImage);
		imgPlayer1View = new ImageView(player1BoardImage);
		imgPlayer2View = new ImageView(player2BoardImage);
		imgPlayer3View = new ImageView(player3BoardImage);
		imgPlayer4View = new ImageView(player4BoardImage);
		
		//Configure image views
		imgDeckView.setX(300); 
		imgDeckView.setY(250); 
	    
		imgPlayer1View.setX(25);
		imgPlayer1View.setY(25);
		
		imgPlayer2View.setX(25);
		imgPlayer2View.setY(25);
		
		imgPlayer3View.setX(25);
		imgPlayer3View.setY(25);
		
		imgPlayer4View.setX(25);
		imgPlayer4View.setY(25);
		
	    root.getChildren().addAll(imgDeckView, imgPlayer1View, imgPlayer2View, 
	    		imgPlayer3View, imgPlayer4View);
	    
	    return true;
	}
	
	/*
	 * Prototype: getCards() 
	 * 	 Purpose: Load in card images and add to hash map
	 * */
	private boolean getCards() 
	{
		//TODO getCards can be removed since tile will load in the card image
		try 
		{
			char[] colours = {'B', 'G', 'O', 'R'};
			String tempCardName;
			
			for(int i=0; i < 4; i++) //Color
			{
				for(int j=1; j < 14; j++) //Card Value
				{
					//Create card file name
					tempCardName = "Tile" + colours[i] + j;
					deck.put(tempCardName, new Image(new FileInputStream(image_dir + tempCardName + ".jpg")));
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * Prototype: handleButtonEvents(Pane root) 
	 * 	 Purpose: Create / add buttons. Handle events for buttons
	 * */
	@SuppressWarnings("restriction")
	private void handleButtonEvents(Pane root) 
	{
		//Create buttons 
		Button btnStart = new Button("Start Game");
		Button btnExit = new Button("Exit Game");
		
		//Set events
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				btnStart.relocate(btnStart.getLayoutX() + 100, 50);
				startGame();
			}
		});
		
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				System.exit(0);
			}
		});
		
		root.getChildren().addAll(btnStart, btnExit);
	}
	
	/* GUI Helpers */
	
	/*
	 * Prototype: setPanePos
	 * 	 Purpose: Set the width and height of the window(scene)
	 * */
	private void setPanePos() 
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = 	screenSize.height;
	}
	
	/* GUI interface */
	
	/*
	 * Prototype: startGame()
	 * 	 Purpose: Start a game of rummy
	 * */
	public boolean startGame() 
	{
		sayMsg("Hand being dealt", 2);
		dealHand(null, null, null, null);
		System.out.println("Start Game");
		return true;
	}
	
	/*   prototype: dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, 
	 *   ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	 *   purpose: deal each card to player
	 * */
	public boolean dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	{
		return false;
	}
	
	/*
	 * Prototype: sayMsg(String msg, int delay)
	 * 	 Purpose: General function to prompt a message and add a delay
	 * */
	public boolean sayMsg(String msg, int delay)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setContentText(msg);
		alert.show();
		
		if(delay < 0)
			return false;
		
		try 
		{
			TimeUnit.SECONDS.sleep(delay);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		alert.close();
		return true;
	}
}


