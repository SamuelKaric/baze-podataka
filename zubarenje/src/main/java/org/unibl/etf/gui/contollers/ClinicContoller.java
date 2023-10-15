package org.unibl.etf.gui.contollers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.unibl.etf.entity.CjenovnikItem;
import org.unibl.etf.entity.Grad;
import org.unibl.etf.entity.Klinika;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.gui.GUIUtil;
import org.unibl.etf.wrapper.WrapperGrad;
import org.unibl.etf.wrapper.WrapperKlinika;

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

public class ClinicContoller implements Initializable {

	@FXML
	private Button add_button;

	@FXML
	private Button adder_button;

	@FXML
	private TextField city_filed;

	@FXML
	private TableView<Klinika> clinic_table;

	@FXML
	private AnchorPane clinics_pane;

	@FXML
	private Button delete_button;

	@FXML
	private TableColumn<Klinika, Integer> id_column;

	@FXML
	private TextField id_field;

	@FXML
	private TableColumn<Klinika, String> name_column;

	@FXML
	private TextField name_field;

	@FXML
	private TableColumn<Klinika, String> phone_column;

	@FXML
	private TextField phone_field;

	@FXML
	private Button populate_button;

	@FXML
	private Button reserve_button;

	@FXML
	private TableColumn<CjenovnikItem, String> t_description_column;

	@FXML
	private TableColumn<CjenovnikItem, Integer> t_id_column;

	@FXML
	private TableColumn<CjenovnikItem, String> t_name_column;

	@FXML
	private TableColumn<CjenovnikItem, Double> t_price_column;

	@FXML
	private TableView<CjenovnikItem> tretman_table;

	@FXML
	private Button tretment_button;

	@FXML
	private Button update_button;

	@FXML
	private Label warning_labels;

	@FXML
	void add(ActionEvent event) {
		wClear();
		try {
			Klinika g = new Klinika(null, name_field.getText(), phone_field.getText(), null, WrapperGrad.selectBy(new Grad(null, city_filed.getText(), null, null),null).get(0));
			List<Grad> list = WrapperGrad.selectBy(new Grad(null, city_filed.getText(), null, null), null);
			if (!list.isEmpty()) {
				WrapperKlinika.insert(g, list.get(0).getId());
			}
		} catch (InputException ex) {
			warning_labels.setText(ex.getMessage());
		}
	}

	@FXML
	void addTreatment(ActionEvent event) {
		Klinika obj = clinic_table.getSelectionModel().getSelectedItem();
		TreatmentContoller controller = new GUIUtil().pupupPane(clinics_pane, "TreatmentScreen", obj.getId().toString());
		if (controller != null)
			controller.setClinic().setText(obj.getId().toString());
		populate(null);
	}

	@FXML
	void delete(ActionEvent event) {

	}

	public TextField getCity() {
		return city_filed;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clinic_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				id_field.setText(newSelection.getId().toString());
				name_field.setText(newSelection.getNaziv());
				phone_field.setText(newSelection.getTelefon());
				city_filed.setText(newSelection.getLokacija().getNaziv());
				adder_button.setDisable(false);
			}
		});
		tretman_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				reserve_button.setDisable(false);
			}
		});
	}

	@FXML
	void populate(ActionEvent event) {
		wClear();
		clinic_table.getItems().clear();
		Integer id = null;
		Integer id_in = null;
		List<Grad> grad = null;
		if (!"".equals(city_filed.getText())) {
			grad = WrapperGrad.selectBy(new Grad(null, city_filed.getText(), null, null), null);
			if (grad.size() != 0) {
				id = grad.get(0).getId();
			}
		}
		if (!"".equals(id_field.getText())) {
			id_in = Integer.valueOf(id_field.getText());
		}
		List<Klinika> gradovi = WrapperKlinika
				.selectBy(new Klinika(id_in, name_field.getText(), phone_field.getText(), null, 
						id==null? null: WrapperGrad.selectBy(new Grad(null, city_filed.getText(), null, null), null).get(0)));
		ObservableList<Klinika> data = FXCollections.observableArrayList(gradovi);
		id_column.setCellValueFactory(new PropertyValueFactory<Klinika, Integer>("id"));
		name_column.setCellValueFactory(new PropertyValueFactory<Klinika, String>("naziv"));
		phone_column.setCellValueFactory(new PropertyValueFactory<Klinika, String>("telefon"));
		for (Klinika emp : data) {
			clinic_table.getItems().add(emp);
		}
	}

	@FXML
	void populateTretment(ActionEvent event) {
		wClear();
		tretman_table.getItems().clear();
//		Integer id = null;
//		Integer id_in = null;
		Klinika klinika = clinic_table.getSelectionModel().getSelectedItem();
		if (klinika != null) {
//			if (!"".equals(id_field.getText())) {
//				id_in = Integer.valueOf(id_field.getText());
//			}
			ObservableList<CjenovnikItem> data = FXCollections.observableArrayList(klinika.getCijenovnik());
			t_price_column.setCellValueFactory(new PropertyValueFactory<CjenovnikItem, Double>("cijena"));
			t_id_column.setCellValueFactory(new PropertyValueFactory<CjenovnikItem, Integer>("ID"));
			t_name_column.setCellValueFactory(new PropertyValueFactory<CjenovnikItem, String>("Treatement"));
			t_description_column.setCellValueFactory(new PropertyValueFactory<CjenovnikItem, String>("Desc"));
			for (CjenovnikItem emp : data) {
				tretman_table.getItems().add(emp);
			}
		}

	}

	@FXML
	void reserve(ActionEvent event) {
		FrontPageController r = GUIUtil.builder(null);
		Klinika selected = clinic_table.getSelectionModel().getSelectedItem();
		CjenovnikItem ci = tretman_table.getSelectionModel().getSelectedItem();
		if(selected != null && ci != null) {
			r.setClinic_field(selected);
			r.setProcedure_field(ci);
			r.setCity_label(selected.getLokacija());
			r.getPrice_label().setText(ci.getCijena().toString());
			new GUIUtil().swapPane(clinics_pane, null);
		}else {
			warning_labels.setText("Select both a klinic and a treatment");
		}
	}

	@FXML
	void update(ActionEvent event) {
		wClear();
		try {
			Klinika g = new Klinika(Integer.valueOf(id_field.getText()), name_field.getText(), phone_field.getText(),
					null, null);
			WrapperKlinika.update(g);
		} catch (NumberFormatException ex) {
			warning_labels.setText("Id not valid");
		} catch (InputException ex) {
			warning_labels.setText(ex.getMessage());
		}
	}

	private void wClear() {
		// TODO Auto-generated method stub
		warning_labels.setText("");
	}

}
