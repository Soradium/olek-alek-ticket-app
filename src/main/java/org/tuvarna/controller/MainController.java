package org.tuvarna.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.User;
import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;
import org.tuvarna.repository.*;
import org.tuvarna.service.*;
import org.tuvarna.singleton.DatabaseSingleton;

import java.util.List;

public class MainController implements Subject, Observer {

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
    private Parent checkRequests;

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
    @FXML
    private RequestPanelController requestPanelController;

    private MenuStripSelector menuStrip;

    private String selectedMenu;

    private String selectedMenuItem;

    private TripDAO tripDAO;
    private CompanyDAO companyDAO;
    private BusDAO busDAO;
    private DistributorDAO distributorDAO;
    private TicketDAO ticketDAO;
    private CashierDAO cashierDAO;
    private UserDAO userDAO;

    private TripService tripService;
    private CompanyService companyService;
    private BusService busService;
    private DistributorService distributorService;
    private TicketService ticketService;
    private CashierService cashierService;
    private UserService userService;

    public void initialize() {

        SessionFactory sessionFactory = DatabaseSingleton.getInstance().getSessionFactory();

        tripDAO = new TripDAOImpl(sessionFactory);
        companyDAO = new CompanyDAOImpl(sessionFactory);
        busDAO = new BusDAOImpl(sessionFactory);
        distributorDAO = new DistributorDAOImpl(sessionFactory);
        ticketDAO = new TicketDAOImpl(sessionFactory);
        cashierDAO = new CashierDAOImpl(sessionFactory);
        userDAO = new UserDAOImpl(sessionFactory);

        tripService = new TripService(tripDAO);
        companyService = new CompanyService(companyDAO);
        busService = new BusService(busDAO);
        distributorService = new DistributorService(distributorDAO);
        ticketService = new TicketService(ticketDAO);
        cashierService = new CashierService(cashierDAO);
        userService = new UserService(userDAO);

        List<Cashier> cashiers = cashierDAO.getCashiers();
        List<User> users = userDAO.getUsers();
        List<Company> companies = companyDAO.getCompanies();
        List<Distributor> distributors = distributorDAO.getDistributors();

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

            FXMLLoader companyLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/company.fxml"));
            company = companyLoader.load();
            companyController = companyLoader.getController();
            companyController.setTripService(tripService);
            companyController.setBusService(busService);
            companyController.setCompanyService(companyService);

            FXMLLoader cashierLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/cashier.fxml"));
            cashier = cashierLoader.load();
            cashierController = cashierLoader.getController();

            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelController = requestPanelLoader.getController();

            FXMLLoader distributorLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/distributor.fxml"));
            distributor = distributorLoader.load();
            distributorController = distributorLoader.getController();

            FXMLLoader userLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/user.fxml"));
            user = userLoader.load();
            userController = userLoader.getController();

            root.setTop(menuStrip.getMenuBar());
            root.setCenter(administrator);

            menuStrip.registerObserver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object context) {
        // send menuitem and menu, then process them
        MenuStripSelector menuStrip = (MenuStripSelector) context;
        selectedMenu = menuStrip.getCurrentMenuChosen();
        selectedMenuItem = menuStrip.getCurrentItemChosen();

        System.out.println(selectedMenu);
        System.out.println(selectedMenuItem);
        switch(selectedMenu) {
            case "Distributors": {
                root.setCenter(distributor);
                break;
            }
            case "Companies": {
                root.setCenter(company);
                tripService.setCurrentCompany(selectedMenuItem);
                break;
            }
            case "Users": {
                root.setCenter(user);
                break;
            }
            case "Cashiers": {
                root.setCenter(cashier);
                break;
            } default: {
                root.setCenter(administrator);
                break;
            }
        }

    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }

//    @Override
//    public void update(Object menuItem) {
//        Session session = databaseSingleton.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//
//    }


}
