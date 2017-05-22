package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.model.ClientSendReceiveThread;
import project.view.ClientController;

import java.io.IOException;

/**
 * Created by Lenovo on 2017-05-11.
 */
public class MainAppClient extends Application {

    Stage primaryStage;
    AnchorPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("test");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kalambury");

        initRootLayout();

    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            System.out.println("a");
            loader.setLocation(MainAppClient.class.getResource("view/RootLayout.fxml"));
            System.out.println("b");
            rootLayout = loader.load();
            System.out.println("c");

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tron");
            System.out.println("d");
            primaryStage.show();
            System.out.println("e");

            ClientController controller = loader.getController();
            controller.setMainAppClient(this);

            ClientSendReceiveThread send = new ClientSendReceiveThread("send", controller);
            send.start();

            ClientSendReceiveThread receive = new ClientSendReceiveThread("receive", controller, controller.objectInputStream, controller.objectOutputStream);
            receive.start();


//            ClientReceiveThread clientReceiveThread = new ClientReceiveThread(controller.objectOutputStream, controller.objectInputStream, controller.graphicsContext);
//            clientReceiveThread.start();



        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
