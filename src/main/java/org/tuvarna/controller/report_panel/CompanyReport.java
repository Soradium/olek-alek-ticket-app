package org.tuvarna.controller.report_panel;

import javafx.scene.control.Alert;
import org.tuvarna.controller.CompanyController;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.TicketService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CompanyReport extends ReportController {
    private final TicketService ticketService;
    private CompanyController companyController;

    public CompanyReport() {
        this.ticketService = new TicketService();
    }

    public void initialize() {
        super.setButton1Text("Upcoming trips with unsold tickets");
        super.setButton2Text("Upcoming trips with sold tickets");
    }

    @Override
    void handleButtonOne() {
        StringBuilder sb = new StringBuilder();
        sb.append("Upcoming trips with unsold tickets report result: ");
        Set<Trip> trips = new HashSet<>();
        ticketService.getAllTickets()
                .stream()
                .filter(ticket -> (ticket
                                .getTrip()
                                .getCompany()
                                .getName()
                                .equals(companyController.companyName.getText()))
                                && (ticket
                                .getTrip()
                                .getDate().isAfter(LocalDate.now().minusDays(30)))
                                && (ticket
                                .getUser() == null
                        )
                ).forEach(ticket -> {
                    trips.add(ticket.getTrip());
                });
        sb.append(trips).append("\n");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Upcoming trips with unsold tickets");
        alert.setHeaderText(null);
        alert.setContentText(sb.toString());
        alert.showAndWait();

    }

    @Override
    public void handleButtonTwo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Upcoming trips with sold tickets report result: ");
        Map<Boolean, Set<Trip>> tripMap = new HashMap<>();
        tripMap.put(false, new HashSet<>());
        tripMap.put(true, new HashSet<>());

        ticketService.getAllTickets()
                .stream()
                .filter(ticket -> (ticket
                        .getTrip()
                        .getCompany()
                        .getName()
                        .equals(companyController.companyName.getText()))
                        && (ticket
                        .getTrip()
                        .getDate().isAfter(LocalDate.now().minusDays(30)))

                ).forEach(ticket -> {
                    if (ticket.getUser() == null) {
                        tripMap.get(false).add(ticket.getTrip());
                    } else {
                        tripMap.get(true).add(ticket.getTrip());
                    }
                });
        super.commonAlert(sb, tripMap);
    }

    public CompanyController getCompanyController() {
        return companyController;
    }

    public void setCompanyController(CompanyController companyController) {
        this.companyController = companyController;
    }
}
