package org.unibl.etf.gui.contollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.entity.Drzava;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.gui.GUIUtil;
import org.unibl.etf.wrapper.WrapperDrzava;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DrzavaContoller implements Initializable{

    @FXML
    private Button add_button;

    @FXML
    private Button city_button;

    @FXML
    private AnchorPane county_pane;

    @FXML
    private Button delete_button;

    @FXML
    private TableColumn<Drzava, Integer> id_column;

    @FXML
    private TextField id_field;

    @FXML
    private TableColumn<Drzava, String> name_column;

    @FXML
    private TextField name_field;

    @FXML
    private Button populate_button;

    @FXML
    private TableView<Drzava> table;

    @FXML
    private Button update_button;

    @FXML
    private Label warning_label;


    @FXML
    void add(ActionEvent event) {
    	wClear();
    	try {
	        Drzava in = new Drzava(null, name_field.getText(), null);
	    	WrapperDrzava.insert(in);
    	}catch(InputException e) {
    		warning_label.setText(e.getMessage());
    	}
    }

    @FXML
    void delete(ActionEvent event) {
    	wClear();
    }

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		wClear();
	    table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	id_field.setText(newSelection.getId().toString());
	        	name_field.setText(newSelection.getNaziv());
	        }
	    });
		populate(null);
	}

    @FXML
    void populate(ActionEvent event) {
    	wClear();
    	table.getItems().clear();
		List<Drzava> drzave = null;
		try {
	    	if("".equals(name_field.getText()) && "".equals(id_field.getText())) {
	    		drzave = WrapperDrzava.selectAll();
	    	}else if("".equals(id_field.getText())){
				drzave = WrapperDrzava.selectBy(new Drzava(null, name_field.getText(), null));
	    	}else
	    		{
	    		drzave = WrapperDrzava.selectBy(new Drzava(Integer.valueOf(id_field.getText()), name_field.getText(), null));
	    	}
			ObservableList<Drzava> data = FXCollections.observableArrayList(drzave);
			id_column.setCellValueFactory(new PropertyValueFactory<Drzava, Integer>("id"));
			name_column.setCellValueFactory(new PropertyValueFactory<Drzava, String>("naziv"));
			for (Drzava emp : data){
			    table.getItems().add(emp);
			}
		} catch (InputException e) {
			warning_label.setText(e.getMessage());
		} catch (NumberFormatException e) {
			warning_label.setText("Not a valid ID.");
		}
    }

    @FXML
    void update(ActionEvent event) {
    	wClear();
    	try {
        	Drzava in = new Drzava(Integer.valueOf(id_field.getText()), name_field.getText(), null);
			WrapperDrzava.update(in);
		} catch (InputException e) {
    		warning_label.setText(e.getMessage());
		}catch (NumberFormatException e) {
    		warning_label.setText("Not a valid ID.");
		}
    }

    @FXML
    void viewCities(ActionEvent event) {
    	Pane p = (Pane)county_pane.getParent();
    	Drzava obj = table.getSelectionModel().getSelectedItem();
    	GradContoller cc = (GradContoller) new GUIUtil().swapPane(p, "GradScreen");
    	if(obj != null)
        	cc.getCountry().setText(obj.getNaziv());
    }

	private void wClear() {
    	warning_label.setText("");
    }

}
