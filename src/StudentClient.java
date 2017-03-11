/**
 * Created by Dave on 3/10/17.
 */

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StudentClient extends Application {

    private TextField nameTF = new TextField();
    private TextField streetTF = new TextField();
    private TextField cityTF = new TextField();
    private TextField stateTF = new TextField();
    private TextField zipTF = new TextField();

    // button to send student to the server
    private Button registerButton = new Button("Register to the Server");
    String host = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        pane.add(new Label("Name"), 0, 0);
        pane.add(nameTF, 1, 0);
        pane.add(new Label("Street"), 0, 1);
        pane.add(streetTF, 1, 1);
        pane.add(new Label("City"), 0, 2);
        pane.add(cityTF, 0, 2);

        HBox hBox = new HBox(2);
        pane.add(hBox, 1, 2);
        hBox.getChildren().addAll(cityTF, new Label("State"), stateTF, new Label("Zip Code"), zipTF);
        pane.add(registerButton, 1, 3);
        GridPane.setHalignment(registerButton, HPos.RIGHT);

        pane.setAlignment(Pos.CENTER);
        nameTF.setAlignment(Pos.CENTER);
        nameTF.setPrefColumnCount(15);
        streetTF.setPrefColumnCount(15);
        cityTF.setPrefColumnCount(10);
        streetTF.setPrefColumnCount(2);
        zipTF.setPrefColumnCount(5);

        // custom button listener
        registerButton.setOnAction(new ButtonListener());

        // create scene and place on the stage
        Scene scene = new Scene(pane, 600, 300);
        primaryStage.setTitle("Student Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public class ButtonListener implements EventHandler {

        @Override
        public void handle(Event event) {
            try {
                // connect to the server
                Socket socket = new Socket(host, 8675);

                // create output stream
                ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());

                // get text fields
                String name = nameTF.getText().trim();
                String street = streetTF.getText().trim();
                String city = cityTF.getText().trim();
                String state = stateTF.getText().trim();
                String zip = zipTF.getText().trim();

                // construct a student object
                StudentAddress studentAddress = new StudentAddress(name, street, city, state, zip);
                toServer.writeObject(studentAddress);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
