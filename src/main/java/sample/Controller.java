package sample;

import com.github.simplesteph.grpc.greeting.client.GreetingClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label label;

    @FXML
    public void handleAction(){
        label.setText("OK Button pressed");
        new GreetingClient().run();

        // for example, create a login method that actually starts the client
        // send the input from the user through gRPC and bring back a boolean
        // declaring if the user name and password is valid, if not, display a message
        // otherwise, close the login scene and open the nex one with the options
    }


}
