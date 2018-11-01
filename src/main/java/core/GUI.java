package core;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	
	/* TODO remove this when done*/
	public static final String[] suites = {"R", "B", "G", "Y"};
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
		setPanePos();	
		root = new Pane();
		scene = new Scene(root, screenWidth, screenHeight);
		deck = new HashMap<String, Image>();
		setUpscene();
		initUI(primaryStage, scene);
		handleStage(primaryStage, scene);
		placeDeck(TileRummyMain.buildDeck(suites, values));
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
					screenWidth - 0.05*screenWidth, screenHeight ,true,true);

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
		imgPlayer1View.setX(screenWidth - screenWidth*0.99); 
		imgPlayer1View.setY(screenHeight - screenHeight*0.1025); 
				
		/* Left side player*/
		imgPlayer2View.setX(0); 
		imgPlayer2View.setY(screenHeight/16 + screenHeight*0.05); 
		
		/* Player opposite side of screen */
		imgPlayer3View.setY(screenHeight*0.0125); 
		imgPlayer3View.setX(screenWidth - screenWidth*0.99); 

		/* Right side player */
		imgPlayer4View.setX(screenWidth - screenWidth*0.05); 
		imgPlayer4View.setY(screenHeight/16 + screenHeight*0.05); 
		
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
				//startGame();
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
/*
    private void enableDragging(Node node) {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        node.setOnMousePressed(event -> mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY())));
        node.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseAnchor.get().getX();
            double deltaY = event.getSceneY() - mouseAnchor.get().getY();
            //node.relocate(node.getLayoutX() + deltaX, node.getLayoutY() + deltaY);
            //mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
            
        });
    }
    */
	
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
		tempImageView = setUpCardEvents(imageHolder);

		//Set width and height
		tempImageView.setFitHeight(screenHeight/19);
		tempImageView.setFitWidth(screenWidth*0.0225);

		//Set Pos
		randNum = (0.0225) * rand.nextDouble();
		tempImageView.setX(screenWidth - screenWidth*0.10 + screenWidth*randNum*Math.pow(-1, i)); 
		tempImageView.setY(screenHeight/16 + screenHeight*0.06 + offsetY);
		
		
		return tempImageView;
	}
	private ImageView setUpCardEvents(Image imageHolder) {
		final ImageView imgView = new ImageView(imageHolder);
		final DoubleProperty tempX = new SimpleDoubleProperty();
		final DoubleProperty tempY = new SimpleDoubleProperty();
		final DoubleProperty dragX = new SimpleDoubleProperty();
		final DoubleProperty dragY = new SimpleDoubleProperty();
		final DoubleProperty tempX2 = new SimpleDoubleProperty();
		final DoubleProperty tempY2 = new SimpleDoubleProperty();
		
		ImageView tempImageView = new ImageView(imageHolder);
		tempImageView.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent event) {
				tempImageView.setVisible(false);
				tempImageView.setX(event.getSceneX());
				tempImageView.setY(event.getSceneY());
				tempImageView.setVisible(true);
			}
		});
		return tempImageView;
	}
/*
	private ImageView setUpCardEvents(Image imageHolder) 
	{
		final ObjectProperty<Point2D> anchor = new SimpleObjectProperty<>();
		final ImageView imgView = new ImageView(imageHolder);
		final DoubleProperty tempX = new SimpleDoubleProperty();
		final DoubleProperty tempY = new SimpleDoubleProperty();
		final DoubleProperty dragX = new SimpleDoubleProperty();
		final DoubleProperty dragY = new SimpleDoubleProperty();
		final DoubleProperty tempX2 = new SimpleDoubleProperty();
		final DoubleProperty tempY2 = new SimpleDoubleProperty();

		    
		ImageView tempImageView = new ImageView(imageHolder);
		tempImageView.setOnDragDetected(new EventHandler<MouseEvent>() {
	        public void handle(MouseEvent event) 
	        {
	        	tempImageView.setVisible(false);
	            ClipboardContent content = new ClipboardContent();
	            content.putImage(imageHolder);
	            Dragboard db = tempImageView.startDragAndDrop(TransferMode.ANY);
	            db.setDragView(imageHolder,screenWidth*0.0225 ,screenHeight/19);
	            db.setContent(content); 
	            event.consume();
	        }
	    });
	    
	    imgView.setOnDragOver(new EventHandler<DragEvent>(){

	        @Override
	        public void handle(DragEvent event) {
	            imgView.toFront();
	            
	            dragX.set(event.getSceneX() - anchor.get().getX());
	            dragY.set(event.getSceneY() - anchor.get().getY());
	            imgView.setOpacity(0.5);

	            tempX2.set(tempX.get() + dragX.get());
	            tempY2.set(tempY.get() + dragY.get());

	            imgView.setTranslateX(tempX2.get());
	            imgView.setTranslateY(tempY2.get());

	            event.consume();
	        }
	    });	
	    
	    imgView.setOnDragDone(new EventHandler<DragEvent>() {
	        public void handle(DragEvent event) 
	        {
	            ClipboardContent content = new ClipboardContent();
	            content.putImage(imageHolder);
	            Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
	            db.setDragView(imageHolder, screenWidth*0.0225, screenHeight/19); 
	            db.setContent(content); 
	            tempImageView.setVisible(true);
	            event.consume();	        
	        }
	    });
	    
	    return tempImageView;
	}
 */
	
	/*   prototype: dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, 
	 *   ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	 *   purpose: deal each card to player
	 * */
	public boolean dealHand(ArrayList<Tile> p1Hand, ArrayList<Tile> p2Hand, ArrayList<Tile> p3Hand, ArrayList<Tile> p4Hand)
	{
		sayMsg("Hand being dealt");
		return false;
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
}


