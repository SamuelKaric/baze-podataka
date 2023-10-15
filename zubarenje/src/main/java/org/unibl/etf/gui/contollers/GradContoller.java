package org.unibl.etf.gui.contollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.entity.Drzava;
import org.unibl.etf.entity.Grad;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.gui.GUIUtil;
import org.unibl.etf.wrapper.WrapperDrzava;
import org.unibl.etf.wrapper.WrapperGrad;

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


public class GradContoller implements Initializable{

    @FXML
    private Button add_button;

    @FXML
    private TableColumn<Grad, Integer> clinic_column;

    @FXML
    private Button clinics;

    @FXML
    private Button delete_button;

    @FXML
    private TextField drzava_field;

    @FXML
    private AnchorPane grad_pane;

    @FXML
    private TableColumn<Grad, Integer> id_column;

    @FXML
    private TextField id_field;

    @FXML
    private TableColumn<Grad, String> name_column;

    @FXML
    private TextField name_field;

    @FXML
    private Button populate_button;

    @FXML
    private TableView<Grad> table;

    @FXML
    private Button update_button;

    @FXML
    private Label warning_label;

    @FXML
    private TableColumn<Grad, String> zip_column;

    @FXML
    private TextField zip_field;

    @FXML
    void add(ActionEvent event) {
		wClear();
    	Grad g = new Grad(null, name_field.getText(), null, zip_field.getText());
    	try {
	    	if("".equals(drzava_field.getText())) {
	    		throw new InputException("No country selected.");
	    	}
	    	List<Drzava> list = WrapperDrzava.selectBy(new Drzava(null,drzava_field.getText(), null));
	    	if(!list.isEmpty()) {
	    		WrapperGrad.insert(g, list.get(0).getId());
	    	}else {
	    		throw new InputException("No such country!");
	    	}
    	}catch(InputException e) {
    		warning_label.setText(e.getMessage());
    	}
    }

    @FXML
    void delete(ActionEvent event) {
		wClear();
    }

    public TextField getCountry() {
		return drzava_field;
	}

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		wClear();
	    table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	id_field.setText(newSelection.getId().toString());
	        	name_field.setText(newSelection.getNaziv());
	        	zip_field.setText(newSelection.getPostanskiBroj());
	        }
	    });
	}

    @FXML
    void populate(ActionEvent event) {
		wClear();
    	table.getItems().clear();
    	List<Grad> gradovi = null;
    	List<Drzava> list = null;
    	Integer id = null;
    	Integer g_id = null;
		try {
	    	if("".equals(drzava_field.getText())) {
	    		id = null;
	    	}else {
				list =WrapperDrzava.selectBy(new Drzava(null, drzava_field.getText(), null));
		        if(!list.isEmpty())
		        	id = list.get(0).getId();
		        else
		        	throw new InputException("No such country.");
			}
	    	if(!"".equals(id_field.getText())) {
	    		g_id = Integer.valueOf(id_field.getText());
	    	}
	    	gradovi = WrapperGrad.selectBy(new Grad(g_id, name_field.getText(), null, zip_field.getText()), id);
			ObservableList<Grad> data = FXCollections.observableArrayList(gradovi);
			id_column.setCellValueFactory(new PropertyValueFactory<Grad, Integer>("id"));
			name_column.setCellValueFactory(new PropertyValueFactory<Grad, String>("naziv"));
			zip_column.setCellValueFactory(new PropertyValueFactory<Grad, String>("PostanskiBroj"));
			for (Grad emp : data){
			    table.getItems().add(emp);
			}
		}catch (NumberFormatException e) {
    		warning_label.setText("Not a valid ID.");
		} catch (InputException e) {
			warning_label.setText(e.getMessage());
		}
    }


	@FXML
    void update(ActionEvent event) {
		wClear();
    	try {
    	Grad g = new Grad(Integer.valueOf(id_field.getText()), name_field.getText(), null, zip_field.getText());
    	WrapperGrad.update(g, null);
		}catch (NumberFormatException e) {
    		warning_label.setText("Not a valid ID.");
    	}catch(InputException e) {
    		warning_label.setText(e.getMessage());
    	}
    }

	@FXML
    void viewClinics(ActionEvent event) {
    	Pane p = (Pane)grad_pane.getParent();
    	Grad obj = table.getSelectionModel().getSelectedItem();
    	ClinicContoller cc = (ClinicContoller) new GUIUtil().swapPane(p, "ClinicsScreen");
    	if(obj != null)
    		cc.getCity().setText(obj.getNaziv());
    }


	private void wClear() {
		// TODO Auto-generated method stub
    	warning_label.setText("");
	}

}