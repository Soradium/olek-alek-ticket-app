package org.tuvarna.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.User;
import org.tuvarna.observer.Observer;
import org.tuvarna.service.*;

import java.util.List;

public class MainController implements Observer {

    private final static Logger logger = LogManager.getLogger(MainController.class);
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

    public void initialize() {
        logger.info("Initialize MainController");
        tripService = new TripService();
        companyService = new CompanyService();
        busService = new BusService();
        distributorService = new DistributorService();
        ticketService = new TicketService();
        cashierService = new CashierService();
        userService = new UserService();
        logger.info("All services initialized");

        List<Cashier> cashiers = cashierService.getAllCashiers();
        List<User> users = userService.getAllUsers();
        List<Company> companies = companyService.getAllCompanies();
        List<Distributor> distributors = distributorService.getAllDistributors();
        logger.info("Setting all Cashiers, Users, Companies, and Distributors");

        menuStrip = new MenuStripSelector
                .MenuStripSelectorBuilder()
                .withCashiers(cashiers)
                .withDistributors(distributors)
                .withCompanies(companies)
                .withUsers(users)
                .build();
        logger.info("Menu strip configured");

        try {
            FXMLLoader administratorLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/administrator.fxml"));
            administrator = administratorLoader.load();
            administratorController = administratorLoader.getController();
            administratorController.setCompanyService(companyService);
            administratorController.setDistributorService(distributorService);
            administratorController.setUserService(userService);
            administratorController.registerObserver(menuStrip);
            logger.info("AdministratorController configured");

            FXMLLoader companyLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/company.fxml"));
            company = companyLoader.load();
            companyController = companyLoader.getController();
            companyController.setTripService(tripService);
            companyController.setBusService(busService);
            companyController.setCompanyService(companyService);
            companyController.registerObserver(menuStrip);
            logger.info("CompanyController configured");

            FXMLLoader distributorLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/distributor.fxml"));
            distributor = distributorLoader.load();
            distributorController = distributorLoader.getController();
            distributorController.setTripService(tripService);
            distributorController.setCompanyService(companyService);
            distributorController.setCashierService(cashierService);
            distributorController.setDistributorService(distributorService);
            distributorController.registerObserver(menuStrip);
            distributorController.setCompanyController(companyController);
            logger.info("DistributorController configured");

            FXMLLoader cashierLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/cashier.fxml"));
            cashier = cashierLoader.load();
            cashierController = cashierLoader.getController();
            cashierController.setTripService(tripService);
            cashierController.setDistributorController(distributorController);
            cashierController.registerObserver(menuStrip);
            cashierController.setService(cashierService);
            logger.info("CashierController configured");

            FXMLLoader userLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/user.fxml"));
            user = userLoader.load();
            userController = userLoader.getController();
            userController.setTripService(tripService);
            userController.setTicketService(ticketService);
            userController.setUserService(userService);
            userController.setCashierController(cashierController);
            userController.initializeData();
            logger.info("UserController configured");


            root.setTop(menuStrip.getMenuBar());
            root.setCenter(administrator);

            menuStrip.registerObserver(this);
        } catch (Exception e) {
            logger.error("Error in initialize function with message: {}", e.getMessage());
        }
    }

    @Override
    public void update(Object context) {
        MenuStripSelector menuStrip = (MenuStripSelector) context;
        selectedMenu = menuStrip.getCurrentMenu();
        logger.info("Selected menu: {}", selectedMenu);
        selectedMenuItem = menuStrip.getCurrentItem();
        logger.info("Selected menu item: {}", selectedMenuItem);

        switch (selectedMenu.getText()) {
            case "Distributors": {
                distributorController.setCurrentDistributor(selectedMenuItem.getText());
                distributorController.initializeData(selectedMenuItem.getText());
                root.setCenter(distributor);
                root.setRight(distributorController.getCheckRequests());
                root.setBottom(distributorController.getReportsPanel());
                logger.info("Set Request panel for distributorController");
                break;
            }
            case "Companies": {
                busService.setCurrentCompany(selectedMenuItem.getText());
                tripService.setCurrentCompany(selectedMenuItem.getText());
                companyController.initializeData(selectedMenuItem.getText());
                root.setCenter(company);
                root.setRight(companyController.getCheckRequests());
                root.setBottom(companyController.getReportsPanel());
                logger.info("Set Request panel for companyController");
                break;
            }
            case "Users": {
                root.setCenter(user);
                userController.setCurrentUser(selectedMenuItem.getText());
                root.setRight(null);
                root.setBottom(null);
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
                cashierController.setCashierName(cashierName);
                cashierController.setDistributorName(distName);
                root.setRight(cashierController.getCheckRequests());
                root.setBottom(cashierController.getReportsPanel());
                logger.info("Set Request panel for cashierController");
                break;
            }
            default: {
                root.setCenter(administrator);
                root.setRight(null);
                root.setBottom(null);
                break;
            }
        }
        menuStrip.getMenuBar().toFront();
    }

}
