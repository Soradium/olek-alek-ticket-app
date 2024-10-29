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


    opens org.tuvarna.olekalekproject to javafx.fxml;
    exports org.tuvarna.olekalekproject;
}