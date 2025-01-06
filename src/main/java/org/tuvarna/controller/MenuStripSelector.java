package org.tuvarna.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.User;
import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class MenuStripSelector implements Subject, Observer {
    @FXML
    private final MenuBar menuBar;
    private String currentItemChosen;
    private String currentMenuChosen;
    Observer observer;

    public static class MenuStripSelectorBuilder {
        private final MenuBar menuBar = new MenuBar();
        private final Menu companyMenu = new Menu("Companies");
        private final Menu distributorMenu = new Menu("Distributors");
        private final Menu cashierMenu = new Menu("Cashiers");
        private final Menu userMenu = new Menu("Users");
        private final Menu adminMenu = new Menu("Admin");

        private final ObservableList<MenuItem> companies = new ListView<MenuItem>().getItems();
        private final ObservableList<MenuItem> distributors = new ListView<MenuItem>().getItems();
        private final ObservableList<MenuItem> cashiers = new ListView<MenuItem>().getItems();
        private final ObservableList<MenuItem> users = new ListView<MenuItem>().getItems();
        private final MenuItem admin = new MenuItem("Admin");

        public MenuStripSelectorBuilder withCompanies(String... companies) {
            addItems(this.companies, companies);
            return this;
        }

        public MenuStripSelectorBuilder withDistributors(String... distributors) {
            addItems(this.distributors, distributors);
            return this;
        }

        public MenuStripSelectorBuilder withCashiers(String... cashiers) {
            addItems(this.cashiers, cashiers);
            return this;
        }

        public MenuStripSelectorBuilder withUsers(String... users) {
            addItems(this.users, users);
            return this;
        }

        public MenuStripSelectorBuilder withCompanies(List<Company> companies) {
            addItems(this.companies, companies.stream().map(Company::getName).toArray(String[]::new));
            return this;
        }

        public MenuStripSelectorBuilder withDistributors(List<Distributor> distributors) {
            addItems(this.distributors, distributors.stream().map(Distributor::getName).toArray(String[]::new));
            return this;
        }

        public MenuStripSelectorBuilder withCashiers(List<Cashier> cashiers) {
            addItems(this.cashiers, cashiers.stream().map(Cashier::getName).toArray(String[]::new));
            return this;
        }

        public MenuStripSelectorBuilder withUsers(List<User> users) {
            addItems(this.users, users.stream().map(User::getName).toArray(String[]::new));
            return this;
        }
        private void addItems(List<MenuItem> list, String... items) {
            for (String item : items) {
                list.add(new MenuItem(item));
            }
        }

        public MenuStripSelector build() {
            companyMenu.getItems().addAll(companies);
            distributorMenu.getItems().addAll(distributors);
            cashierMenu.getItems().addAll(cashiers);
            userMenu.getItems().addAll(users);
            adminMenu.getItems().add(admin);

            menuBar.getMenus().addAll(companyMenu, distributorMenu, cashierMenu, userMenu, adminMenu);
            return new MenuStripSelector(menuBar);
        }
    }

    private MenuStripSelector(MenuBar menuBarPassed) {
        this.currentMenuChosen = null;
        this.currentItemChosen = null;
        this.menuBar = menuBarPassed;

        this.getMenuBar().getMenus().forEach(menu -> {
                menu.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        currentMenuChosen = menu.getText();
                        System.out.println(currentMenuChosen);
                    }
                });
                menu.getItems().forEach(menuItem -> {
                        menuItem.setOnAction(new EventHandler<>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                currentItemChosen = menuItem.getText();
                                System.out.println(currentItemChosen);
//                                menuItem.getParentMenu().getItems()
//                                        .add(new MenuItem(currentItemChosen));
//                                Menu tempMenu = menuItem.getParentMenu();
//                                menuItem.getParentMenu().getItems().clear();
//                                menuItem.getParentMenu().getItems().addAll(tempMenu);
//                                ObservableList<Menu> menus = menuBar.getMenus();
//                                Menu companyMenu = menus.get(0);
//                                Menu distributorMenu = menus.get(1);
//                                Menu cashierMenu = menus.get(2);
//                                Menu userMenu = menus.get(3);
//                                Menu adminMenu = menus.get(4);
//                                menus.clear(); // Clear existing menus
//                                menus.addAll(companyMenu, distributorMenu, cashierMenu, userMenu, adminMenu);
                            }
                        });
                });
        });

    }
    //when an update is coming from main (new dist or company) update this
    //when an update is coming from this, update main(change panes)

    private void refreshMenuBar() {
        // Clear and re-add menus to the MenuBar
         // Re-add menus with updated items
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer; //main controller
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        this.observer.update(this);
    }

    @Override
    public void update(Object context) {
        String a = (String) context;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void addCompany(String company) {
        findMenuByName("Companies").getItems().add(new MenuItem(company));
    }

    public void addDistributor(String distributor) {
        findMenuByName("Distributors").getItems().add(new MenuItem(distributor));
    }

    public void addCashier(String cashier) {
        findMenuByName("Cashiers").getItems().add(new MenuItem(cashier));
    }

    public void addUser(String user) {
        findMenuByName("Users").getItems().add(new MenuItem(user));
    }

    private Menu findMenuByName(String name) {
        return menuBar.getMenus().stream()
                .filter(menu -> menu.getText().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Menu not found: " + name));
    }


}
