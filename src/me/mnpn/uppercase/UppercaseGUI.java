// Sigh.. Why are you looking at this? The code is so extremely messy, but hey! It works!

package me.mnpn.uppercase;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UppercaseGUI extends Application{
	
	String version = "1.0";
	
	private static TextArea text;
	private static TextArea output;
	
	int	timespressed = 0;
	Button copy = new Button("Copy output");
	
	Random rand = new Random();
	
	String helptext = "Help in 3 steps:\n"
			+ "Step 1. Enter anything into the upper TextBox.\n"
			+ "Step 2. Press the \"UppercasE\" button.\n"
			+ "Step 3. Read the output from the lower TextBox, and optionally copy the content to your clipboard.\n\n"
			+ "That wasn't so hard now, was it?";
	
	public void start(Stage s){
		s.setTitle("UppercasE " + version);
		
		VBox layout = new VBox();
		
		HBox input = new HBox();
		input.setAlignment(Pos.CENTER);
		
		text = new TextArea();
		text.prefWidthProperty().bind(s.widthProperty().divide(1.5));
		text.setPromptText("Text to UppercasE");
		text.setEditable(true);
		layout.getChildren().add(text);
		
		Button justdoitlmao = new Button("UppercasE");
		justdoitlmao.setOnAction(e -> {
			copy.setText("Copy output");
			timespressed++;
		    StringBuilder sb = new StringBuilder(text.getLength());

		    for (char c : text.getText().toCharArray())
		        sb.append(rand.nextBoolean() ? Character.toLowerCase(c) : Character.toUpperCase(c));		    
			
			output.setText(sb.toString());
			if(timespressed == 5){
				s.setTitle("Just stop it. End yourself.");
			}
			});
		layout.getChildren().add(justdoitlmao);
		output = new TextArea();
		output.setEditable(false);
		output.setPromptText("Output will be shown here.");
		layout.getChildren().add(output);
		output.setOnMouseClicked(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Expanded output");
			
			TextArea area = new TextArea(output.getText());
			area.setEditable(false);
			alert.getDialogPane().setContent(area);
			alert.showAndWait();
		});
		
		HBox save = new HBox();
		
		copy.setOnAction(e -> {
			ClipboardContent c = new ClipboardContent();
			c.putString(output.getText());
			Clipboard.getSystemClipboard().setContent(c);
			copy.setText("Copied!");
		});
		
		Button help = new Button("Help");
		help.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Help");
			alert.setTitle("I am pissed at you for not knowing how to use this.");
			TextArea area = new TextArea(helptext);
			area.setEditable(false);
			alert.getDialogPane().setContent(area);
			alert.setResizable(true);
			alert.showAndWait();
		});
		save.getChildren().add(copy);
		save.getChildren().add(help);

		layout.getChildren().add(save);
		
		Scene scene = new Scene(layout, 1024, 512);
		s.setScene(scene);
		s.show();
	}
}
