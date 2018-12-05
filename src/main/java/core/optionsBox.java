package core;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.geometry.*;

public class optionsBox {
	public static ArrayList<String> choices;
	
    @SuppressWarnings("restriction")
	public static void display() {
    	choices = new ArrayList<String>();
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int screenWidth = screenSize.width;
		int screenHeight = 	screenSize.height;
		
    	Stage gameOptions = new Stage();

        gameOptions.initModality(Modality.APPLICATION_MODAL);
        gameOptions.setTitle("Game Options");
        gameOptions.setMinWidth(screenHeight*0.25);

        Label info = new Label();
        Label infoTimer = new Label();
        info.setText("Please select the players");
        infoTimer.setText("Would you like a timer?");
        ComboBox comboBox1 = new ComboBox();
        ComboBox comboBox2 = new ComboBox();
        ComboBox comboBox3 = new ComboBox();
        ComboBox comboBox4 = new ComboBox();
        ComboBox comboBox5 = new ComboBox();
        
        ArrayList<String> options = new ArrayList<String>();
        options.add("Player");
        options.add("AI Strategy 1");
        options.add("AI Strategy 2");
        options.add("AI Strategy 3");
        options.add("AI Strategy 4");
        
        comboBox1.getItems().addAll(options);
        comboBox2.getItems().addAll(options);
        comboBox3.getItems().addAll(options);
        comboBox4.getItems().addAll(options);
        comboBox5.getItems().add("Yes");
        comboBox5.getItems().add("No");
        
        Button btnFinish = new Button("Finish");
        
        btnFinish.setOnAction(e -> {
        	//Slot 1
        	if(comboBox1.getValue() == null)
        	{
        		choices.add("Player");
        	}
        	else
        	{
        		choices.add((String) comboBox1.getValue());
        	}
        	
        	//Slot 2
        	if(comboBox2.getValue() == null)
        	{
        		choices.add("AI Strategy 1");
        	}
        	else
        	{
        		choices.add((String) comboBox2.getValue());
        	}
        	
        	//Slot 3
        	if(comboBox3.getValue() == null)
        	{
        		choices.add("AI Strategy 2");
        	}
        	else
        	{
        		choices.add((String) comboBox3.getValue());
        	}
        	
        	//Slot 4
        	if(comboBox4.getValue() == null)
        	{
        		choices.add("AI Strategy 3");
        	}
        	else
        	{
        		choices.add((String) comboBox4.getValue());
        	}
        	
        	//Slot 5
        	if(comboBox5.getValue() == null)
        	{
        		choices.add((String) "No");
        	}
        	else
        	{
        		choices.add((String) comboBox5.getValue());
        	}
        	gameOptions.close();  	
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(info, comboBox1, comboBox2, comboBox3, comboBox4, infoTimer, comboBox5, btnFinish);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        gameOptions.setScene(scene);
        gameOptions.showAndWait();
    }

}