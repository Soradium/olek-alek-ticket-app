package org.tuvarna.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import org.hibernate.SessionFactory;
import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.User;
import org.tuvarna.observer.Observer;
import org.tuvarna.service.*;

import java.util.List;

public class MainController implements Observer {

    @FXML
    private BorderPane root;

    @FXML
    private Parent administrator;
    @FXML
    private Parent distributor;
    @FXML
    private Parent company;
    @FXML
    private Parent user;
    @FXML
    private Parent cashier;

    @FXML
    private AdministratorController administratorController;
    @FXML
    private DistributorController distributorController;
    @FXML
    private CompanyController companyController;
    @FXML
    private UserController userController;
    @FXML
    private CashierController cashierController;

    private MenuStripSelector menuStrip;

    private Menu selectedMenu;

    private MenuItem selectedMenuItem;

    private TripService tripService;
    private CompanyService companyService;
    private BusService busService;
    private DistributorService distributorService;
    private TicketService ticketService;
    private CashierService cashierService;
    private UserService userService;
    /*
    TODO: RequestPanelController
    TODO: Cashier
    TODO: inject RequestPanelController into Cashier, Company, Distributor

    *  */

    public void initialize() {

        SessionFactory sessionFactory = DatabaseSingleton.getInstance().getSessionFactory();

        tripService = new TripService();
        companyService = new CompanyService();
        busService = new BusService();
        distributorService = new DistributorService();
        ticketService = new TicketService();
        cashierService = new CashierService();
        userService = new UserService();

        List<Cashier> cashiers = cashierService.getAllCashiers();
        List<User> users = userService.getAllUsers();
        List<Company> companies = companyService.getAllCompanies();
        List<Distributor> distributors = distributorService.getAllDistributors();

        menuStrip = new MenuStripSelector
                .MenuStripSelectorBuilder()
                .withCashiers(cashiers)
                .withDistributors(distributors)
                .withCompanies(companies)
                .withUsers(users)
                .build();

        try {
            FXMLLoader administratorLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/administrator.fxml"));
            administrator = administratorLoader.load();
            administratorController = administratorLoader.getController();
            administratorController.setCompanyService(companyService);
            administratorController.setDistributorService(distributorService);
            administratorController.setUserService(userService);
            administratorController.registerObserver(menuStrip);

            FXMLLoader companyLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/company.fxml"));
            company = companyLoader.load();
            companyController = companyLoader.getController();
            companyController.setTripService(tripService);
            companyController.setBusService(busService);
            companyController.setCompanyService(companyService);
            companyController.setTicketService(ticketService);
            companyController.registerObserver(menuStrip);

            FXMLLoader distributorLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/distributor.fxml"));
            distributor = distributorLoader.load();
            distributorController = distributorLoader.getController();
            distributorController.setTripService(tripService);
            distributorController.setCompanyService(companyService);
            distributorController.setCashierService(cashierService);
            distributorController.setDistributorService(distributorService);
            distributorController.registerObserver(menuStrip);
            distributorController.setCompanyController(companyController);

            FXMLLoader cashierLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/cashier.fxml"));
            cashier = cashierLoader.load();
            cashierController = cashierLoader.getController();
            cashierController.setTripService(tripService);
            cashierController.setDistributorController(distributorController);
            cashierController.registerObserver(menuStrip);
            cashierController.setService(cashierService);

            FXMLLoader userLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/user.fxml"));
            user = userLoader.load();
            userController = userLoader.getController();
            userController.setTripService(tripService);
            userController.setTicketService(ticketService);
            userController.initializeData();


            root.setTop(menuStrip.getMenuBar());
            root.setCenter(administrator);

            menuStrip.registerObserver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object context) {
        MenuStripSelector menuStrip = (MenuStripSelector) context;
        selectedMenu = menuStrip.getCurrentMenu();
        selectedMenuItem = menuStrip.getCurrentItem();

        System.out.println(selectedMenu);
        System.out.println(selectedMenuItem);

        switch (selectedMenu.getText()) {
            case "Distributors": {
                distributorController.setCurrentDistributor(selectedMenuItem.getText());
                root.setCenter(distributor);
                root.setRight(distributorController.getCheckRequests());
                break;
            }
            case "Companies": {
                busService.setCurrentCompany(selectedMenuItem.getText());
                tripService.setCurrentCompany(selectedMenuItem.getText());
                root.setCenter(company);
                root.setRight(companyController.getCheckRequests());
                break;
            }
            case "Users": {
                root.setCenter(user);
                userController.setCurrentUser(selectedMenuItem.getText());
                root.setRight(null);
                break;
            }
            case "Cashiers": {
                String cashierName = selectedMenuItem.getText();
                String distName = distributorService.getAllDistributors().stream()
                        .flatMap(distributor -> distributor.getCashiers().stream())
                        .filter(cashier -> cashier.getName().equals(cashierName))
                        .map(cashier -> cashier.getDistributor().getName())
                        .findFirst()
                        .orElse("Unknown Distributor");
                root.setCenter(cashier);
                cashierController.setCurrentCashier(
                        cashierService.getCashierByName(
                                selectedMenuItem.getText()
                        ));
                cashierController.setDistributorName(distName);
                root.setRight(null);
                break;
            }
            default: {
                root.setCenter(administrator);
                root.setRight(null);
                break;
            }
        }
        menuStrip.getMenuBar().toFront();
    }

}
