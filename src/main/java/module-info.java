module org.tuvarna.olekalekproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.postgresql.jdbc;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;
    requires java.naming;
    requires jakarta.transaction;

    opens org.tuvarna.olekalekproject to javafx.fxml;
    opens org.tuvarna.entity to org.hibernate.orm.core;
    exports org.tuvarna.olekalekproject;
    exports org.tuvarna.controller;
    opens org.tuvarna.controller to javafx.fxml;
}