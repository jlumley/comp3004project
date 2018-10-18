package core;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.geometry.Pos;

public class GUI extends Application
{
	private int screenWidth;
	private int screenHeight;
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{	
		//Grab user screen size 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = 	screenSize.height;
		
		//Create layout, and scene
		StackPane root = new StackPane();
		Scene scene = new Scene(root, screenWidth, screenHeight);
	
		initUI(root);
		
		//Set the ID for root
		root.setStyle("-fx-background-color: green; -fx-text-fill: white;");
		
		//Set scene and show it
		primaryStage.setTitle("Rummy");
		//Get CSS file
		scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		/* Start game */
		startGame();
	}
	
	public boolean dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	{
		return false;
	}
	
	@SuppressWarnings("restriction")
	private void initUI(StackPane root) 
	{
		//Create components
		Button btnStart = new Button("Start Game");
		Button btnExit = new Button("Exit Game");
		
		//Set events
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event)
			{
				startGame();
			}
		});
		
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event)
			{
				System.exit(0);
			}
		});
		
		/* Set up image views and load images */
		Image deckImage;
		ImageView imageView;
		try 
		{
			deckImage = new Image(new FileInputStream("src/main/resources/core/images/deckImage.jpg"));
			imageView = new ImageView(deckImage); 
		    
		    imageView.setFitHeight(screenHeight*0.9); 
		    imageView.setFitWidth(screenWidth * 0.125); 
		    
		    imageView.setPreserveRatio(true); 
		    root.setAlignment(imageView, Pos.TOP_RIGHT);
		    
			//Add to layout 
			root.getChildren().addAll(btnStart, btnExit, imageView);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			//Add to layout 
			root.getChildren().addAll(btnStart, btnExit);
		}
	}

	public boolean startGame() 
	{
		System.out.println("STart Game");
		return true;
	}
}
