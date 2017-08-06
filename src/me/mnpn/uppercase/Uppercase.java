package me.mnpn.uppercase;

import javafx.application.Application;

public class Uppercase {

	public static void main(String[] args) {
		if (args.length >= 1) {
			System.out.println("Yeah.. I don't really do arguments.");
			return;
		}
		Application.launch(UppercaseGUI.class);
	}

}
