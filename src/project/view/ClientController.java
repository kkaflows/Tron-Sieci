package project.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.*;
import project.MainAppClient;
import project.MainAppServer;
import project.model.ClientSendReceiveThread;
import project.model.DataPackage;


import javax.xml.soap.Text;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController {

    public DataPackage dataPackage;
    public MainAppClient mainAppClient;
    public Socket socket;
    public ObjectInputStream objectInputStream;
    public ObjectOutputStream objectOutputStream;
    public GraphicsContext graphicsContext;
    public BufferedImage bufferedImage;
    public static boolean[][] board = new boolean[400][400];
    public boolean isRunning = true;
    private static int id = 0;
    public String addressString;


    public void setMainAppClient(MainAppClient mainAppClient) {
        this.mainAppClient = mainAppClient;

    }

    @FXML
    public Canvas canvas;

    @FXML
    public TextField textField;

    @FXML
    public TextField address;

    @FXML
    private void initialize() {
        address.setText("localhost");
        dataPackage = new DataPackage(10, 200, 1, 0);
        bufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        graphicsContext = canvas.getGraphicsContext2D();

        initDraw(graphicsContext);
    }

    @FXML
    private void handleButtonUp() {
        if (dataPackage.getDirY() != 1)
            dataPackage.goUp();

        dataPackage.nextStep();
//        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
//        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
        graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5, 5);
    }

    @FXML
    private void handleButtonRight() {
        if (dataPackage.getDirX() != -1)
            dataPackage.goRight();

        dataPackage.nextStep();

//        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
//        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
        graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5, 5);
    }

    @FXML
    private void handleButtonDown() {
        if (dataPackage.getDirY() != -1)
            dataPackage.goDown();

        dataPackage.nextStep();
//        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
//        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
        graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5, 5);
    }

    @FXML
    private void handleButtonLeft() {
        if (dataPackage.getDirX() != 1)
            dataPackage.goLeft();

        dataPackage.nextStep();
//        bufferedImage.setRGB(dataPackage.getX(), dataPackage.getY(), Color.YELLOW.getRGB());
//        graphicsContext.drawImage(SwingFXUtils.toFXImage(bufferedImage, null), 0, 0);
        graphicsContext.fillOval(dataPackage.getX(), dataPackage.getY(), 5, 5);
    }

    @FXML
    public void handleButtonStart() throws IOException {

        ClientSendReceiveThread send = new ClientSendReceiveThread("send", this);
        send.start();
        ClientSendReceiveThread receive = new ClientSendReceiveThread("receive", this, this.objectInputStream, this.objectOutputStream);
        receive.start();
    }







    @FXML
    public void handleConnectButton() throws IOException {
        addressString = address.getText().toString();
        socket = new Socket(addressString, 4444);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

    }


    public void move() {
        if (dataPackage.getDirX() == 1)
            handleButtonRight();
        if (dataPackage.getDirX() == -1)
            handleButtonLeft();
        if (dataPackage.getDirY() == 1)
            handleButtonDown();
        if (dataPackage.getDirY() == -1)
            handleButtonUp();


        board[dataPackage.getX()][dataPackage.getY()] = true;

        try {
            DataPackage tmp = new DataPackage(dataPackage.getX(), dataPackage.getY(), dataPackage.getDirX(), dataPackage.getDirY());
            //System.out.println(dataPackage.getX());
            objectOutputStream.writeObject(tmp);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(javafx.scene.paint.Color.LIGHTGRAY);
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(javafx.scene.paint.Color.RED);
        gc.setStroke(javafx.scene.paint.Color.BLUE);
        gc.setLineWidth(1);
    }

    @FXML
    private void handleButtonPlayerOne() {

        dataPackage = new DataPackage(0, 200, 1, 0);
        graphicsContext.setFill(javafx.scene.paint.Color.RED);
    }

    @FXML
    private void handleButtonPlayerTwo() {
        dataPackage = new DataPackage(390, 200, -1, 0);
        graphicsContext.setFill(javafx.scene.paint.Color.BLUE);
    }


}
