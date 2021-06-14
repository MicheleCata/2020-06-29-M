/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.ArcoPeso;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	txtResult.clear();
    	Integer anno = boxAnno.getValue();
    	
    	if (anno==null) {
    		txtResult.appendText("Seleziona i valori di input!");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	txtResult.appendText("Grafo creato\n #VERTICI: "+ model.getNumVertici()+ " \n #ARCHI: "+ model.getNArchi()+"\n");
    
    	boxRegista.getItems().addAll(model.getRegisti());
    
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	Director d= boxRegista.getValue();
    	
    	if (d==null) {
    		txtResult.appendText("ERRORE: scegli un regista!\n");
    	}
    	
    	List<ArcoPeso> result = model.getVicini(d);
    	
    	txtResult.appendText("Registi adiacenti a "+ d.toString()+"\n");
    	if(result.size()==0) {
    		txtResult.appendText("NESSUNO\n");
    	}else {
    		
    		for (ArcoPeso d1: result) {
    			txtResult.appendText(d1.toString()+"\n");
    		}
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	txtResult.clear();
    	Director partenza = boxRegista.getValue();
    	if (partenza==null) {
    		txtResult.appendText("ERRORE: scegli un regista!\n");
    	}
    	String soglia = txtAttoriCondivisi.getText();
    	
    	int c;
    	try {
    		c= Integer.parseInt(soglia);
    		
    	}catch (NumberFormatException e) {
    		txtResult.setText("Inserisci un valore numerico");
    		return;
    	}
    	
    	List<Director> percorso = model.getPercorso(partenza, c);
    	for (Director d: percorso) {
    		txtResult.appendText(d+"\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	List<Integer> anni = new ArrayList<>();
    	for (int i =2004; i<=2006; i++) {
    		anni.add(i);
    	}
    	boxAnno.getItems().addAll(anni);
    	
    }
    
}
