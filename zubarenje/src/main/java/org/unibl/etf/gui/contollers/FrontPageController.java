package org.unibl.etf.gui.contollers;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.unibl.etf.entity.CjenovnikItem;
import org.unibl.etf.entity.Grad;
import org.unibl.etf.entity.Klijent;
import org.unibl.etf.entity.Klinika;
import org.unibl.etf.entity.Rezervacija;
import org.unibl.etf.exceptions.InputException;
import org.unibl.etf.gui.GUIUtil;
import org.unibl.etf.wrapper.WrapperCjenovnik;
import org.unibl.etf.wrapper.WrapperKlijent;
import org.unibl.etf.wrapper.WrapperRezervacija;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

public class FrontPageController implements Initializable {

	@FXML
	private AnchorPane anchor;

	@FXML
	private TextField city_label;

	@FXML
	private TextField clinic_field;

	@FXML
	private MenuItem close_item;
	
    @FXML
    private Button cheap_button;

	@FXML
	private Menu close_menu;

	private Rezervacija currentReservation;
	
    @FXML
    private Button delete_button;

	@FXML
	private DatePicker end_date;

	@FXML
	private MenuItem grad_item;

	@FXML
	private MenuItem klijent_item;

	@FXML
	private MenuItem klinika_item;

	@FXML
	private Button log_in_button;

	@FXML
	private AnchorPane log_in_pane;

	@FXML
	private MenuItem log_out_item;

	@FXML
	private MenuBar menu;

	@FXML
	private Label message_iabel;

	@FXML
	private TextField price_label;

	@FXML
	private TextField procedure_field;

	@FXML
	private Button procure_button;

	@FXML
	private ComboBox<Rezervacija> reservations;

	@FXML
	private Button reserve_button;

	@FXML
	private MenuItem rezervacija_item;

	@FXML
	private MenuItem rezerve_item;

	@FXML
	private DatePicker start_date;

	@FXML
	private Menu tables_menu;

	@FXML
	private AnchorPane target_pane;

	@FXML
	private TextField username_field;

	@FXML
	void close(ActionEvent event) {
		System.exit(0);
	}
	
    @FXML
    void cheapskate(ActionEvent event) {
    	clear();
    	try {
	    	List<Pair<String,Double>> bra = WrapperCjenovnik.cheaper(currentReservation.getTretman().getId(), currentReservation.getLokacija().getId(), currentReservation.getCijenaTretman());
	    	if(!bra.isEmpty() && !bra.get(0).getKey().equals(currentReservation.getKlinika().getNaziv())) {
	    		message_iabel.setText("Cheeper at "+ bra);
	    	}else {
	    		message_iabel.setText("Cheep as can be.");
	    	}
    	}catch(NullPointerException ex) {
    		message_iabel.setText("No data");
    	}
    }

	public TextField getCity_label() {
		return city_label;
	}

	public TextField getClinic_field() {
		return clinic_field;
	}

	public Rezervacija getCurrentReservation() {
		return currentReservation;
	}

	public AnchorPane getLog_in_pane() {
		return log_in_pane;
	}

	public TextField getPrice_label() {
		return price_label;
	}

	public TextField getProcedure_field() {
		return procedure_field;
	}

	@FXML
	void gradBase(ActionEvent event) {
		new GUIUtil().swapPane(target_pane, "GradScreen");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCurrentReservation(new Rezervacija());
		GUIUtil.builder(this);
		reservations.setOnAction(e -> {
			currentReservation = reservations.getValue() == null? new Rezervacija() : reservations.getValue();
			if(currentReservation.getKlinika()!=null) {
			currentReservation.setLokacija(currentReservation.getKlinika().getLokacija());
			clinic_field.setText(currentReservation.getKlinika().getNaziv());
			}
			System.out.println(currentReservation);
			if(currentReservation.getCijenaTretman()!=null)
			price_label.setText(this.currentReservation.getCijenaTretman().toString());
			if(currentReservation.getTretman()!=null)
				procedure_field.setText(this.currentReservation.getTretman().getNaziv());
			if(currentReservation.getLokacija()!=null)
				city_label.setText(currentReservation.getLokacija().getNaziv());
			if(currentReservation.getVrijemeRezervacije()!=null)
			start_date.setValue(currentReservation.getVrijemeRezervacije().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			if(currentReservation.getVrijemeOdlaska()!=null)
				end_date.setValue(currentReservation.getVrijemeOdlaska().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			});
	}

	@FXML
	void klijentBase(ActionEvent event) {
		target_pane.getChildren().clear();
		target_pane.getChildren().add(log_in_pane);
	}

	@FXML
	void klinikaBase(ActionEvent event) {
		new GUIUtil().swapPane(target_pane, "ClinicsScreen");
	}

	@FXML
	void login(ActionEvent event) {
		clear();
		try {
			logout(null);
			if(!"".equals(username_field.getText())) {
				List<Klijent> k = WrapperKlijent.selectBy(new Klijent(null, username_field.getText(), null, null));
				if(!k.isEmpty()) {
					currentReservation.setKlijent(k.get(0));
						reservations.getItems().addAll(WrapperRezervacija.selectBy(new Rezervacija(null, null, null, k.get(0), null, null, null)));
				}else {
					WrapperKlijent.insert(new Klijent(null, username_field.getText(), username_field.getText()+"jovanovic@gmail.com", new Date()));
				}
			}
		} catch (InputException e) {
			// TODO Auto-generated catch block
			message_iabel.setText(e.getMessage());
		}
	}

	@FXML
	void logout(ActionEvent event) {
		clear();
		if(this.currentReservation !=null)
			this.currentReservation.setKlijent(null);
		reservations.getItems().clear();
	}

    @FXML
    void delete(ActionEvent event) {
    	clear();
    	Rezervacija r = reservations.getValue();
    	if(r != null) {
    		try {
				WrapperRezervacija.delete(r);
			} catch (InputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
	
	@FXML
	void procure(ActionEvent event) {
		clear();
		new GUIUtil().swapPane(target_pane, "DrzavaScreen");
	}

	@FXML
	void reserve(ActionEvent event) {
		clear();
		try {
			System.out.println(currentReservation);
			if(start_date.getValue() == null)
				throw new InputException("Start Date needed.");
			Date begin = new Date(java.sql.Date.valueOf(start_date.getValue()).getTime());
			if(end_date.getValue() == null)
				throw new InputException("End Date needed.");
			Date end = new Date(java.sql.Date.valueOf(end_date.getValue()).getTime());
			currentReservation.setVrijemeRezervacije(begin);
			currentReservation.serVrijemeOdlaska(end);
			if(currentReservation.getKlinika() != null){
				WrapperRezervacija.overlap(begin, end, currentReservation.getKlinika().getId());
			}
			WrapperRezervacija.insert(currentReservation);
		} catch (InputException e) {
			// TODO Auto-generated catch block
			message_iabel.setText(e.getMessage());
		}
	}

	@FXML
	void rezervacijaBase(ActionEvent event) {
		new GUIUtil().swapPane(target_pane, "DrzavaScreen");
	}

	public void setCity_label(Grad city) {
		this.city_label.setText(city.getNaziv());
		this.currentReservation.setLokacija(city);
	}

	public void setClinic_field(Klinika in) {
		clinic_field.setText(in.getNaziv());
		this.currentReservation.setKlinika(in);
	}

	public void setCurrentReservation(Rezervacija currentReservation) {
		this.currentReservation = currentReservation;
	}

	public void setLog_in_pane(AnchorPane log_in_pane) {
		this.log_in_pane = log_in_pane;
	}

	public void setPrice_label(TextField price_label) {
		this.price_label = price_label;
	}

	public void setProcedure_field(CjenovnikItem in) {
		procedure_field.setText(in.getTretman().getNaziv());
		currentReservation.setTretman(in.getTretman());
		currentReservation.setCijenaTretman(in.getCijena());
	}
	
	private void clear() {
		message_iabel.setText("");
		}

}
