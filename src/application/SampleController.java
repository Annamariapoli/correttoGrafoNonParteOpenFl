package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	
	private Model m = new Model();
	
	public void setModel(Model m){
		this.m=m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtDistMax;

    @FXML
    private Button btnSelez;

    @FXML
    private TextField txtNumPass;

    @FXML
    private Button btnSimul;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelez(ActionEvent event) {
    	txtResult.clear();
    	double km = Double.parseDouble(txtDistMax.getText());

    }

    @FXML
    void doSimul(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtDistMax != null : "fx:id=\"txtDistMax\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnSelez != null : "fx:id=\"btnSelez\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtNumPass != null : "fx:id=\"txtNumPass\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnSimul != null : "fx:id=\"btnSimul\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

    }
}
