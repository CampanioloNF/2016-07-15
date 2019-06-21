package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;
	
	private int distMax = 0;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		
		txtResult.clear();
		
		try {
			
			if(txtDistanzaInput.getText()!=null)
		    	distMax = Integer.parseInt(txtDistanzaInput.getText());
		    else {
		      txtResult.appendText("Si prega di inserire un numero intero, grazie.\n");
		      return;
		    }
			
			
			model.creaGrafo(distMax);
			
			if(model.isConnected())
				txtResult.appendText("Tutte le destinazioni sono raggiungibili da qualunque aereoporto.\n");
			else
				txtResult.appendText("Non tutte le destinazioni sono raggiungibili da qualunque aereoporto.\n");
			
			txtResult.appendText("L'aereoporto, raggiungibile, più lontano da fiumicino è : "+model.getAirport()+"\n");
			
			
		}catch(NumberFormatException nfe) {
			txtResult.appendText("Si prega di inserire un numero intero, grazie.");
			return;
		}
		
	}

	@FXML
	void doSimula(ActionEvent event) {
		
		txtResult.clear();
		
		try {
		
		if(distMax>0 && distMax==Integer.parseInt(txtDistanzaInput.getText())) {
			
	          txtResult.appendText("Sistemazione dei turisti: \n\n");
	          txtResult.appendText(model.simulate(Integer.parseInt(txtPasseggeriInput.getText())));
	          
			
		}
		else {
			txtResult.appendText("Nel caso in cui si voglia cambiare la distanza massima considerata, premere nuovamente 'Seleziona Rotte', grazie.");
	        return;
		}
		}catch(NumberFormatException nfe) {
			txtResult.appendText("Si prega di selezionare un numero intero di passeggeri.");
			return;
		}
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}

