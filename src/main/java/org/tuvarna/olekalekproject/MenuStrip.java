package org.tuvarna.olekalekproject;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MenuStrip {
    private final MenuBar menuBar;

    public static class MenuStripControllerBuilder {
        private final MenuBar menuBar = new MenuBar();
        private final Menu companyMenu = new Menu("Companies");
        private final Menu distributorMenu = new Menu("Distributors");
        private final Menu cashierMenu = new Menu("Cashiers");
        private final Menu userMenu = new Menu("Users");
        private final Menu adminMenu = new Menu("Admin");
        private final List<MenuItem> companies = new ArrayList<>();
        private final List<MenuItem> distributors = new ArrayList<>();
        private final List<MenuItem> cashiers = new ArrayList<>();
        private final List<MenuItem> users = new ArrayList<>();

        public MenuStripControllerBuilder withCompanies(String... companies) {
            for(String company : companies) {
                this.companies.add(new MenuItem(company));
            }
            return this;
        }

        public MenuStripControllerBuilder withCompanies(List<String> companies) {
            withCompanies(companies.toArray(new String[0]));
            return this;
        }

        public MenuStripControllerBuilder withDistributors(String... distributors) {
            for (String distributor : distributors) {
                this.distributors.add(new MenuItem(distributor));
            }
            return this;
        }

        public MenuStripControllerBuilder withDistributors(List<String> distributors) {
            withDistributors(distributors.toArray(new String[0]));
            return this;
        }

        public MenuStripControllerBuilder withCashiers(String... cashiers) {
            for (String cashier : cashiers) {
                this.cashiers.add(new MenuItem(cashier));
            }
            return this;
        }

        public MenuStripControllerBuilder withCashiers(List<String> cashiers) {
            withCashiers(cashiers.toArray(new String[0]));
            return this;
        }

        public MenuStripControllerBuilder withUsers(String... users) {
            for (String user : users) {
                this.users.add(new MenuItem(user));
            }
            return this;
        }

        public MenuStripControllerBuilder withUsers(List<String> users) {
            withUsers(users.toArray(new String[0]));
            return this;
        }

        public MenuBar getMenuBar() {
            return menuBar;
        }

        public MenuStrip build() {
            companyMenu.getItems().addAll(companies);
            distributorMenu.getItems().addAll(distributors);
            cashierMenu.getItems().addAll(cashiers);
            userMenu.getItems().addAll(users);
            menuBar.getMenus().addAll(companyMenu, distributorMenu, cashierMenu, userMenu, adminMenu);

            return new MenuStrip(this);
        }
    }

    private MenuStrip(MenuStripControllerBuilder builder) {
        this.menuBar = builder.getMenuBar();
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public void addCompany(String company) {
        menuBar.getMenus().getFirst().getItems().add(new MenuItem(company));
    }

    public void addDistributor(String distributor) {
        menuBar.getMenus().get(1).getItems().add(new MenuItem(distributor));
    }

    public void addCashier(String cashier) {
        menuBar.getMenus().get(2).getItems().add(new MenuItem(cashier));
    }

    public void addUser(String user) {
        menuBar.getMenus().getLast().getItems().add(new MenuItem(user));
    }
}
