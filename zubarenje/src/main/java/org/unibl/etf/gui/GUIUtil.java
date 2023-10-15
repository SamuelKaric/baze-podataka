package org.unibl.etf.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.unibl.etf.gui.contollers.FrontPageController;
import org.unibl.etf.gui.contollers.TreatmentContoller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIUtil {

	private static String FXML_root_location = ".\\src\\main\\java\\org\\unibl\\etf\\gui";
	public static AnchorPane home = null;
	public static FrontPageController r = null;

	public static FrontPageController builder(FrontPageController fpc) {
		if(home == null && fpc != null) {
			home = fpc.getLog_in_pane();
			r = fpc;
		}
		return r;
	}

	public static String findFLXM(String FXMLname) {
		return FXML_root_location + "/" + FXMLname + ".fxml";
	}

	public TreatmentContoller pupupPane(Pane target, String name, String win_name) {
	    	try {
		    	FXMLLoader loader = new FXMLLoader();
		    	//FileInputStream fxmlStream = new FileInputStream(GUIUtil.findFLXM(name));
		    	InputStream fstream = this.getClass().getResourceAsStream(name);
	    		Parent root = (Parent) loader.load(fstream);
	    		
	    		TreatmentContoller controller = (TreatmentContoller) loader.getController();
	    		Scene scene = new Scene(root);
	    		Stage stage = new Stage();
	    		stage.setScene(scene);
	    		stage.setTitle(win_name);
	    		stage.show();
	    		return controller;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;

	}

	public Object swapPane(Pane target, String name) {
		try {
	    	FXMLLoader loader = new FXMLLoader();
	    	Pane p = null;
	    	if(name != null) {
	    	//FileInputStream fxmlStream = new FileInputStream(GUIUtil.findFLXM(name));
	    	InputStream fstream = this.getClass().getResourceAsStream(name + ".fxml");
	    	 p = (AnchorPane) loader.load(fstream);
	    	}else
	    		p=	home;
	    	target.getChildren().remove(0);
	    	target.getChildren().add(p);
	    	return loader.getController();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
