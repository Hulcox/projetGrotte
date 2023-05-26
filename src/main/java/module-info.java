module com.example.projetgrotte {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetgrotte to javafx.fxml;
    exports com.example.projetgrotte;
}