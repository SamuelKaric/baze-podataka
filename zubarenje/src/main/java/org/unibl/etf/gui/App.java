package org.unibl.etf.gui;

import java.io.FileInputStream;
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		//FileInputStream fxmlStream = new FileInputStream(".\\src\\main\\java\\org\\unibl\\etf\\gui\\FrontPage.fxml");
		InputStream fstream = this.getClass().getResourceAsStream("FrontPage.fxml");
		AnchorPane root = (AnchorPane) loader.load(fstream);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
