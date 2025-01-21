package org.tuvarna.controller.report_panel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.tuvarna.entity.Trip;

import java.util.Map;
import java.util.Set;

public abstract class ReportController {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    public void setButton1Text(String text) {
        button1.setText(text);
    }

    public void setButton2Text(String text) {
        button2.setText(text);
    }

    protected void commonAlert(StringBuilder sb, Map<Boolean, Set<Trip>> tripMap) {
        tripMap.get(true).removeAll(tripMap.get(false));

        sb.append(tripMap.get(true)).append("\n");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming trips with sold tickets");
        alert.setHeaderText(null);
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    @FXML
    abstract void handleButtonOne();

    @FXML
    abstract void handleButtonTwo();


}
