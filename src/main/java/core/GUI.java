package core;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.text.Position;
import javafx.scene.input.ClipboardContent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.input.DragEvent;
import javafx.scene.Node;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
public class GUI extends Application
{
	public static final String image_dir = "src/main/resources/core/images/";
	private int screenWidth;
	private int screenHeight;
	private Map<String, Image> deck;
	private Pane root;
	private Scene scene;
	private Image tempImage;
	private static Text playerInfo;
	private TileRummyMain game;
	
	/* TODO remove this when done*/
	public static final String[] suites = {"R", "B", "G", "O"};
	public static final int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	
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
		/* Set up GUI */
		setPanePos();	
		root = new Pane();
		scene = new Scene(root, screenWidth, screenHeight);
		setUpscene();
		initUI(primaryStage, scene);
		handleStage(primaryStage, scene);
		
		/* Set up game */
		game = new TileRummyMain();
		game.initialize();
		placeDeck(game.initDeck);
		dealHand(game.player1.getHand(), game.player2.getHand(), game.player3.getHand());
		
		deck = new HashMap<String, Image>();
	}
	
	/*
	 * Prototype: setUpscene()
	 *   Purpose: Set the scene as the target to drop images
	 * */
	private void setUpscene() 
	{
		scene.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* data is dragged over the target */
		        /* accept it only if it is not dragged from the same node 
		         * and if it has a string data */
		        /* allow for both copying and moving, whatever user chooses */
		        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		        event.consume();
		    }
		});
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
	private void initUI(Stage primaryStage, Scene scene) 
	{		
		handleButtonEvents();
		handleImages();
				
		//Set style for nodes
		root.setStyle("-fx-background-color: green; -fx-text-fill: white;");
	}
	
	/*
	 * Prototype: handleImages(Pane root) 
	 * 	 Purpose: Create, add and position images.
	 * */
	private void handleImages() 
	{
		loadBackgroundImages();		    
	}

	/*
	 * Prototype: loadBackgroundImages() 
	 * 	 Purpose: Read in background images and add to scene.
	 * */
	private boolean loadBackgroundImages() 
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
			deckImage = new Image(new FileInputStream(image_dir + "deckImage.jpg"),
					screenWidth, (screenHeight - 0.15*screenHeight)*0.9,true,true);
			
			player1BoardImage = new Image(new FileInputStream(image_dir + "playerBoard.jpg"), 
					screenWidth - 0.25*screenWidth, screenHeight ,true,true);

			player2BoardImage = new Image(new FileInputStream(image_dir + "sideBoard.jpg"), 
					screenWidth, (screenHeight - 0.15*screenHeight)*0.9,true,true);
			
			player3BoardImage = new Image(new FileInputStream(image_dir + "oppositeSideBoard.jpg"),
					screenWidth - 0.05*screenWidth, screenHeight ,true,true);
			
			player4BoardImage = new Image(new FileInputStream(image_dir + "sideBoard.jpg"),
					screenWidth, (screenHeight - 0.15*screenHeight)*0.9,true,true);
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
		
		/* Deck next to right player */
		imgDeckView.setX(screenWidth - screenWidth*0.13); 
		imgDeckView.setY(screenHeight/16 + screenHeight*0.05); 
		
		/* Player bottom of screen */
		imgPlayer1View.setX(screenWidth - screenWidth*0.92); 
		imgPlayer1View.setY(screenHeight - screenHeight*0.2025); 
				
		/* Left side player*/
		imgPlayer2View.setX(0); 
		imgPlayer2View.setY(screenHeight/16 + screenHeight*0.05); 
		
		/* Player opposite side of screen */
		imgPlayer3View.setY(screenHeight*0.0125); 
		imgPlayer3View.setX(screenWidth - screenWidth*0.99); 

		/* Right side player */
		imgPlayer4View.setX(screenWidth - screenWidth*0.05); 
		imgPlayer4View.setY(screenHeight/16 + screenHeight*0.05); 
		
		/* Set text fields */
		playerInfo = new Text();
		playerInfo.setFont(new Font(50));
		playerInfo.setText("Current Turn is: Player 1");
		playerInfo.setY(screenWidth - screenWidth*0.465);
		playerInfo.setX(0);
		
	    root.getChildren().addAll(imgDeckView, imgPlayer1View, imgPlayer2View, 
	    		imgPlayer3View, imgPlayer4View, playerInfo);
	    
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
	private void handleButtonEvents() 
	{
		//Create buttons 
		Button btnStart = new Button("Start Game");
		Button btnExit = new Button("Exit Game");
	
		btnStart.setLayoutX(screenWidth/2 + 100);
		btnStart.setLayoutY(screenHeight/2);
		
		btnExit.setLayoutX(screenWidth/2);
		btnExit.setLayoutY(screenHeight/2);
		
		//Set events
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				game.playGame();
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
	
	/* --------------------------------------------------------------------------------
	 * 
	 * 			GUI Interface
	 * 
	 * --------------------------------------------------------------------------------
	 * */
	
	/*   prototype: placeDeck(ArrayList<Tile> deck)
	 *   purpose: Place all cards given in pile
	 * */
	public boolean placeDeck(ArrayList<Tile> deck)
	{
		sayMsg("Place Deck");
		int i = 0;
		double offsetY = 0.0;
		ImageView tempImageView;

		for(Tile card : deck)
		{
			i += 1;
			if(i%4 == 0)
				offsetY += screenHeight*0.0265;
			
			tempImageView = createCard(i, card, offsetY);
			root.getChildren().add(tempImageView);
		}
		return true;
	}
	
	private ImageView createCard(int i, Tile card, double offsetY) 
	{
		ImageView tempImageView;
		
		double randNum = 0.0;
	
		Random rand = new Random();

		Image imageHolder = card.getImage();
		
		/* Set drag and drop events */
		String valueString1 = Integer.toString(card.getValue());
		String valueString2 = card.getSuite();
		String valueString3 = valueString2 + valueString1;
		tempImageView = setUpCardEvents(imageHolder,valueString3);
		
		//Set width and height
		tempImageView.setFitHeight(screenHeight/19);
		tempImageView.setFitWidth(screenWidth*0.0225);

		//Set Pos
		randNum = (0.0225) * rand.nextDouble();
		tempImageView.setX(screenWidth - screenWidth*0.10 + screenWidth*randNum*Math.pow(-1, i)); 
		tempImageView.setY(screenHeight/16 + screenHeight*0.06 + offsetY);
		
		
		return tempImageView;
	}

	private ImageView setUpCardEvents(Image imageHolder, String id) {
		
		ImageView tempImageView = new ImageView(imageHolder);
		tempImageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				tempImageView.setVisible(false);
				tempImageView.setX(event.getSceneX());
				tempImageView.setY(event.getSceneY());
				tempImageView.setVisible(true);
			}
		});
		tempImageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				double xCord = tempImageView.getX();
				double yCord = tempImageView.getY();
				tempImageView.getId();
				System.out.println(xCord + " " + yCord + " " + tempImageView.getId());
				game.checkPerimeter(xCord,yCord);
			}
		});
		tempImageView.setId(id);
		return tempImageView;
	}

	public boolean dealHandPlayer1(ArrayList<Tile> playerHand)
	{
		ImageView tempImageView;
		int i = 0;

		for(Tile tile:playerHand)
		{
			/* Set drag and drop events */		
			String valueString1 = Integer.toString(tile.getValue());
			String valueString2 = tile.getSuite();
			String valueString3 = valueString2 + valueString1;
			tempImageView = setUpCardEvents(tile.getImage(), valueString3);

			//Set width and height
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);

			//Set Pos
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);
			
			tempImageView.setX(screenWidth - screenWidth*0.92 + i*screenWidth*0.025); 
			tempImageView.setY(screenHeight - screenHeight*0.2025); 
			i += 1;
			root.getChildren().add(tempImageView);
		}
		
		return true;
	}

	public boolean dealHandPlayer2(ArrayList<Tile> playerHand)
	{
		ImageView tempImageView;
		int i = 1;

		for(Tile tile:playerHand)
		{
			String valueString1 = Integer.toString(tile.getValue());
			String valueString2 = tile.getSuite();
			String valueString3 = valueString2 + valueString1;
			/* Set drag and drop events */		
			tempImageView = setUpCardEvents(tile.getImage(), valueString3);

			//Set width and height
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);

			//Set Pos
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);
			
			tempImageView.setX(screenWidth*0.0125); 
			tempImageView.setY(screenHeight*0.86 - i*screenHeight*0.0525); 
			i += 1;
			root.getChildren().add(tempImageView);
		}
		return true;
	}
	public boolean dealHandPlayer3(ArrayList<Tile> playerHand)
	{
		ImageView tempImageView;
		int i = 0;

		for(Tile tile:playerHand)
		{
			String valueString1 = Integer.toString(tile.getValue());
			String valueString2 = tile.getSuite();
			String valueString3 = valueString2 + valueString1;
			/* Set drag and drop events */		
			tempImageView = setUpCardEvents(tile.getImage(), valueString3);

			//Set width and height
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);

			//Set Pos
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);
			
			tempImageView.setX(screenWidth - screenWidth*0.92 + i*screenWidth*0.025); 
			tempImageView.setY(screenHeight*0.025); 
			i += 1;
			root.getChildren().add(tempImageView);
		}
		
		return true;
	}
	
	/*   prototype: dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, 
	 *   ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	 *   purpose: deal each card to player
	 * */
	public boolean dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, ArrayList<Tile> p3Hand)
	{
		double totalRun = 0;
		sayMsg("Hands being dealt");
		
		sortHand(p1Hand);
		sortHand(p2Hand);
		sortHand(p3Hand);
		
		dealHandPlayer1(sortHand(p1Hand));
		dealHandPlayer2(sortHand(p2Hand));
		dealHandPlayer3(sortHand(p3Hand));
		return true;
	}
	
	/*
	 * Prototype: sayMsg(String msg, int delay)
	 * 	 Purpose: General function to prompt a message and add a delay
	 * */
	public boolean sayMsg(String msg)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setContentText(msg);
		alert.show();	
		return true;
	}
	
	/*
	 * Prototype: setPlayerTurn(String playerName)
	 *   Purpose: Display which user is currently playing
	 */
	public static boolean setPlayerTurn(String playerName)
	{
		String result = "Current Turn is: Player";
		switch (playerName)
		{
			case "player1": result += " 1"; 
				 break;
			case "player2": result += " 2"; 
				 break;
			case "player3": result += " 3"; 
				 break;
			case "player4": result += " 4"; 
				 break;
			default:
				 return false;
		}
		
		playerInfo.setText(result);
		return true;
	}
	public ArrayList<Tile> sortHand(ArrayList<Tile> tempHand)
	{
		ArrayList<Tile> masterHand = new ArrayList<Tile>();
		ArrayList<Tile> redHand = new ArrayList<Tile>();
		ArrayList<Tile> blueHand = new ArrayList<Tile>();
		ArrayList<Tile> orangeHand = new ArrayList<Tile>();
		ArrayList<Tile> greenHand = new ArrayList<Tile>();
		
		for(Tile temp:tempHand)
		{
			if(temp.getColour().equals("O"))
			{
				orangeHand.add(temp);
			}
			else if(temp.getColour().equals("G"))
			{
				greenHand.add(temp);
			}
			else if(temp.getColour().equals("B"))
			{
				blueHand.add(temp);
			}
			else if(temp.getColour().equals("R"))
			{
				redHand.add(temp);
			}
		}
		
		Collections.sort(orangeHand, new customComparitor());
		Collections.sort(greenHand, new customComparitor());
		Collections.sort(blueHand, new customComparitor());
		Collections.sort(redHand, new customComparitor());
		
		masterHand.addAll(orangeHand);
		masterHand.addAll(greenHand);
		masterHand.addAll(blueHand);
		masterHand.addAll(redHand);
		
		return masterHand;
	}
}


