// Sigh.. Why are you looking at this? The code is so extremely messy, but hey! It works!

package me.mnpn.uppercase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UppercaseGUI extends Application {

	String version = "1.3";
	String content = "";

	private static TextArea text;
	private static TextArea output;
	public static final FileChooser chooser = new FileChooser();

	int timespressed = 0;

	Button copy = new Button("Copy output");
	Random rand = new Random();

	String helptext = "Help in 3 steps:\n" + "Step 1. Enter anything into the upper TextBox.\n"
			+ "Step 2. Press the \"UppercasE\" button.\n"
			+ "Step 3. Read the output from the lower TextBox, and optionally copy the content to your clipboard.\n\n"
			+ "You can also open a file using the \"Open from file\" button, or just drag the file into the upper TextBox. Saving is also possible using the \"Save to file\" button.\n"
			+ "The \"UppercasE Automatically\" checkbox is checked by default. It simply automatically UppercasEs your text as you type.\n\n"
			+ "That wasn't so hard now, was it?";

	public void start(Stage s) {
		s.setTitle("UppercasE " + version);
		s.getIcons().add(new Image("file:icon.png")); // Use "file:res/icon.png" during development, but when you export you need to modify this for the icon to work.

		VBox layout = new VBox();
		HBox input = new HBox();
		input.setAlignment(Pos.CENTER);

		GridPane toprow = new GridPane();
		toprow.setAlignment(Pos.CENTER);
		toprow.setHgap(10);
		toprow.setVgap(10);
		toprow.setPadding(new Insets(0, 0, 10, 0));

		CheckBox auto = new CheckBox("UppercasE Automatically");
		auto.setAlignment(Pos.TOP_LEFT);
		auto.setSelected(true);
		toprow.add(auto, 0, 1);

		Button saveTo = new Button("Save to file");
		saveTo.setOnAction(e -> {
			File f = chooser.showSaveDialog(s);
			if (f == null)
				return;
			try {
				Files.write(f.toPath(), output.getText().getBytes());
			} catch (IOException err) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("An error occured. That's strange! Please report this to the developer.");
				alert.setContentText("Error: " + err.getMessage());
				alert.showAndWait();
				return;
			}
		});

		Button file = new Button("Open from file");
		file.setOnAction(e -> {
			open(s);
		});
		toprow.add(file, 1, 1);
		toprow.add(saveTo, 2, 1);
		layout.getChildren().add(toprow);

		text = new TextArea();
		text.prefWidthProperty().bind(s.widthProperty().divide(1.5));
		text.setPromptText("Text to UppercasE");
		text.setEditable(true);
		text.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(final ObservableValue<? extends String> observable, final String oldValue,
					final String newValue) {
				if (auto.isSelected()) {
					copy.setText("Copy output");
					uppercase();
				}
			}
		});
		layout.getChildren().add(text);

		Button justdoitlmao = new Button("UppercasE");
		justdoitlmao.setOnAction(e -> {
			copy.setText("Copy output");
			timespressed++;
			uppercase();

			if (timespressed == 5) {
				s.setTitle("Stop.");
			}
			if (timespressed == 10) {
				s.setTitle("Just stop it.");
			}
			if (timespressed == 20) {
				s.setTitle("Just stop it. End yourself.");
			}
			if (timespressed == 50) {
				s.setTitle("*POKES BACK*");
			}
			if (timespressed == 100) {
				s.setTitle("***PROGRAM STRIKES BACK***");
			}
			if (timespressed == 125) {
				s.setTitle("UppercasE " + version);
			}
		});
		layout.getChildren().add(justdoitlmao);

		text.setOnDragOver(e -> {
			if (e.getDragboard().hasFiles())
				e.acceptTransferModes(TransferMode.COPY);
		});
		text.setOnDragDropped(e -> {
			Dragboard d = e.getDragboard();
			if (!d.hasFiles())
				return;
			List<File> list = d.getFiles();
			if (list.isEmpty())
				return;
			e.setDropCompleted(true);

			File f = list.get(0);
			try {
				content = new String(Files.readAllBytes(Paths.get(f.toString())));
			} catch (IOException e1) {
				e1.printStackTrace();

				new Alert(AlertType.ERROR, "Couldn't read file.", ButtonType.OK).showAndWait();
				return;
			}
			text.setText(content);
		});

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

	private void open(Stage s) {
		File f = chooser.showOpenDialog(s);
		try {
			if (f != null)
				content = new String(Files.readAllBytes(Paths.get(f.toString())));
		} catch (IOException e1) {
			e1.printStackTrace();
			new Alert(AlertType.ERROR, "Couldn't read file.", ButtonType.OK).showAndWait();
			return;
		}
		if (f != null)
			text.setText(content);
	}

	private void uppercase() {
		StringBuilder sb = new StringBuilder(text.getLength());
		for (char c : text.getText().toCharArray())
			sb.append(rand.nextBoolean() ? Character.toLowerCase(c) : Character.toUpperCase(c));
		output.setText(sb.toString());
	}
}
