package core;

import javafx.scene.control.TextInputDialog;
import javafx.application.Platform;
import java.awt.Dimension;
import java.lang.Object;
import javafx.scene.control.ContentDisplay;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.print.Printable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.management.PlatformLoggingMXBean;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.control.Label;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ButtonType;
public class GUI extends Application
{
	public static final String image_dir = "src/main/resources/core/images/";
	private int screenWidth;
	private int screenHeight;
	private Pane root;
	private Scene scene;
	private TileRummyMain game;
	private static Button btnFinish;
	public boolean inFieldOrHand = false;
	Label playerTimer;
	private boolean startGame = true;
	Timer t = new Timer();
	
	/* Data structures to hold cards */
	public ArrayList<String> choices;
	private HashMap<Integer, ImageView> deck;
	private HashMap<Integer, ImageView> playerHands;
	
	/*
	 * Prototype: main(String[] args)
	 *   Purpose: Entry point for the game
	 * */
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
		setUpGUI(primaryStage);
		setUpGame();
	}
	
	private void setUpGame() 
	{
		/* Initialize data structures to hold on to cards on game board */
		deck = new HashMap<Integer, ImageView>();
		playerHands = new HashMap<Integer, ImageView>();
		
		/* Get rigged game if applies*/
		String file_input = use_file_input();
		
		/* Start up timer */
		playerTimer = new Label("");
		
		/* Initialize game logic */
		game = new TileRummyMain();
		game.initialize(file_input, getPlayerStrategies()); 
		
		/* Deal hands */
		placeDeck(game.initDeck);
		dealHand(game.player0.getHand(), game.player1.getHand(), game.player2.getHand(), game.player3.getHand());
		
		/* Start game */
		game.playGame();
	}

	private void setUpGUI(Stage primaryStage) 
	{
		setPanePos();	
		root = new Pane();
		scene = new Scene(root, screenWidth, screenHeight);
		setUpscene();
		initUI(primaryStage, scene);
		handleStage(primaryStage, scene);
	}

	/*
	 * Prototype: getPlayerStrategies()
	 *   Purpose: Get which players will play and a riged file input
	 */
	private ArrayList<String> getPlayerStrategies()
	{
		choices = new ArrayList<String>();
		optionsBox.display();
		
		if(optionsBox.choices.size() > 3)
		{
			/* Add user options assuming they selected each one*/
			choices.addAll(optionsBox.choices);
		}
		else
		{
			/* Default options if they didn't pick all players*/
	        choices.add("Player");
	        choices.add("AI Strategy 1");
	        choices.add("AI Strategy 2");
	        choices.add("AI Strategy 3");
		}
		
		/* Check which players were selected */
		System.out.println(choices.get(0));
		System.out.println(choices.get(1));
		System.out.println(choices.get(2));
		System.out.println(choices.get(3));
		return choices;
	}
	
	/*
	 * Prototype: setUpscene()
	 *   Purpose: Set the scene as the target to drop images
	 * */
	@SuppressWarnings("restriction")
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
	@SuppressWarnings("restriction")
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
		Text playerInfo, playerInfo2, playerInfo3, playerInfo4;
		Image deckImage, player1BoardImage, player2BoardImage, player3BoardImage, player4BoardImage;
		ImageView imgDeckView, imgPlayer1View, imgPlayer2View, imgPlayer3View, imgPlayer4View;
		
		ArrayList<ImageView> decks = new ArrayList<ImageView>();
		ArrayList<Text> playerTexts = new ArrayList<Text>();
		
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
		
		imgDeckView = new ImageView(deckImage);imgPlayer1View = new ImageView(player1BoardImage);imgPlayer2View = new ImageView(player2BoardImage);
		imgPlayer3View = new ImageView(player3BoardImage);imgPlayer4View = new ImageView(player4BoardImage);
		
		decks.add(imgDeckView);decks.add(imgPlayer1View);decks.add(imgPlayer2View);decks.add(imgPlayer3View);decks.add(imgPlayer4View);
		decks = setDeckPoistions(decks);
				
		/* Set text fields */
		playerInfo = new Text();playerInfo2 = new Text();
		playerInfo3 = new Text();playerInfo4 = new Text();
		
		/* Store text filed to pass to function */
		playerTexts.add(playerInfo);playerTexts.add(playerInfo2);
		playerTexts.add(playerInfo3);playerTexts.add(playerInfo4);
		
		/* Set positions to player names */
		playerTexts = setTextPos(playerTexts);		
		
		/* Add player names to stage */
	    root.getChildren().addAll(decks);
	    root.getChildren().addAll(playerTexts);

	    return true;
	}
	
	/*
	 * Prototype: setTextPos(ArrayList<Text> playerTexts) 
	 *   Purpose: Helper function to set player names in game
	 */
	private ArrayList<Text> setTextPos(ArrayList<Text> playerTexts) 
	{
		//TODO set proper positions
		playerTexts.get(0).setFont(new Font(50));
		//playerTexts.get(0).setText(optionsBox.choices.get(0));
		playerTexts.get(0).setText("Player 1");
		playerTexts.get(0).setY(screenWidth - screenWidth*0.465);
		playerTexts.get(0).setX(0);

		playerTexts.get(1).setFont(new Font(50));
		//playerTexts.get(1).setText(optionsBox.choices.get(1));
		playerTexts.get(1).setText("AI Strategy 1");
		playerTexts.get(1).setY(screenWidth - screenWidth*0.465);
		playerTexts.get(1).setX(0);
		
		playerTexts.get(2).setFont(new Font(50));
		//playerTexts.get(2).setText(optionsBox.choices.get(2));
		playerTexts.get(2).setText("AI Strategy 2");
		playerTexts.get(2).setY(screenWidth - screenWidth*0.465);
		playerTexts.get(2).setX(0);
		
		playerTexts.get(3).setFont(new Font(50));
		playerTexts.get(3).setText("AI Strategy 3");
		playerTexts.get(3).setY(screenWidth - screenWidth*0.465);
		playerTexts.get(3).setX(0);
		
		return playerTexts;
	}

	private ArrayList<ImageView> setDeckPoistions(ArrayList<ImageView> decks) 
	{
		/* Deck next to right player */
		decks.get(0).setX(screenWidth - screenWidth*0.13); 
		decks.get(0).setY(screenHeight/16 + screenHeight*0.05); 

		/* Player bottom of screen */
		decks.get(1).setX(screenWidth - screenWidth*0.92); 
		decks.get(1).setY(screenHeight - screenHeight*0.2025); 
				
		/* Left side player*/
		decks.get(2).setX(0); 
		decks.get(2).setY(screenHeight/16 + screenHeight*0.05); 
		
		/* Player opposite side of screen */
		decks.get(3).setY(screenHeight*0.0125); 
		decks.get(3).setX(screenWidth - screenWidth*0.99); 

		/* Right side player */
		decks.get(4).setX(screenWidth - screenWidth*0.05); 
		decks.get(4).setY(screenHeight/16 + screenHeight*0.05); 
		return decks;
	}

	/*
	 * Prototype: handleButtonEvents(Pane root) 
	 * 	 Purpose: Create / add buttons. Handle events for buttons
	 * */
	@SuppressWarnings("restriction")
	private void handleButtonEvents() 
	{
		//Create buttons 
		btnFinish = new Button("Finish Move");
		Button btnExit = new Button("Exit Game");
	
		btnFinish.setLayoutX(200);
		btnFinish.setLayoutY(300);
		
		btnExit.setLayoutX(300);
		btnExit.setLayoutY(200);
		
		//Set events
		btnFinish.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				btnFinish.setDisable(false);
				if (game.isValidTable(game.player0.tilesOnField)) {
					game.field = game.player0.tilesOnField;
					game.player0.oldHand = game.player0.hand;
				} else {
					//reset table to previous state and draw tile 
					game.player0.drawTile(game.initDeck);
					game.player0.hand = game.player0.oldHand;
				}
				game.checkPlays(game.player0.tilesOnField);
				game.playGame();
				updateTiles();
			}
		});
		
		btnExit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				System.exit(0);
			}
		});
		
		root.getChildren().addAll(btnFinish, btnExit);
		
	}
		
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
		
	/*   prototype: placeDeck(ArrayList<Tile> deck)
	 *   purpose: Place all cards given in pile
	 * */
	public boolean placeDeck(ArrayList<Tile> deckTemp)
	{
		//In order to avoid multiple calls to place deck 
		//adding the same tiles we will reset the stored deck for the new one
		deck = new HashMap<Integer, ImageView>();
		
		int i = 0;
		double offsetY = 0.0;
		
		if(startGame) 
		{
			sayMsg("Place Deck");
			startGame = false;
		}
		
		ImageView tempImageView;

		/* Set deck position and add to GUI */
		for(Tile card : deckTemp)
		{
			/* Every 4th card reset x position */
			i += 1;
			if(i%4 == 0)
				offsetY += screenHeight*0.0265;
			
			//Create card with given position 
			tempImageView = createCard(i, card, offsetY);
			
			//Store the tile ID and the image view
			deck.put(card.getId(), tempImageView);	
			
			//Add card to the game
			root.getChildren().add(tempImageView);
		}
		return true;
	}
	
	/*   prototype: createCard(int i, Tile card, double offsetY) 
	 *   purpose: Place card in side deck
	 * */
	private ImageView createCard(int i, Tile card, double offsetY) 
	{
		ImageView tempImageView;
		
		//Randomly position tiles on deck
		double randNum = 0.0;
		Random rand = new Random();

		Image imageHolder = card.getImage();
		
		/* Set drag and drop events */
		tempImageView = setUpCardEvents(imageHolder, card);
		
		//Set width and height
		tempImageView.setFitHeight(screenHeight/19);
		tempImageView.setFitWidth(screenWidth*0.0225);

		//Set Pos
		randNum = (0.0225) * rand.nextDouble();
		tempImageView.setX(screenWidth - screenWidth*0.10 + screenWidth*randNum*Math.pow(-1, i)); 
		tempImageView.setY(screenHeight/16 + screenHeight*0.06 + offsetY);
		
		return tempImageView;
	}
	
	/*   prototype: setUpCardEvents(Image imageHolder, Tile tile)
	 *   purpose: Add events to a card such as drag and drop
	 * */
	private ImageView setUpCardEvents(Image imageHolder, Tile tile) 
	{
		ImageView tempImageView = new ImageView(imageHolder);
		tempImageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				int id = tile.getId();
				int playerTurn = game.getTurn();
				boolean setBoolean = true;
				
				for (int i = 0; i < game.player0.tilesOnField.size(); i++) { // checks to see if the tile is in our dummy field
					if(game.player0.tilesOnField.get(i).contains(tile)) {
						inFieldOrHand = true;
					}
				}
				for (int i = 0; i < game.field.size(); i++) { // checks to see if the tile is in our actual field
					if(game.field.get(i).contains(tile)) {		
						inFieldOrHand = true;
					}
				}
				for(int i = 0; i <game.rollbackField.size(); i++) {
					if(game.rollbackField.get(i).contains(tile)) {
						inFieldOrHand = true;
					}
				}
				if(game.player0.oldHand.contains(tile)) {
					inFieldOrHand = true;
				}
				if(playerTurn == 0) { // players turn
					if(inFieldOrHand){
						tempImageView.setVisible(false);
						tempImageView.setX(event.getSceneX());
						tempImageView.setY(event.getSceneY());
						tempImageView.setVisible(true);
						// if card being dragged in on the table, remove it
						GUI.removeTileFromTable(tile, game.field);
						game.player0.removeTileFromHand(tile);
					}
				}else if(playerTurn == 1) { // AI1
					System.out.println("AIs 1's turn or not your card");
				}else if(playerTurn == 2) { // AI2
					System.out.println("AIs 2's turn or not your card");
				}else { // AI3 
					System.out.println("AIs 3's turn or not your card");
				}

			}
		});
		
		tempImageView.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int id = tile.getId();
				int playerTurn = game.getTurn();

				
				double xCord = tempImageView.getX();
				double yCord = tempImageView.getY();
				tempImageView.getId();
				System.out.println(xCord + " " + yCord + " " + tempImageView.getId());
				tile.setx(xCord);
				tile.sety(yCord);
				if(playerTurn == 0) {
					if(inFieldOrHand) {
						game.checkPerimeter(xCord,yCord, id, tile);
					}
				}
				System.out.println(game.player0.tilesOnField);
				System.out.println("Current Field" + game.field);
				System.out.println("rollback Field" + game.rollbackField);
				inFieldOrHand = false;
			}
		});
		
		tempImageView.setId(Integer.toString(tile.getId()));
		return tempImageView;
	}
	
	public static boolean removeTileFromTable(Tile tile, ArrayList<ArrayList<Tile>> table) {
		//return true if tile was removed from table
		for (ArrayList<Tile> meld: table) {
			for (Tile t: meld) {
				if (tile.getId() == t.getId()) {
					meld.remove(t);
					return true;
				}
			}
		}
		return false;
	}

	//TODO these functions should be refactored 
	public boolean dealHandPlayer1(ArrayList<Tile> playerHand)
	{
		ImageView tempImageView;
		int i = 0;

		for(Tile tile:playerHand)
		{
			/* Set drag and drop events */		
			tempImageView = setUpCardEvents(tile.getImage(), tile);

			//Set width and height
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);
			
			tempImageView.setX(screenWidth - screenWidth*0.92 + i*screenWidth*0.025); 
			tempImageView.setY(screenHeight - screenHeight*0.2025); 
			i += 1;
			
			playerHands.put(tile.getId(), tempImageView);
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
			/* Set drag and drop events */		
			tempImageView = setUpCardEvents(tile.getImage(), tile);

			//Set width and height
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);
			
			tempImageView.setX(screenWidth*0.0125); 
			tempImageView.setY(screenHeight*0.86 - i*screenHeight*0.0525); 
			i += 1;
			
			playerHands.put(tile.getId(), tempImageView);
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
			/* Set drag and drop events */		
			tempImageView = setUpCardEvents(tile.getImage(), tile);

			//Set width and height
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);
			
			tempImageView.setX(screenWidth - screenWidth*0.92 + i*screenWidth*0.025); 
			tempImageView.setY(screenHeight*0.025); 
			i += 1;
			
			playerHands.put(tile.getId(), tempImageView);
			root.getChildren().add(tempImageView);
		}
		
		return true;
	}
	
	public boolean dealHandPlayer4(ArrayList<Tile> playerHand)
	{
		ImageView tempImageView;
		int i = 1;

		for(Tile tile:playerHand)
		{
			/* Set drag and drop events */		
			tempImageView = setUpCardEvents(tile.getImage(), tile);

			//Set width and height
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);

			//Set Pos
			tempImageView.setX(screenWidth*0.965); 
			tempImageView.setY(screenHeight*0.86 - i*screenHeight*0.0525); 
			i += 1;
			
			playerHands.put(tile.getId(), tempImageView);
			root.getChildren().add(tempImageView);
		}
		return true;
	}
	
	/*   prototype: dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, 
	 *   ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	 *   purpose: deal each card to player
	 * */
	public boolean dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	{
		System.out.println("Deal hand called");
		
		/* Reset playerHands */
		playerHands = new HashMap<Integer, ImageView>();
		double totalRun = 0;
		isNewGame();
		
		/* Sort hand to ensure each players card are in color ascending order */
		sortHand(p1Hand);sortHand(p2Hand);
		sortHand(p3Hand);sortHand(p4Hand);
		
		/* Place tiles on player hands */
		dealHandPlayer1(sortHand(p1Hand));dealHandPlayer2(sortHand(p2Hand));
		dealHandPlayer3(sortHand(p3Hand));dealHandPlayer4(sortHand(p4Hand));

		return true;
	}
	
	/*
	 * Prototype: isNewGame()
	 *   Purpose: Check if a new game was started
	 */
	private void isNewGame() 
	{
		if(startGame) 
		{
			sayMsg("Hands being dealt");
		}	
	}

	/*
	 * Prototype: sayMsg(String msg, int delay)
	 * 	 Purpose: General function to prompt a message and add a delay
	 * */
	public static boolean sayMsg(String msg)
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
			case "player1": 
				result += " 1"; 
				btnFinish.setDisable(false);
				break;
			case "player2": result += " 2"; 
				btnFinish.setDisable(true); 
				break;
			case "player3": result += " 3"; 
				btnFinish.setDisable(true); 
				break;
			case "player4": result += " 4"; 
				btnFinish.setDisable(true); 
				break;
			default:
				 return false;
		}
		sayMsg(result);
		
		return true;
	}
	
	//TODO fix this
	public boolean updateTiles()
	{
		/* New cards to be placed */
		//ArrayList<ImageView> tempField = new ArrayList<ImageView>();
		ArrayList<ArrayList<Tile>> newCardsTemp;
		newCardsTemp = isRollBack(game.checkField());		
		ImageView tempImageView;
		
		/* Hide all cards from player hands and from field and from deck */
		for(Integer key: deck.keySet())
		{
			deck.get(key).setVisible(false);
		}
		
		for(Integer key: playerHands.keySet())
		{
			playerHands.get(key).setVisible(false);
		}
		
		//TODO hide all from field
		
		/* Reset data structures */
		deck = new HashMap<Integer, ImageView>();
		playerHands = new HashMap<Integer, ImageView>();
		
		/* Create new cards and add */
		int rowCounter = 1;
		int colCounter = 0;
		
		System.out.println("Field size is: " + game.field.size());
		
		/* Display field to table */
		for(ArrayList<Tile> tileList: newCardsTemp)
		{
			colCounter +=1;
			for(Tile tiles: tileList)
			{
				//TODO display on mane field
				tempImageView = isPlayed(tiles);
				tempImageView = setImageViewPos(tempImageView, rowCounter, colCounter);
				tiles.setx((colCounter*30)+50);
				tiles.sety(rowCounter*75);
				
				//Check this since it is the temp field
				//TODO figure out how to store field
				root.getChildren().add(tempImageView);
				
				colCounter+=1;
				
				if(colCounter > 30) 
				{
					rowCounter+=1;
					colCounter = 1;
				}
				
				tempField.add(tempImageView);
			}
		}
		
		/* Show player hands */
		//TODO show field
		placeDeck(game.initDeck); 
		dealHand(game.player0.getHand(),  game.player1.getHand(),  game.player2.getHand(),  game.player3.getHand());
		return true;
	}
	
	private ImageView setImageViewPos(ImageView tempImageView, int rowCounter, int colCounter) {
		tempImageView.setFitHeight(screenHeight/19);
		tempImageView.setFitWidth(screenWidth*0.0225);
		tempImageView.setY((rowCounter*75));
		tempImageView.setX((colCounter*30) + 50);
		return tempImageView;
	}

	private ImageView isPlayed(Tile tiles) 
	{
		if(game.justPlayed.contains(tiles)) 
		{
			return setUpCardEvents(tiles.getImage2(0), tiles);
		}else 
		{
			return setUpCardEvents(tiles.getImage2(1), tiles);
		}
	}

	private boolean updateHelper(ArrayList<Tile> cards)
	{
		ImageView tempImageView;
		for(Tile tiles: cards)
		{
			tempImageView = setUpCardEvents(tiles.getImage(), tiles);
			tempImageView.setFitHeight(screenHeight/19);
			tempImageView.setFitWidth(screenWidth*0.0225);
			tempImageView.setX(tiles.getX());
			tempImageView.setY(tiles.getY());
			deck.put(tiles.getId(), tempImageView);
			root.getChildren().add(tempImageView);
		}
		return true;
	}
	
	/*
	 * Prototype: isRollBack(boolean checkField)
	 *   Purpose: Check if we roll back player move
	 */  
	private ArrayList<ArrayList<Tile>> isRollBack(boolean checkField) 
	{
		if(game.checkField()) 
		{
			System.out.println("Update tiles");
			return game.field;
		}
		else 
		{
			game.rollbackNow();
			return game.rollbackField;
		}
	}

	
	public void playerTimer() {
		root.getChildren().add(playerTimer);
		playerTimer.setAlignment(Pos.BOTTOM_RIGHT);
		playerTimer.setFont(new Font(30));
		playerTimer.setContentDisplay(ContentDisplay.TOP);
		
		t.scheduleAtFixedRate(new TimerTask() {
	        
	        int minutes = 2;
            int seconds = 0;
            @Override
	        public void run() {
	            Platform.runLater(() -> {
	            	
	            	playerTimer.setText(minutes + "m " + seconds + "s\n");
			          
		            if (seconds == 0) {
		            	if (minutes == 0) {
		            		playerTimer.setText("");;
		            		cancel();
		            		return;
		            	}
		            	seconds = 59;
		            	minutes--;
		            } else {
		            	seconds--;
		            }
	            	
	            });
	        }
	    }, 1000, 1000);
	}
	
	public String use_file_input() {
		String file_input = "";
		
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("File input");
		dialog.setHeaderText("Enter a file to deal from or leave it blank");
	

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    file_input = result.get();
		    System.out.println("'" + file_input + "'");
		}
		return file_input;
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

