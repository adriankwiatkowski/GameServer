module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
                
    opens com.example.client to javafx.fxml;
    exports com.example.client;
}