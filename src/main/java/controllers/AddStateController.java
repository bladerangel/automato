package controllers;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.ValidationFacade;
import com.jfoenix.validation.base.ValidatorBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.State;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Rangel on 22/11/2016.
 */
public class AddStateController extends AbstractController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField name;

    ValidatorState validator;

    RequiredFieldValidator requiredFieldValidator;

    @FXML
    public void save() throws IOException {
        if (name.validate() && applicationController.addStateGraph(new State(name.getText()))) {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        validator = new ValidatorState();
        requiredFieldValidator = new RequiredFieldValidator();
        validator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.WARNING));
        requiredFieldValidator.setMessage("Empty");
        validator.setMessage("Choose another name!");
        name.getValidators().add(validator);
        name.getValidators().add(requiredFieldValidator);
    }


    @FXML
    public void validate() {
        name.validate();
    }

    private class ValidatorState extends ValidatorBase {

        @Override
        protected void eval() {
            if (applicationController.findStateByName(name.getText()) != null) {
                hasErrors.set(true);
            } else {
                hasErrors.set(false);
            }
        }
    }
}
