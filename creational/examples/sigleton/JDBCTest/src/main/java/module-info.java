module com.bawker.jdbctest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.bawker.jdbctest to javafx.fxml;
    exports com.bawker.jdbctest;
}