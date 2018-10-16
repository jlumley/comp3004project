package core;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.BackgroundImage;

public class GUI extends Application
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{		
		//Create layout, and scene
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 500, 500);
	
		initUI(root);
		
		//Set scene and show it
		primaryStage.setTitle("Rummy");
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
		
		//Set background image
		BackgroundImage myBI= new BackgroundImage(new Image("my url",32,32,false,true),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		
		//then you set to your node
		myContainer.setBackground(new Background(myBI));
		
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
		
		//Add to layout 
		root.getChildren().addAll(btnStart, btnExit);
	}

	public boolean startGame() 
	{
		System.out.println("STart Game");
		return true;
	}
}
