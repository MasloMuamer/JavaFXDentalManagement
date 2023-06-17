module com.example.dentalmanagementd2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.controlsfx.controls;
    requires java.persistence;
    requires java.sql;
    requires java.sql.rowset;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.desktop;
    requires java.xml.bind;
    requires jbcrypt;


    opens com.example.dentalmanagementd2 to javafx.fxml;
    opens com.example.dentalmanagementd2.business.model to org.hibernate.orm.core;

    exports com.example.dentalmanagementd2;
    exports com.example.dentalmanagementd2.business.model;
}

