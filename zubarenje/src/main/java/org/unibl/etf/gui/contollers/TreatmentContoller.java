package org.unibl.etf.gui.contollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.entity.CjenovnikItem;
import org.unibl.etf.entity.Tretman;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.wrapper.WrapperCjenovnik;
import org.unibl.etf.wrapper.WrapperTretman;

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

public class TreatmentContoller implements Initializable{

    @FXML
    private Button add_button;

    @FXML
    private TextField clinic_fiield;

    @FXML
    private TableColumn<Tretman, Integer> id_column;

    @FXML
    private TableColumn<Tretman, String> opis_column;

    @FXML
    private TextField price_field;

    @FXML
    private TableView<Tretman> table;

    @FXML
    private TableColumn<Tretman, String> treatment_column;

    @FXML
    private AnchorPane treatment_pane;

    @FXML
    private Label warning_label;

    @FXML
    void add(ActionEvent event) {
    	Tretman t = table.getSelectionModel().getSelectedItem();
    	try {
    		if(t == null) {
    			throw new InputException("Please Select a treatment");
    		}
    		CjenovnikItem g = new CjenovnikItem(t, Double.valueOf(price_field.getText()) );
    		WrapperCjenovnik.insert(g, Integer.valueOf(clinic_fiield.getText()));
    	}catch(InputException ex) {
    		warning_label.setText(ex.getMessage());
    	}catch(NumberFormatException e) {
    		warning_label.setText(e.getMessage());
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Tretman> list = WrapperTretman.selectAll();
		ObservableList<Tretman> data = FXCollections.observableArrayList(list);
		id_column.setCellValueFactory(new PropertyValueFactory<Tretman, Integer>("id"));
		treatment_column.setCellValueFactory(new PropertyValueFactory<Tretman, String>("naziv"));
		opis_column.setCellValueFactory(new PropertyValueFactory<Tretman, String>("opis"));

		for(Tretman t :data) {
			table.getItems().add(t);
		}
	}

    public TextField setClinic() {
        return clinic_fiield;
    }

}
